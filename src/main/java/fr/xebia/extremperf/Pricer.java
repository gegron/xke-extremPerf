package fr.xebia.extremperf;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.primitives.Doubles.max;

public class Pricer {

    private final Integer expiration;
    private final Double R;
    private final Double u;
    private final Double K;
    private final Double d;
    private final Double q;
    private final Double initialStockPrice;

    private Map<MyEntry, Double> cuCache = new HashMap<MyEntry, Double>();
    private Map<MyEntry, Double> cdCache = new HashMap<MyEntry, Double>();

    public Pricer(Double initialStockPrice, Double strike, Double upFactor, Double riskFreeRate, Integer expiration) {
        this.initialStockPrice = initialStockPrice;
        this.K = strike;
        this.u = upFactor;
        this.R = riskFreeRate;
        this.expiration = expiration;
        this.d = 1 / u;
        this.q = (R - d) / (u - d);
    }

    private Double round(Double aDouble) {
        return Math.rint(aDouble * 100) / 100;
    }

//    public Double fairValue() {
//        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));
//
//        service.submit(new Callable<Pricer>() {
//            @Override
//            public Object call() throws Exception {
//                return null;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        })
//
//
//        return loop(0, initialStockPrice);
//    }
//
//    private Double loop(Integer period, Double S) {
//        if (period.equals(expiration)) {
//            return max(0, S - K);
//        }
//        else {
//            Double Cu = loop(period + 1, S * u);
//            Double Cd = loop(period + 1, S * d);
//
//            return round(1 / R * (q * Cu + (1 - q) * Cd));
//        }
//    }

    public Double fairValue() {
        return loop(0, initialStockPrice);
    }

    private Double loop(Integer period, Double S) {
        if (period.equals(expiration)) {
            return max(0, round(S - K));
        }
        else {
            Double Cu;
            if (cuCache.containsKey(new MyEntry(period + 1, S * u))) {
                Cu = cuCache.get(new MyEntry(period + 1, S * u));
            }
            else {
                Cu = loop(period + 1, S * u);
                cuCache.put(new MyEntry(period + 1, S * u), Cu);
            }

            Double Cd;
            if (cdCache.containsKey(new MyEntry(period + 1, S * d))) {
                Cd = cdCache.get(new MyEntry(period + 1, S * d));
            }
            else {
                Cd = loop(period + 1, S * d);
                cdCache.put(new MyEntry(period + 1, S * d), Cd);
            }

            return round(1 / R * (q * Cu + (1 - q) * Cd));
        }
    }

    private class MyEntry {
        Integer period;
        Double s;

        private MyEntry(Integer period, Double s) {
            this.period = period;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyEntry myEntry = (MyEntry) o;

            if (!period.equals(myEntry.period)) return false;
            if (!s.equals(myEntry.s)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = period.hashCode();
            result = 31 * result + s.hashCode();
            return result;
        }
    }
}
