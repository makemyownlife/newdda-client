package com.elong.pb.newdda.client.visitor;

import com.dangdang.ddframe.rdb.sharding.parser.result.router.SQLBuilder;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by zhangyong on 16/9/11.
 */
public class SqlBuilderUnitTest {

    @Test
    public void testAppend() throws IOException {
        SQLBuilder result = new SQLBuilder();
        result.append("SELECT * FROM ");
        result.appendToken("order");
        System.out.println(result.toString());
    }

}
