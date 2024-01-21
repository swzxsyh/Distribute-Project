package com.distribute.domain.action;

import com.distribute.persistence.model.BatchInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class InputAction extends AbstractAction<BatchInput> {

    @Override
    public BatchResultVo onAction(BatchInput param) {
        try {
            return BatchResultVo.builder()
                    .successCount(0).failCount(1).unableOperate(0)
                    .failList(Collections.singletonList(Outcome.success(param.getId(), "success")))
                    .build();
        } catch (Exception e) {
            Outcome outcome = Outcome.error(param.getId(), e.getMessage());
            return BatchResultVo.builder()
                    .successCount(0).failCount(1).unableOperate(0)
                    .failList(Collections.singletonList(outcome))
                    .build();
        }
    }
}
