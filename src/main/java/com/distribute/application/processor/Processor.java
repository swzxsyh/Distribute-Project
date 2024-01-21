package com.distribute.application.processor;

/**
 * 职责链模式
 */
public interface Processor {

    /**
     * 链式处理
     *
     * @param context 上下文
     */
    void process(ProcessContext context);
}
