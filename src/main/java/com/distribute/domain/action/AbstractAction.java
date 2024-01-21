package com.distribute.domain.action;

import com.distribute.persistence.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Objects;

/**
 * 模版模式,抽象类
 *
 * @param <T>
 */
@Slf4j
@SuppressWarnings("all")
abstract public class AbstractAction<T> {

    private Class<T> entityClass;

    @Autowired
    private Redisson redisson;

    public AbstractAction() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    private RBlockingQueue<BatchResultVo> getSyncBlockQueue(String queueName) {
        return redisson.getBlockingQueue(queueName);
    }

    public void doAction(BatchParam<T> batchParam) {
        String queueName = batchParam.getQueueName();
        if (StringUtils.isBlank(queueName)) {
            log.error("queueName 为空，无法获取结果");
            return;
        }

        BatchResultVo vo = null;
        try {
            if (batchParam.getParam() instanceof String) {
                String jsonObject = (String) batchParam.getParam();
                T param = JsonUtil.toClass((String) batchParam.getParam(), entityClass);
                vo = onAction(param);
            } else {
                vo = onAction(batchParam.getParam());
            }
        } catch (Exception e) {
            log.error("action 执行异常:", e);
            Outcome outcome = Outcome.builder().data(e.getMessage()).build();
            vo = BatchResultVo.builder().failCount(1).successCount(0).unableOperate(0)
                    .failList(Collections.singletonList(outcome))
                    .build();
        }

        if (StringUtils.isNotBlank(queueName)) {
            RBlockingQueue<BatchResultVo> queue = getSyncBlockQueue(queueName);
            if (Objects.isNull(vo)) {
                Outcome outcome = Outcome.builder().data(batchParam.getParam() + "-执行结果为空").build();
                vo = BatchResultVo.builder().failCount(1).successCount(0).unableOperate(0)
                        .failList(Collections.singletonList(outcome))
                        .build();
            }
            queue.add(vo);
        }
    }

    /**
     * 执行操作
     *
     * @param param
     * @return
     */
    abstract public BatchResultVo onAction(T param);

}
