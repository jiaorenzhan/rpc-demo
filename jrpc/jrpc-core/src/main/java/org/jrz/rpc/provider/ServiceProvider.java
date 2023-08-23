package org.jrz.rpc.provider;


import java.io.Serializable;

public class ServiceProvider implements Serializable {
    private String serviceName;
    private String serverIp;
    private int rpcPort;
    private int networkPort;
    private long timeout;
    // the weight of service provider
    private int weight;


    public ServiceProvider(String serviceName, String serverIp, int rpcPort, int networkPort, long timeout, int weight) {
        this.serviceName = serviceName;
        this.serverIp = serverIp;
        this.rpcPort = rpcPort;
        this.networkPort = networkPort;
        this.timeout = timeout;
        this.weight = weight;
    }

    public ServiceProvider(String serviceName, String serverIp, int rpcPort) {
        this.serviceName = serviceName;
        this.serverIp = serverIp;
        this.rpcPort = rpcPort;
    }

    public ServiceProvider() {

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    public int getNetworkPort() {
        return networkPort;
    }

    public void setNetworkPort(int networkPort) {
        this.networkPort = networkPort;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "serviceName='" + serviceName + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", rpcPort=" + rpcPort +
                ", networkPort=" + networkPort +
                ", timeout=" + timeout +
                ", weight=" + weight +
                '}';
    }
}
