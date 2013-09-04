package SparkWebDemo;

import static spark.Spark.*;
import spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        setPort(8080);

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });
    }
}
