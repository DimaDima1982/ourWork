package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by dima on 31.05.18.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("quotes")
public class Quotes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @JsonProperty("id")
    private Integer id;

    @Column(name="currencyname", nullable = false)
    private String currency;

    @Column(name="period", nullable = false)
    @Enumerated(EnumType.STRING)
    private Period period;

    @Column(name = "date", nullable = false)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime data;

    @Column(name="open", nullable = false)
    private BigDecimal open;

    @Column(name="close", nullable = false)
    private BigDecimal close;

    @Column(name="hight", nullable = false)
    private BigDecimal high;

    @Column(name="low", nullable = false)
    private BigDecimal low;

    public BigDecimal getPrice(){
        return (this.low
                .add(this.high
                        .add(this.open
                                .add(this.close))))
                .divide(new BigDecimal(4));
    }
}
