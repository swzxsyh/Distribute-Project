package com.distribute.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批量处理入参
 *
 * @author swzxsyh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchInputDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String inputId;

    private Integer inputType;

    private List<Odds> list;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Odds {
        private Long id;
        private Long odds;
        private BigDecimal amount;
    }


}
