package com.entity;

/**
 * Created by dima on 31.05.18.
 */
public enum Currency {
    EURUSD ("EURUSD",83),
    GBPUSD ("GBPUSD",86),
    AUDUSD ("AUDUSD",66699);

    private final String currency;
    private final Integer em;

    private Currency (final String currency, final Integer em){
        this.currency = currency;
        this.em=em;
    }

    public static Integer getByCurrensy(String name) {
        for(Currency e : values()) {
            if(e.currency.equals(name)) return e.em;
        }
        return 0;
    }

    @Override
    public String toString(){
        return currency;
    }
}
