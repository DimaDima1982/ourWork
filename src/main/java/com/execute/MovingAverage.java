package com.execute;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by dima on 31.05.18.
 */
@Builder
@Data
public class MovingAverage {
    private int slow;
    private int middle;
    private int fast;

    private BigDecimal slowRes;
    private BigDecimal middleRes;
    private BigDecimal fastRes;
    private String result;
    private LocalDateTime dataTime;

    public void exResult() {
        if ((middleRes.compareTo(fastRes) == 1) && (slowRes.compareTo(middleRes) == 1)) {
            this.result = "UP";
        } else if ((middleRes.compareTo(fastRes) == -1) && (slowRes.compareTo(middleRes) == -1)) {
            this.result = "DOWN";
        } else {
            this.result = "FENCE";
        }
    }

}
