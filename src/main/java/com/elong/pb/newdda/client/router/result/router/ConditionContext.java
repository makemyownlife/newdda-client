package com.elong.pb.newdda.client.router.result.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhangyong on 16/9/15.
 */
public class ConditionContext {

    private final static Logger logger = LoggerFactory.getLogger(ConditionContext.class);

    private final Map<RouterColumn, RouterCondition> conditions = new LinkedHashMap<RouterColumn, RouterCondition>();

}
