package org.elece.request;

public record SqlRequest(RequestType requestType, String sqlString) {
    @Override
    public String toString() {
        return "SqlRequest{" +
                "requestType=" + requestType +
                ", sqlString='" + sqlString + '\'' +
                '}';
    }
}