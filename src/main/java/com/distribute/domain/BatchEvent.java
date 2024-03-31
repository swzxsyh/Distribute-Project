package com.distribute.domain;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class BatchEvent extends ApplicationEvent {

    private Integer batch;
    private Integer action;

    public BatchEvent(Object source, Integer batch, Integer action) {
        super(source);
        this.batch = batch;
        this.action = action;
    }

    public BatchEvent(Object source, Clock clock, Integer batch, Integer action) {
        super(source, clock);
        this.batch = batch;
        this.action = action;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
