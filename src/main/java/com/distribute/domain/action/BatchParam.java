package com.distribute.domain.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张嘉旭
 * @time 2021/2/17 16:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchParam<T> {

    private String queueName;

    private T param;

    private String beanName;
}
