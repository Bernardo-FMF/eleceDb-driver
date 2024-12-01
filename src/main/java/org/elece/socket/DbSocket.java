package org.elece.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getLogger(DbSocket.class);

    private final Socket socket;
    private OutputStream sender;
    private BufferedReader reader;

    public DbSocket(DriverConfig driverConfig) throws IOException {
        this.socket = new Socket(driverConfig.getHost(), driverConfig.getPort());

        this.sender = socket.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void sendRequest(SqlRequest sqlRequest) throws IOException {
        logger.info("Sending request: {}", sqlRequest);
        byte[] statementBytes = BinaryUtils.stringToBytes(sqlRequest.sqlString());
        byte[] dataBytes = new byte[Integer.BYTES + statementBytes.length];
        System.arraycopy(ByteBuffer.wrap(BinaryUtils.integerToBytes(statementBytes.length)).order(ByteOrder.LITTLE_ENDIAN).array(), 0, dataBytes, 0, Integer.BYTES);
        System.arraycopy(statementBytes, 0, dataBytes, Integer.BYTES, statementBytes.length);
        logger.debug("Sending serialized request: {}", dataBytes);
        sender.write(dataBytes);
        sender.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public String readSizedString(Integer length) throws IOException {
        char[] chars = new char[length];
        reader.read(chars);
        return new String(chars);
    }

    public void close() throws IOException {
        logger.info("Closing socket");
        sender.close();
        reader.close();
        socket.close();
    }
}
