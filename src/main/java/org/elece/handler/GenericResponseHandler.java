package org.elece.handler;

import org.elece.response.ResponseType;
import org.elece.response.SqlResponse;
import org.elece.socket.DbSocket;

import java.io.IOException;

public class GenericResponseHandler implements ResponseHandler {
    private final ResponseType responseType;

    public GenericResponseHandler(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public SqlResponse handle(DbSocket dbSocket) throws IOException {
        int messageType = Integer.parseInt(dbSocket.readSizedString(1));
        String header = dbSocket.readLine();
        if (isErrorResponse(messageType)) {
            return createErrorResponse(header, dbSocket);
        }
        int responseSize = extractResponseSize(header, responseType);
        if (responseSize > 0) {
            SqlResponse sqlResponse = new SqlResponse();
            sqlResponse.addMessage(header);
            sqlResponse.addMessage(dbSocket.readSizedString(responseSize));
            return sqlResponse;
        }
        return new SqlResponse();
    }
}
