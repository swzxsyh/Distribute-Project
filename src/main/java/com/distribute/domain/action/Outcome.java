package com.distribute.domain.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Outcome {
    private boolean success;
    private String id;
    private String data;

    public static Outcome success(String id, String data) {
        return Outcome.builder().success(true).id(id).data(data).build();
    }

    public static Outcome error(String id, String data) {
        return Outcome.builder().success(false).id(id).data(data).build();
    }

    public static Outcome error(String id, String data, String msg) {
        return Outcome.builder().success(false).id(id).data(data).build();
    }

}
