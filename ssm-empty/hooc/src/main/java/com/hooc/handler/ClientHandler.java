package com.hooc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hooc.util.modbus.CRC16;


@Component
@Scope("prototype")
public class ClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf m = (ByteBuf) msg; // (1)
                String a="";
        try {
        	System.out.println(System.currentTimeMillis()+"Msg Form Client: " + m.toString(CharsetUtil.UTF_8));
        	if(m.isReadable()){
        		char[] c = new char[m.readableBytes()];
        		for(int i=0;i<c.length;i++){
        			c[i]=(char) m.readByte();
        		}
        		a = new String(c);
        		if("010300000006C5C8".equals(a)){
        			a="01030CDAE541BF0000434800004348F704";
        		}else{
        			a="0103140548428A0000432F0000432F000040A000004348D763";
        		}
        	}
        	byte[] xx = CRC16.HexString2BufNoAdd(a);
        	//System.out.println(System.currentTimeMillis()+"   "+a);
        	ctx.writeAndFlush(Unpooled.copiedBuffer(xx));
//            ctx.close();
        } finally {
            m.release();
        }
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    	System.out.println("Connected");
//    	String b="MIGEM12312312121212";
//    	byte[] bytes = b.getBytes();
//    	String a="01030CDAE541BF0000434800004348F704";
//    	byte[] xx = CRC16.HexString2BufNoAdd(a);
//    	byte[] update = CRC16.update(bytes);
//    	ctx.writeAndFlush(Unpooled.copiedBuffer(b,CharsetUtil.UTF_8));
//    	String msg= "This msg from clien!";
//    	byte[] sendBuf = CRC16.getSendBuf(msg);
//    	ctx.writeAndFlush(Unpooled.copiedBuffer(sendBuf));
//        ctx.writeAndFlush(Unpooled.copiedBuffer("This msg from clien!", CharsetUtil.UTF_8));
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    

}
