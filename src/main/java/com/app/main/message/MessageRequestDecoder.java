/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.main.message;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 *
 * @author ronya
 */
public class MessageRequestDecoder extends CumulativeProtocolDecoder {
    
    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() >= 12) {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            String message = in.getString(decoder);
            
            MessageNetwork request = new MessageNetwork(message,"nonthing");
           
            out.write(request);
            return true;
        } else {
            return false;
        }
    }
    
}
