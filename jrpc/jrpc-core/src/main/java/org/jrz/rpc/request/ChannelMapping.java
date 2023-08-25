package org.jrz.rpc.request;

import io.netty.channel.Channel;


public class ChannelMapping {
    //服务提供者节点，格式: ip:port
    private String ipWithPort;
    //服务提供者ip
    private String ip = "127.0.0.1";
    //服务提供者端口
    private int port;
    //客户端连接通道channel
    private Channel channel;

    public ChannelMapping(String ip, int port, Channel channel) {
        this.ip = ip;
        this.port = port;
        this.channel = channel;
    }

    public String getIpWithPort() {
        return ip+":"+port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelMapping that = (ChannelMapping) o;
        return port == that.port && ip.equals(that.ip) ;
    }


    public void setIpWithPort(String ipWithPort) {
        this.ipWithPort = ipWithPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
