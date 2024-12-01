package org.elece.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestMapper {
    public RequestMapper() {
        // private constructor
    }

    public static List<SqlRequest> prepareRequests(String request) {
        String[] requests = request.split(";");
        ArrayList<SqlRequest> mappedRequests = new ArrayList<>();

        for (String sqlRequest : requests) {
            Optional<RequestType> requestType = RequestType.fromPrefix(sqlRequest);
            requestType.ifPresent(type -> mappedRequests.add(new SqlRequest(type, sqlRequest)));
        }

        return mappedRequests;
    }
}
