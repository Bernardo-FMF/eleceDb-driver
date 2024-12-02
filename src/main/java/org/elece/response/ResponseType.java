package org.elece.response;

public enum ResponseType {
    CREATE_DB("CreateDbResult"),
    CREATE_INDEX("CreateIndexResult"),
    CREATE_TABLE("CreateTableResult"),
    DROP_DB("DropDbResult"),
    DROP_TABLE("DropTableResult"),
    DELETE("DeleteResult"),
    INSERT("InsertResult"),
    UPDATE("UpdateResult"),
    ERROR("ErrorResult"),
    SELECT_START("RowDescriptionResult"),
    SELECT_END("SelectEndResult");

    private final String responseType;

    ResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }
}
