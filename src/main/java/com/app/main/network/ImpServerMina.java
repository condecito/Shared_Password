package com.app.main.network;

import com.app.main.message.MessageCodeFactory;
import com.app.main.message.MessageNetwork;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import org.apache.mina.core.service.IoAcceptor;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public final class ImpServerMina extends IoHandlerAdapter implements IApplicationSocket {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImpServerMina.class);
    private static IoAcceptor acceptor;
    @Autowired
    private InetSocketAddress inetSocketAddress;

    @Override
    public boolean start() {
        boolean succsess = false;
        try {
            acceptor = new NioSocketAcceptor();

            //  acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new MessageCodeFactory()));
            //  acceptor.getSessionConfig().setReadBufferSize(2048);
            // acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.setHandler(this);
            acceptor.bind(this.inetSocketAddress);

            succsess = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean stop() {

        return false;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOGGER.info("SERVER CLOSED");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {

        LOGGER.info("SERVER OPENED");
        //  final String welcomeStr = "welcome from server \n";

        //   String newContent = new String(bf.array());
        MessageNetwork msj = new MessageNetwork();
        msj.setMessage("hello from Server");
        //  final ByteBuffer bf = ByteBuffer.wrap(msj.toString().getBytes());
        //  System.out.println("se envio " + msj);

        session.write(msj);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       // System.out.println("mensaje en el server:" + message);
        MessageNetwork n = (MessageNetwork) message;
        System.out.println(n.getMessage());
//        InputStream socketInputStream=castToInputStream(message);
//        ObjectInputStream ois = new ObjectInputStream(socketInputStream);
//        System.out.println(ois.readObject());
        //  MessageNetwork msj = (MessageNetwork) ois.readObject();
        //  System.out.println(msj.getMessage());
        //  String g = new String(message.toString());
        //  final ByteBuffer bf = (ByteBuffer) message;
        //  String newContent = new String(bf.array());

        //String converted = new String(bf.array(), "UTF-8");
        //System.out.println("recivido en server : " + newContent);
//        byte[] bytes = new byte[in.remaining()];
//        in.get(bytes);
//        String newContent = new String(bytes, "UTF-8");
        //  System.out.println("los datos son :" + (String) message);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("SERVER IDLE " + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.info(cause.toString());
    }

}
