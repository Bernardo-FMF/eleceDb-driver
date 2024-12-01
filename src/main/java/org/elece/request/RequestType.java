package org.elece.request;

import java.util.Optional;

public enum RequestType {
    CREATE_DB("CREATE DATABASE"),
    DELETE_DB("DELETE DATABASE"),
    CREATE_TABLE("CREATE TABLE"),
    DROP_TABLE("DROP TABLE"),
    CREATE_INDEX("CREATE INDEX"),
    SELECT("SELECT"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private static final RequestType[] REQUEST_TYPES = values();
    private final String prefix;

    RequestType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static Optional<RequestType> fromPrefix(String request) {
        for (RequestType requestType : REQUEST_TYPES) {
            if (request.regionMatches(true, 0, requestType.getPrefix(), 0, requestType.getPrefix().length())) {
                return Optional.of(requestType);
            }
        }
        return Optional.empty();
    }
}
