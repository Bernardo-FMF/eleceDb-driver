package org.elece.handler;

import org.elece.response.ResponseType;
import org.elece.response.SqlResponse;
import org.elece.socket.DbSocket;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ResponseHandler {
    SqlResponse handle(DbSocket dbSocket) throws IOException;

    default Integer extractResponseSize(String header, ResponseType responseType) {
        Pattern responsePattern = Pattern.compile(String.format("Response::%s::(\\d+)::", responseType.getResponseType()));
        Matcher responseMatcher = responsePattern.matcher(header);
        if (responseMatcher.find()) {
            return Integer.parseInt(responseMatcher.group(1));
        }
        return 0;
    }

    default boolean isErrorResponse(String header) {
        return header.startsWith("Response::" + ResponseType.ERROR.name() + "::");
    }

    default SqlResponse createErrorResponse(String header, DbSocket dbSocket) {
        return null;
    }
}
