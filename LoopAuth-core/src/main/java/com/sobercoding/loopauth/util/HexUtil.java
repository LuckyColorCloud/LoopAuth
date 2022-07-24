package com.sobercoding.loopauth.util;

/**
 * @ClassName HexUtil
 * @Description 二进制、十六进制互转
 * @Author gezi
 * @Email a1151575020@163.com
 * @Since 2022/7/24 11:58
 * @Version 1.0
 */
public class HexUtil {
    private static final char[] HEX_CHARS = {
            '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
    };

    /**
     * @param bytes: 二进制数组
     * @Author gezi
     * @Description: byte[] -> 16
     * @Since 2022/7/24 12:38
     * @return java.lang.String
     */
    public static String encodeHexStr(byte[] bytes){
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHARS[b >>> 4 & 0xf];
            buf[index++] = HEX_CHARS[b & 0xf];
        }
        return new String(buf);
    }
    /**
     * @param hexStr: 十六进制字符串
     * @Author gezi
     * @Description: 16 -> byte[]
     * @Since 2022/7/24 12:37
     * @return byte[]
     */
    public static byte[] decodeHex(String hexStr){
        if (hexStr == null || hexStr.length() == 0){
            return null;
        }
        if (hexStr.length() % 2 != 0){
            hexStr = "0" + hexStr;
        }
        char[] chars = hexStr.toCharArray();
        int len = chars.length / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int x = i * 2;
            bytes[i] = (byte)Integer.parseInt(
                    String.valueOf(new char[]{chars[x],chars[x + 1]}),16
            );
        }
        return bytes;
    }
}
