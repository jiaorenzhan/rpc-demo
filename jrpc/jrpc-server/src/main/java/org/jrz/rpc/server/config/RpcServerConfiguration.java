package org.jrz.rpc.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RpcServerConfiguration {

    /**
     * ZK根节点名称
     */
    @Value("${rpc.server.zk.root}")
    private String zkRoot;

    /**
     * ZK地址信息
     */
    @Value("${rpc.server.zk.addr}")
    private String zkAddr;


    /**
     * RPC通讯端口
     */
    @Value("${rpc.network.port}")
    private int rpcPort;

    /**
     * Spring Boot 服务端口
     */
    @Value("${server.port}")
    private int serverPort;

    /**
     * ZK连接超时时间配置
     */
    @Value("${rpc.server.zk.timeout:10000}")
    private int connectTimeout;

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

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
