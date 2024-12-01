package org.elece.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class BinaryUtils {
    private BinaryUtils() {
        // private constructor
    }

    public static byte[] stringToBytes(final String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] integerToBytes(final Integer value) {
        return ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
    }
}
