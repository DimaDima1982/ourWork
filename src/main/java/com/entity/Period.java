package com.entity;

/**
 * Created by dima on 31.05.18.
 */
public enum Period {
    FIVEMINUTES ("5",3),
    FIFTEENMINUTES ("15",5),
    ONEHOUR("60",7);

    private final String period;
    private final Integer timeParametr;

    private Period (final String period, Integer timeParametr){
        this.timeParametr=timeParametr;
        this.period = period;
    }

    public static Integer getByCurrensy(String name) {
        for(Period e : values()) {
            if(e.period.equals(name)) return e.timeParametr;
        }
        return 0;
    }

    @Override
    public String toString(){
        return period;
    }
}
