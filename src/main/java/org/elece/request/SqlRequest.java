package org.elece.request;

public record SqlRequest(RequestType requestType, String sqlString) {
}