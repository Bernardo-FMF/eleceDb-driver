package org.elece.config;

import java.util.Objects;

public class DefaultDriverConfigBuilder {
    private Integer port;
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

    private int getPort() {
        return Objects.requireNonNullElse(port, 3000);
    }

    private String getHost() {
        return Objects.requireNonNullElse(host, "127.0.0.1");
    }

    public DefaultDriverConfig build() {
        return new DefaultDriverConfig(getPort(), getHost());
    }
}
