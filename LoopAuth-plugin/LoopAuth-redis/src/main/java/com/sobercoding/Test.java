package com.sobercoding;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author: Weny
 * @date: 2022/8/13
 * @see: com.sobercoding
 * @version: v1.0.0
 */
public class Test {

    public static void main(String[] args) {
        RedisHelper redisHelper = new RedisHelper();
        String setValue1 = redisHelper.setJson("test1", "testValue1");
        System.out.println("setValue1 = " + setValue1);
        String setValue2 = redisHelper.setJson("test2", "testValue2", 2);
        System.out.println("setValue2 = " + setValue2);
        long test3 = redisHelper.expire("test1", 1);
        System.out.println("test3 = " + test3);
        String getValue1 = redisHelper.getJson("test1");
        String getValue2 = redisHelper.getJson("test2");
        System.out.println("test4 getValue1 = " + getValue1 + ", getValue2 = " + getValue2);
        boolean delete1 = redisHelper.delete("test1");
        System.out.println("test5 delete1 = " + delete1);
        boolean hasKey1 = redisHelper.hasKey("test1");
        boolean hasKey2 = redisHelper.hasKey("test2");
        System.out.println("test6 hasKey1 = " + hasKey1 + ", hasKey2 = " + hasKey2);
    }
}
