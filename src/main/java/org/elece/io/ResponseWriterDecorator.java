package org.elece.io;

import java.io.IOException;

public class ResponseWriterDecorator implements ResponseWriter {
    private final ResponseWriter responseWriter;

    public ResponseWriterDecorator(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    @Override
    public void write(String response) throws IOException {
        responseWriter.write(response);
    }

    @Override
    public void close() throws IOException {
        responseWriter.close();
    }
}
