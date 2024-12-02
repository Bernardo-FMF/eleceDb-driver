package org.elece.handler;

import org.elece.request.RequestType;
import org.elece.response.ResponseType;

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
            case CREATE_DB -> new GenericResponseHandler(ResponseType.CREATE_DB);
            case DROP_DB -> new GenericResponseHandler(ResponseType.DROP_DB);
            case CREATE_TABLE -> new GenericResponseHandler(ResponseType.CREATE_TABLE);
            case DROP_TABLE -> new GenericResponseHandler(ResponseType.DROP_TABLE);
            case CREATE_INDEX -> new GenericResponseHandler(ResponseType.CREATE_INDEX);
            case INSERT -> new GenericResponseHandler(ResponseType.INSERT);
            case DELETE -> new GenericResponseHandler(ResponseType.DELETE);
            case UPDATE -> new GenericResponseHandler(ResponseType.UPDATE);
            case SELECT -> new SelectResponseHandler();
        };
    }
}
