package com.repository;

import com.entity.Period;
import com.entity.Quotes;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "quotes",path = "quotes")
public interface QuotesRepo extends PagingAndSortingRepository<Quotes, Integer> {
    List<Quotes> findByCurrency(@Param("currencyname") String currency);
    List<Quotes> findByCurrencyAndPeriod(@Param("currencyname") String currency,@Param("period")Period period);
}
