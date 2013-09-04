package fr.xebia.extremperf;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import static com.google.common.primitives.Doubles.max;

public class Pricer {

    private final Integer expiration;
    private final Double R;
    private final Double u;
    private final Double K;
    private final Double d;
    private final Double q;
    private final Double initialStockPrice;

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
            Double Cu = loop(period + 1, S * u);
            Double Cd = loop(period + 1, S * d);

            return round(1 / R * (q * Cu + (1 - q) * Cd));
        }
    }

}
