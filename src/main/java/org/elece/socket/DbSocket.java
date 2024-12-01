package org.elece.socket;

import org.elece.config.DriverConfig;
import org.elece.request.SqlRequest;
import org.elece.utils.BinaryUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class DbSocket {
    private final Socket socket;
    private OutputStream sender;
    private BufferedReader reader;

    public DbSocket(DriverConfig driverConfig) throws IOException {
        this.socket = new Socket(driverConfig.getHost(), driverConfig.getPort());

        this.sender = socket.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void sendRequest(SqlRequest sqlRequest) throws IOException {
        byte[] statementBytes = BinaryUtils.stringToBytes(sqlRequest.sqlString());
        byte[] dataBytes = new byte[Integer.BYTES + statementBytes.length];
        System.arraycopy(ByteBuffer.wrap(BinaryUtils.integerToBytes(statementBytes.length)).order(ByteOrder.BIG_ENDIAN).array(), 0, dataBytes, 0, Integer.BYTES);
        System.arraycopy(statementBytes, 0, dataBytes, Integer.BYTES, statementBytes.length);
        sender.write(dataBytes);
        sender.flush();
    }
}
