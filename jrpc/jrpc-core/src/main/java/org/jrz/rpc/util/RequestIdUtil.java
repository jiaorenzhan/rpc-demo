package org.jrz.rpc.util;

public class RequestIdUtil {

    public static String requestId() {
//        UUID.randomUUID().toString();
        return GlobalIDGenerator.getInstance().nextStrId();
    }
}
