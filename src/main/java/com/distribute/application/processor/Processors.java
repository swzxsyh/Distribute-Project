package com.distribute.application.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Processors implements InitializingBean, ApplicationContextAware {

    List<Processor> dummy = new ArrayList<>();

    private ApplicationContext applicationContext;

    List<Processor> PROCESSOR_LIST = new ArrayList<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        PROCESSOR_LIST.add(applicationContext.getBean(ValidateProcessor.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public void process(ProcessContext context) {
        for (Processor processor : PROCESSOR_LIST) {
            processor.process(context);
        }
    }
}
