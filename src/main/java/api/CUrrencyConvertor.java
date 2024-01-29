package api;

import java.time.Duration;
import java.util.*;


class CachedRate<Double,Long>{
    Double rate;
    Long timeToLive;
    CachedRate(Double rate, Long timeToLive){
        this.rate=rate;
        this.timeToLive=timeToLive;
    }
    public Double getRate() {
        return rate;
    }
    public Long getTimeToLive() {
        return timeToLive;
    }

}

interface RateService {
    /** This method returns an exchange rate for two currencies.
     You can assume that this method is already implemented. It makes an HTTP call to the rate service.

     Examples:
     EUR -> GBP : 0.88
     GBP -> EUR : 1.14
     GBP -> USD : 1.23


     We pay 0.01 USD for each rate call, and it has 98% availability.
     */
    Double getRate(String toCurrency, String fromCurrrency);
}

/**
 This is a backend service that should serve Wise customers.
 The customer should see what they'll get if they convert their amount of money to a given currency.

 EUR--> GBP : 102
 --->INR : 1003
 rrr
 INR--->GBP : 45
 --->EUR : 56

 Example:
 Given 100 USD we will get 81.37 GBP.
 Given 100 GBP we will get 123 USD

 This service method is called from outside with around 1000 requests/sec.
 We would like it to be highly available and serve the majority of customers in less than 100 ms.



 Your job is to implement the convert method below. You can consider us as your teammates.
 */


class CurrencyConverter {
    Map<String, Map<String,CachedRate>> currencyGraph=new HashMap<>();
    private static final long EVICTION_INTERVAL = 5 * 60 * 1000; // 5 minutes in milliseconds
    RateService rateService;  // Injected
    public Double convert(String toCurrency, String fromCurrency, Double amount)  {
        Double rateFromCache =fetchRate(toCurrency,fromCurrency);
        if(rateFromCache<0){
            Double rate= rateService.getRate(toCurrency, fromCurrency);
            createCurrencyGrap(toCurrency,  fromCurrency,rate);
        }
        amount=amount*rateFromCache;
        return amount;
    }
    //first time we will return the empty hashmap

    public void createCurrencyGrap(String toCurrency, String fromCurrency, Double rate){
        if(!currencyGraph.containsKey(toCurrency)){
            //EUR-->(GBP,1000)
            currencyGraph.put(toCurrency, new HashMap<>());
        }
        Map<String, CachedRate> valueMap=currencyGraph.get(toCurrency);
        //else check for the stale price
        valueMap.put(fromCurrency, new CachedRate(rate,System.currentTimeMillis()));
        currencyGraph.put(toCurrency, valueMap);
        if(!currencyGraph.containsKey(fromCurrency)){
            currencyGraph.put(fromCurrency, new HashMap<>());
        }
        valueMap=currencyGraph.get(fromCurrency);
        valueMap.put(fromCurrency, new CachedRate(1/rate,System.currentTimeMillis()));
        //currencyGraph.put(toCurrency, new ChachedRate(rate,System.currentTimeMillis());
        currencyGraph.put(fromCurrency, valueMap);
    }

    public double fetchRate(String toCurrency, String fromCurrency) {
        if (!currencyGraph.containsKey(toCurrency)) {
            return -1;
        }
        double value = -1;
        Set<String> visited = new HashSet<>();
        Queue<String> nodes = new LinkedList<>();
        Queue<Double> values = new LinkedList<>();
        nodes.add(toCurrency);
        values.add(1.0);
        while (!nodes.isEmpty()) {
            toCurrency = nodes.poll();
            double amount = values.poll();
            if (visited.contains(toCurrency)) continue;
               visited.add(toCurrency);
                Map<String, CachedRate> chachedRateMap = currencyGraph.get(toCurrency);
            for (String key : chachedRateMap.keySet()) {
                if (key.equals(fromCurrency)) {
                    return amount * (double)chachedRateMap.get(fromCurrency).getRate();
                }
                nodes.add(key);
                values.add(amount*(double)chachedRateMap.get(key).getRate());
            }
        }
        return -1;
    }

    private void evictStaleEntries() {
        long currentTime = System.currentTimeMillis();
        for (Map<String, CachedRate> rates : currencyGraph.values()) {
            rates.entrySet().removeIf(entry -> (currentTime - (long)entry.getValue().getTimeToLive()) > EVICTION_INTERVAL);
        }
    }


}

