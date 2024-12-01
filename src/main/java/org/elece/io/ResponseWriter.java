package org.elece.io;

import java.io.IOException;

public interface ResponseWriter {
    void write(String response) throws IOException;

    void close() throws IOException;
}
