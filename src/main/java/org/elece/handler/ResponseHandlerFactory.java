package org.elece.handler;

import org.elece.request.RequestType;

import java.util.Objects;

public class ResponseHandlerFactory {
    private static ResponseHandlerFactory instance;

    private ResponseHandlerFactory() {
        // private constructor
    }

    public static ResponseHandlerFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ResponseHandlerFactory();
        }
        return instance;
    }

    public ResponseHandler getResponseHandler(RequestType requestType) {
        return switch (requestType) {
            case CREATE_DB -> new CreateDbResponseHandler();
            case DROP_DB -> new DropDbResponseHandler();
            default -> null;
        };
    }
}
