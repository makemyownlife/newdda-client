package com.elong.pb.newdda.client.util;

import org.testng.annotations.Test;

/**
 * Created by zhangyong on 16/10/13.
 */
public class IdGeneratorUnitTest {

    @Test
    public void testGetUniqueId() {
        long uniqueId = IdGenerator.getUniqueId(121, 21);
        System.out.println(uniqueId);
    }

}
