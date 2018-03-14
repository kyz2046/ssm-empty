package com.hooc.util.modbus;

import java.math.BigDecimal;

public class ByteUtil {

	/**
	 * byte转bigdecimal 适用于第一代温湿度计
	 * 
	 * @param b
	 * @return
	 */
	public static BigDecimal byteToBigDecimal(byte[] b) {
		int t = (int) b[0];
		t <<= 8;
		t += (b[1] & 0XFF);
		return BigDecimal.valueOf(t);
	}

	/**
	 * 头尾互换 比如{12345}变成{54321}
	 * @param b
	 */
	public static void HeadToTailExchange(byte[] b){
		for(int i=0;i<b.length/2;i++){
			byte x = b[i];
			byte y = b[b.length-1-i];
			x^=y;
			y^=x;
			x^=y;
			b[i]=x;
			b[b.length-1-i]=y;
		}
		
	}
	
	/**
	 * byte转float
	 * 
	 * @param 4字节byte[] b
	 * @return
	 */
	public static float byteToFloat(byte[] b) {
		int l;
		int index = 0;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		// System.out.println(l);
		return Float.intBitsToFloat(l);
	}

	private ByteUtil() {
	}

	public static void main(String[] args) {
		
		String a = "311003140011";
		byte[] ammeterNo = CRC16.HexString2BufNoAdd(a);
		System.out.println(a);
		ByteUtil.HeadToTailExchange(ammeterNo);
		System.out.println(CRC16.getBufHexStr(ammeterNo));
	}
}
