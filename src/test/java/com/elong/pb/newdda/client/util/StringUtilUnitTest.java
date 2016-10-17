package com.elong.pb.newdda.client.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by zhangyong on 2016/10/17.
 */
public class StringUtilUnitTest {

    @Test
    public void testHashCode() {
        String a = "1";
        Assert.assertEquals(a.hashCode(), StringUtil.hash(a, 0, a.length()));
    }

}
