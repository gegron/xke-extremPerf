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

                String s = map.get("S").value();
                String k = map.get("K").value();
                String r = map.get("R").value();
                String u = map.get("u").value();
                String expiration = map.get("expiration").value();

                return String.format("Recu: S=%s, K=%s, R=%s, u=%s, expiration=%s", s, k, r, u, expiration);
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
