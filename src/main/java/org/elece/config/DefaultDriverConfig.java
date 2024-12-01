package org.elece.config;

public class DefaultDriverConfig implements DriverConfig {
    private final int port;
    private final String host;

    public DefaultDriverConfig(int port, String host) {
        this.port = port;
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }
}
