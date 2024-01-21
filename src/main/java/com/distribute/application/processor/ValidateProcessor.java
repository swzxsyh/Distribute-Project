package com.distribute.application.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * example 职责链处理器
 */
@Slf4j
@Service
public class ValidateProcessor implements Processor{

    @Override
    public void process(ProcessContext context) {

    }
}
