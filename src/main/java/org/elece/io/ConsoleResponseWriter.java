package org.elece.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ConsoleResponseWriter implements ResponseWriter {
    private final BufferedWriter consoleWriter;

    public ConsoleResponseWriter() {
        this.consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    }

    @Override
    public void write(String response) throws IOException {
        consoleWriter.write(response);
        consoleWriter.flush();
    }

    @Override
    public void close() throws IOException {
        consoleWriter.close();
    }
}
