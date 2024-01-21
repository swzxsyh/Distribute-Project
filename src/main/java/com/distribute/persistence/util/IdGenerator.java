package com.distribute.persistence.util;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

/**
 * ID生成器
 */
public class IdGenerator {

    static {
        final IdGeneratorOptions idGeneratorOptions = new IdGeneratorOptions();
        idGeneratorOptions.WorkerIdBitLength = 10;
        idGeneratorOptions.SeqBitLength = 12;
        idGeneratorOptions.WorkerId = 1;
        YitIdHelper.setIdGenerator(idGeneratorOptions);
    }

    /**
     * 生成ID，带前缀
     *
     * @param prefix
     * @return
     */
    public static String genId(String prefix) {
        // snow flow id
        return prefix + YitIdHelper.nextId();
    }
}
