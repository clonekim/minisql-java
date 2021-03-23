package tools;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.io.IOException;

public class Application  {

    public static void main(String[] args) throws IOException {

        final Environment environment = new Environment();
        DatabaseFactory databaseFactory = new DatabaseFactory(environment);


        Javalin app = Javalin.create( config -> {

            if(environment.debug)
                config.enableDevLogging();

            config.addStaticFiles("/", "static", Location.CLASSPATH);
            config.addSinglePageRoot("/", "static/index.html", Location.CLASSPATH);

        }).start(environment.port);



        app.get("/hello", ctx -> Router.test(ctx, databaseFactory));
    }
}
