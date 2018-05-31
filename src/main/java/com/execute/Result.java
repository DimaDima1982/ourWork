package com.execute;

import com.entity.Quotes;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dima on 31.05.18.
 */
@Builder
@NoArgsConstructor
public class Result {
    private final int slow = 100;
    private final int middle = 50;
    private final int fast = 10;

    public Set<MovingAverage> getResult(List<Quotes>listOfQuotes){
        Set<MovingAverage> result = new LinkedHashSet<>();
        for(int i=slow; i<listOfQuotes.size();i++){
            List<Quotes>slowList =listOfQuotes.subList(i-slow,i);
            List<Quotes>middleList =listOfQuotes.subList(i-middle,i);
            List<Quotes>fastList =listOfQuotes.subList(i-fast,i);
            MovingAverage ma = MovingAverage.builder()
                    .slow(slow)
                    .middle(middle)
                    .fast(fast)
                    .dataTime(listOfQuotes.get(0).getData())
                    .slowRes(getValueMa(slowList))
                    .middleRes(getValueMa(middleList))
                    .fastRes(getValueMa(fastList))
                    .build();
            ma.exResult();
            result.add(ma);

        }
        return result;
    }

    private BigDecimal getValueMa(List<Quotes> list){
        List<BigDecimal>listOfPrice = new ArrayList<>();
        list.forEach(quotes ->  listOfPrice.add(quotes.getPrice()));
        BigDecimal sum = listOfPrice.stream().map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(new BigDecimal(list.size()), 4);
    }


}
