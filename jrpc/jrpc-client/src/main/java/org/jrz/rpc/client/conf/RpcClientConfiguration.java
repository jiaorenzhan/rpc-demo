package org.jrz.rpc.client.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RpcClientConfiguration {

    @Value("${rpc.client.zk.root}")
    private String zkRoot;

    @Value("${rpc.client.zk.addr}")
    private String zkAddr;

    @Value("${server.port}")
    private String znsClientPort;

    @Value("${rpc.client.api.package}")
    private String rpcClientApiPackage;

/*    @Value("${rpc.cluster.strategy}")
    private String rpcClientClusterStrategy;*/

    @Value("${rpc.client.zk.timeout:10000}")
    private Integer connectTimeout;

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public String getZnsClientPort() {
        return znsClientPort;
    }

    public void setZnsClientPort(String znsClientPort) {
        this.znsClientPort = znsClientPort;
    }

    public String getRpcClientApiPackage() {
        return rpcClientApiPackage;
    }

    public void setRpcClientApiPackage(String rpcClientApiPackage) {
        this.rpcClientApiPackage = rpcClientApiPackage;
    }

/*    public String getRpcClientClusterStrategy() {
        return rpcClientClusterStrategy;
    }

    public void setRpcClientClusterStrategy(String rpcClientClusterStrategy) {
        this.rpcClientClusterStrategy = rpcClientClusterStrategy;
    }*/

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
