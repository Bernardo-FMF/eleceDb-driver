package org.elece.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LogResponseWriterDecorator extends ResponseWriterDecorator {
    private final Logger logger = LogManager.getLogger(LogResponseWriterDecorator.class);

    public LogResponseWriterDecorator(ResponseWriter responseWriter) {
        super(responseWriter);
    }

    @Override
    public void write(String response) throws IOException {
        logger.info("Logging response: {}", response);
        super.write(response);
    }

    @Override
    public void close() throws IOException {
        logger.info("Closing response writer");
        super.close();
    }
}
