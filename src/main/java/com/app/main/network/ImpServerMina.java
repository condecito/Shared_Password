package com.app.main.network;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;

import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.springframework.beans.factory.annotation.Autowired;

public final class ImpServerMina implements IApplicationSocket {
    private static NioDatagramAcceptor acceptor;
    private InetSocketAddress inetSocketAddress;

    @Autowired
    public ImpServerMina(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    public boolean start() {
        boolean succsess = false;
        try {
            acceptor = new NioDatagramAcceptor();
            acceptor.bind(this.inetSocketAddress);
            succsess=true;
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    @Override
    public boolean stop() {

        return false;
    }

    @Override
    public boolean recive() {
        throw new UnsupportedOperationException("This method is not available on Server Mode");
    }

    @Override
    public boolean transmit() {

        return false;
    }

}
