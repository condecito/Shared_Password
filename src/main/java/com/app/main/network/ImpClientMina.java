package com.app.main.network;

import com.app.main.message.MessageCodeFactory;
import com.app.main.message.MessageNetwork;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpClientMina extends IoHandlerAdapter implements IApplicationSocket {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImpClientMina.class);
    private static final long CONNECT_TIMEOUT = 5000;
    private static final boolean USE_CUSTOM_CODEC = false;
    private static final int PORT = 1521;
    private static IoSession session;
    //
    private static NioSocketConnector connector;

    @Override
    public boolean start() {
        boolean succsess = false;
        try {
            connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
            //  connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            //connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new MessageCodeFactory()));
            connector.setHandler(this);
            ConnectFuture futureConnector = connector.connect(new InetSocketAddress("127.0.0.1", PORT));
            futureConnector.awaitUninterruptibly();
            session = futureConnector.getSession();

            //   session.write(new MessageNetwork());
            succsess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void send() throws IOException {
        byte[] yourBytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(new MessageNetwork());
            out.flush();
            yourBytes = bos.toByteArray();

        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

//        for (int i = 0; i <= yourBytes.length-1; i++) {
//            IoBuffer buffer = IoBuffer.allocate(4);
//            buffer.put(yourBytes[i]);
//            buffer.flip();
//            session.write(buffer);
//
//            if (i % 10000 == 0) {
//                System.out.println("Sent " + i + " messages");
//            }
//        }
    }

    @Override
    public boolean stop() {

        return false;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOGGER.info("CLIENT CLOSED");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOGGER.info("CLIENT OPENED");

        MessageNetwork msj = new MessageNetwork();
        msj.setMessage("hello from client");
        // OutputStream os = session.get;

        // final String welcomeStr = "welcome from client\n";
        final ByteBuffer bf = ByteBuffer.wrap(msj.toString().getBytes());
        // String newContent = new String(bf.array());
        // System.out.println("se envio " + msj);
        session.write(msj);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       // System.out.println("mensaje en el cliente:" + message);
        MessageNetwork n = (MessageNetwork) message;
        System.out.println(n.getMessage());
        // InputStream socketInputStream = castToInputStream(message);
        //  ObjectInputStream ois = new ObjectInputStream(socketInputStream);
        //  System.out.println(ois.readObject());
        // MessageNetwork msj = (MessageNetwork) ois.readObject();
        //  System.out.println(msj.getMessage());
        // String g = new String(message.toString());
        // final ByteBuffer bf = (ByteBuffer) message;
        //   String newContent = new String(bf.array());

        //String converted = new String(bf.array(), "UTF-8");
        // System.out.println("recivido en cliente : " + newContent);
//        ByteBuffer in=(ByteBuffer) message;
//        byte[] bytes = new byte[in.remaining()];
//        in.get(bytes);
        //  String newContent = new String(bytes, "UTF-8");
        //System.out.println("los datos son :" + (String) message);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE CLIENTE" + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.info(cause.toString());
    }

}
