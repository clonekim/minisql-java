package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {

    String jdbcUrl;
    String username;
    String password;

    int port;
    boolean debug;

    public Environment() throws IOException {
        this("classpath:/application.properties");
    }

    public Environment(String path) throws IOException {

        Properties properties = new Properties();

        if(path.contains("classpath")) {
            properties.load(this.getClass().getResourceAsStream(path.substring(11)));
        } else {
            properties.load(new FileInputStream(path));
        }

        jdbcUrl =  properties.getProperty("database.jdbcUrl");
        username = properties.getProperty("database.username");
        password = properties.getProperty("database.password");

        port =  Integer.parseInt(properties.getProperty("server.port", "7000"));
        debug = Boolean.parseBoolean(properties.getProperty("server.debug", "false"));
    }
}
