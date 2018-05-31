package com.controller;


import com.entity.Currency;
import com.entity.Period;
import com.execute.MovingAverage;
import com.execute.Result;
import com.repository.QuotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
public class Controller {

    @Autowired
    QuotesRepo quotesRepo;

    @RequestMapping(value="/result", method = RequestMethod.GET)
    public Map<String,List<Set<MovingAverage>>> result(){
        Map<String,List<Set<MovingAverage>>>response = new LinkedHashMap<>();
        Result  result = new Result();
        for(Currency currency: Currency.values()){
            List<Set<MovingAverage>>localList = new ArrayList<>();
            for(Period period: Period.values()){
                Set<MovingAverage> set =result.getResult(quotesRepo.findByCurrencyAndPeriod(currency.name(),period));
                localList.add(set);
            }
            response.put(currency.name(),localList);
        }
        return response;
    }

    @RequestMapping(value="/reload", method = RequestMethod.GET)
    public void reload(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        Period period = null;
        String filepath= "/mnt/3a9fc5c4-bfb3-4721-9434-10c35b009622/job";
        String target = "/mnt/3a9fc5c4-bfb3-4721-9434-10c35b009622/data.sql";
        String MAIN = "insert into \"quotes\" (\"currencyname\",\"period\",\"open\",\"close\",\"hight\",\"low\",\"date\") VALUES ('%s','%s', %s,%s,%s,%s,parsedatetime('%s','yyyy-MM-dd HH:mm'))";
        final File folder = new File(filepath);
        List<File> fileArray = new ArrayList<>(Arrays.asList(folder.listFiles()));
        for (File file:fileArray){
            try {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                FileWriter fstream = new FileWriter(target, true);
                BufferedWriter out = new BufferedWriter(fstream);

                String aLine = null;
                while ((aLine = in.readLine()) != null) {
                    String [] array = aLine.split(",");
                    if (array[1].equals("5"))period = Period.FIVEMINUTES;
                    if (array[1].equals("15"))period = Period.FIFTEENMINUTES;
                    if (array[1].equals("60"))period = Period.ONEHOUR;
                    LocalDateTime localDateTime = LocalDateTime.parse(array[2]+" " + array[3], formatter);
                    String strDataTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String rezult =  String.format(MAIN,array[0],period.name(),array[4],array[7],array[5],array[6],strDataTime);
                    out.write(rezult);
                    out.newLine();
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
