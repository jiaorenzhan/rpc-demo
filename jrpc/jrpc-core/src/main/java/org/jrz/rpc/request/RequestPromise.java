package org.jrz.rpc.request;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;


public class RequestPromise  extends DefaultPromise {

    public RequestPromise(EventExecutor executor) {
        super(executor);
    }
}
