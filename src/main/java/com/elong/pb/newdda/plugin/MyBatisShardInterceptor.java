package com.elong.pb.newdda.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

/**
 * mybatis分区拦截器
 * User: zhangyong
 * Date: 2016/3/26
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class MyBatisShardInterceptor implements Interceptor {

    private static Logger LOGGER = LoggerFactory.getLogger(MyBatisShardInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        LOGGER.error("target:{}", target);
        if (target instanceof Executor) {
            return target;
            //return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
