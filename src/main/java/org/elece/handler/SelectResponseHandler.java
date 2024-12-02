package org.elece.handler;

import org.elece.response.ResponseType;
import org.elece.response.SqlResponse;
import org.elece.socket.DbSocket;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectResponseHandler implements ResponseHandler {
    @Override
    public SqlResponse handle(DbSocket dbSocket) throws IOException {
        int messageType = Integer.parseInt(dbSocket.readSizedString(1));
        String header = dbSocket.readLine();
        if (isErrorResponse(messageType)) {
            return createErrorResponse(header, dbSocket);
        }

        int responseSize = extractResponseSize(header, ResponseType.SELECT_START);

        SqlResponse sqlResponse = new SqlResponse();
        sqlResponse.addMessage(header);
        sqlResponse.addMessage(dbSocket.readSizedString(responseSize));

        int nextMessageType;
        do {
            nextMessageType = Integer.parseInt(dbSocket.readSizedString(1));
            if (isErrorResponse(nextMessageType)) {
                return createErrorResponse(header, dbSocket);
            }
            if (nextMessageType == 3) {
                String rowHeader = dbSocket.readLine();
                int rowResponseSize = Integer.parseInt(extractAttribute(rowHeader, "RowSize").get());
                String row = dbSocket.readSizedString(rowResponseSize);
                sqlResponse.addMessage(extractAttribute(row, "Row").get());
            }
        } while (nextMessageType == 3);


        String endHeader = dbSocket.readLine();
        if (isErrorResponse(nextMessageType)) {
            return createErrorResponse(endHeader, dbSocket);
        }
        responseSize = extractResponseSize(endHeader, ResponseType.SELECT_END);
        sqlResponse.addMessage(endHeader);
        sqlResponse.addMessage(dbSocket.readSizedString(responseSize));

        return sqlResponse;
    }

    private Optional<String> extractAttribute(String response, String attributeName) {
        String regexPattern = attributeName + ":\\s*(.*)";
        Pattern pattern = Pattern.compile(regexPattern, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(response);

        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }
}
