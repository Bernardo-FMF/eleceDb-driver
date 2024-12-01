package org.elece;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elece.config.DefaultDriverConfigBuilder;
import org.elece.config.DriverConfig;
import org.elece.handler.ResponseHandler;
import org.elece.handler.ResponseHandlerFactory;
import org.elece.io.ConsoleResponseWriter;
import org.elece.io.LogResponseWriterDecorator;
import org.elece.io.RequestConsoleReader;
import org.elece.io.ResponseWriter;
import org.elece.request.SqlRequest;
import org.elece.response.SqlResponse;
import org.elece.socket.DbSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class EleceDbDriver {
    private static final Logger logger = LogManager.getLogger(EleceDbDriver.class);

    private static final String CONTEXT_MENU_STRING = """
            List of available queries:
                > CREATE DATABASE <database_name>;
                > DROP DATABASE <database_name>;
            """;

    private static final Map<Class<?>, Function<String, ?>> clazzHandlers;

    static {
        clazzHandlers = new HashMap<>();

        clazzHandlers.put(Integer.class, Integer::parseInt);
        clazzHandlers.put(String.class, String::trim);
    }

    public static void main(String[] args) throws IOException {
        DriverConfig driverConfig = buildDriverConfig();
        logger.info("Starting driver with config: {}", driverConfig);

        DbSocket dbSocket = createSocket(driverConfig);
        RequestConsoleReader requestConsoleReader = new RequestConsoleReader();
        ResponseWriter responseWriter = new LogResponseWriterDecorator(new ConsoleResponseWriter());

        ResponseHandlerFactory responseHandlerFactory = ResponseHandlerFactory.getInstance();

        responseWriter.write(CONTEXT_MENU_STRING);

        while (true) {
            List<SqlRequest> sqlRequests = requestConsoleReader.readNextRequests();
            if (sqlRequests.isEmpty()) {
                break;
            }

            for (SqlRequest sqlRequest : sqlRequests) {
                ResponseHandler responseHandler = responseHandlerFactory.getResponseHandler(sqlRequest.requestType());
                if (Objects.isNull(responseHandler)) {
                    responseWriter.write("Failed to identify type of query");
                    break;
                }

                dbSocket.sendRequest(sqlRequest);
                SqlResponse handle = responseHandler.handle(dbSocket);

                responseWriter.write(String.join("\n", handle.getMessages()));
            }
        }

        requestConsoleReader.close();
        responseWriter.close();
        dbSocket.close();
    }

    private static DbSocket createSocket(DriverConfig driverConfig) throws IOException {
        return new DbSocket(driverConfig);
    }

    private static DriverConfig buildDriverConfig() {
        DefaultDriverConfigBuilder builder = DefaultDriverConfigBuilder.builder();

        Integer port = getProperty("elece.db.port", Integer.class);
        if (Objects.nonNull(port)) {
            builder.setPort(port);
        }

        String host = getProperty("elece.db.host", String.class);
        if (Objects.nonNull(host)) {
            builder.setHost(host);
        }

        return builder.build();
    }

    private static <T> T getProperty(String name, Class<T> clazz) {
        if (Strings.isNullOrEmpty(name)) {
            throw new UnsupportedOperationException(String.format("Can't safely parse the property value to %s. Property value is null or empty", clazz));
        }

        String baseValue = System.getProperty(name);
        if (Strings.isNullOrEmpty(baseValue)) {
            return null;
        }

        if (!sanityCheck(clazz)) {
            return null;
        }

        try {
            return (T) clazzHandlers.get(clazz).apply(baseValue);
        } catch (Exception exception) {
            return null;
        }
    }

    private static <T> boolean sanityCheck(Class<T> clazz) {
        return clazzHandlers.containsKey(clazz);
    }
}
