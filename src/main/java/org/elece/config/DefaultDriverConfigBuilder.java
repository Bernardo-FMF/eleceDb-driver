package org.elece.config;

public class DefaultDriverConfigBuilder {
    private int port;
    private String host;

    private DefaultDriverConfigBuilder() {
        // private constructor
    }

    public static DefaultDriverConfigBuilder builder() {
        return new DefaultDriverConfigBuilder();
    }

    public DefaultDriverConfigBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public DefaultDriverConfigBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public DefaultDriverConfig build() {
        return new DefaultDriverConfig(port, host);
    }
}
