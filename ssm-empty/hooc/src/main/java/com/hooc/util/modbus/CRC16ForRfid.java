package com.hooc.util.modbus;

import io.netty.util.internal.StringUtil;

import java.util.Arrays;

public class CRC16ForRfid {

	private final int polynomial = 0x8408;
	
	private int[] table = new int[256];
	
	public int ComputeChecksum(byte[] bytes) {
		int crc = 0xffff;
		for (int i = 0; i < bytes.length; ++i) {
			int index = (crc ^ (bytes[i] & 0xff )) % 256;
			crc = (crc >> 8) ^ table[index];
		}
		return crc;
	}
	
	public static boolean check(byte[] bytes){
		CRC16ForRfid c = new CRC16ForRfid();
		int checksum = c.ComputeChecksum(Arrays.copyOfRange(bytes, 0, bytes.length-2));
		String str = Integer.toString(checksum, 16);
		byte[] bs = CRC16.HexString2BufNoAdd(str);
		return bs[0]==bytes[bytes.length-1]&&bs[1]==bytes[bytes.length-2];
	}
	
	public static String getRfidAmmeterNO(String srcAmmeterNo){
		char[] c = srcAmmeterNo.toCharArray();
		char[] dest = new char[c.length/2];
		for(int i=1;i<c.length;i++){
			if(i%2==0)continue;
			dest[i/2]=c[i];
		}
		return new String(dest);
	}
	
		
	public CRC16ForRfid() {
		int value;
		int temp;
		for (int i = 0; i < table.length; ++i) {
			value = 0;
			temp = i;
			for (byte j = 0; j < 8; ++j) {
				if (((value ^ temp) & 0x0001) != 0) {
					value = (value >> 1) ^ polynomial;
				} else {
					value >>= 1;
				}
				temp >>= 1;
			}
			table[i] = value;
		}
	}
	
	public static void main(String[] args) {
//		CRC16ForRfid c = new CRC16ForRfid();
//		int[] arr = new int[]{0x4, 0x0, 0x1};
//		System.out.println(Integer.toString(c.ComputeChecksum(arr), 16));
//        arr = new int[]{0xB, 0x0, 0x1, 0x1, 0x1, 0x4, 0xEE, 0x35, 0x45, 0x45 };
//        System.out.println(Integer.toString(c.ComputeChecksum(arr), 16));
//		arr = new int[]{0x15,0x37,(0xAF),0x00,0x35,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x35,0x38,0x33,0x93,0x0A,0xAD,0x17,0xE6,0xF1};
//		System.out.println(Integer.toString(c.ComputeChecksum(arr), 16));
		byte[] b = new byte[]{0x15,0x37,(byte) (0xAF),0x00,0x35,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x35,0x38,0x33,(byte) 0x93,0x0A,(byte) 0xAD,0x17,(byte) 0xE6,(byte) 0xF1};
//		System.out.println(Integer.toString(c.ComputeChecksum(b), 16));
//		System.out.println(check(b));
		long t = System.currentTimeMillis();
		System.out.println(getRfidAmmeterNO(StringUtil.toHexString(b, 4, 12)));
		System.out.println(System.currentTimeMillis()-t);
	}
}

