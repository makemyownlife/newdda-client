package com.elong.pb.newdda.client.visitor;

import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.SqlVisitorRegistry;
import com.elong.pb.newdda.client.router.parser.visitor.VisitorLogProxy;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * sql visitor 例子
 * Created by zhangyong on 2016/8/14.
 */
public class SqlVisitorRefectUnitTest {

    @Test
    public void createInstanceTest() {
        Object object = VisitorLogProxy.enhance(SqlVisitorRegistry.getSelectVistor(DatabaseType.MySQL));
        Assert.assertNotNull(object);
    }

}
