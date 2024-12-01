package org.elece.io;

import org.elece.request.RequestMapper;
import org.elece.request.SqlRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestConsoleReader {
    private final BufferedReader consoleReader;
    private final BufferedWriter consoleWriter;

    public RequestConsoleReader() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
        this.consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    }

    public List<SqlRequest> readNextRequests() throws IOException {
        consoleWriter.write("> ");
        consoleWriter.flush();
        String request = consoleReader.readLine();
        if ("EXIT".equalsIgnoreCase(request)) {
            return List.of();
        }

        return RequestMapper.prepareRequests(request);
    }

    public void close() throws IOException {
        consoleReader.close();
        consoleWriter.close();
    }
}
