package fr.xebia.extremperf;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.setPort;

public class MyServer {

    public static void main( String[] args )
    {
        setPort(8080);

        /*
        S=176.63&K=169.44&R=1.0&u=1.8&expiration=1

         */
        get(new Route("/price") {
            @Override
            public Object handle(Request request, Response response) {
                QueryParamsMap map = request.queryMap();

                Double s = Double.valueOf(map.get("S").value());
                Double k = Double.valueOf(map.get("K").value());
                Double r = Double.valueOf(map.get("R").value());
                Double u = Double.valueOf(map.get("u").value());
                Integer expiration = Integer.valueOf(map.get("expiration").value());

                Pricer pricer = new Pricer(s, k, u, r, expiration);

                return pricer.fairValue();
            }
        });

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });
    }

}
