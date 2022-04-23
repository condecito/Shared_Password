/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.main.message;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 *
 * @author ronya
 */
public class MessageRequestEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        MessageNetwork msj = (MessageNetwork) message;
        IoBuffer buffer = IoBuffer.allocate(msj.toString().length());
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder s = charset.newEncoder();
        buffer.putString(msj.getMessage(), s);
        buffer.flip();
        out.write(buffer);
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        System.out.println(" no have nothing to dispose");
    }

}
