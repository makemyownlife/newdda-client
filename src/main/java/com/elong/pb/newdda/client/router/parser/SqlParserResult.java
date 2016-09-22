package com.elong.pb.newdda.client.router.parser;

import com.elong.pb.newdda.client.router.result.merge.MergeContext;
import com.elong.pb.newdda.client.router.result.router.ConditionContext;
import com.elong.pb.newdda.client.router.result.router.RouterContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 海上钢琴师 ,一山还比一山高 切记骄傲 自满
 * Created by zhangyong on 2016/8/14.
 */
public class SqlParserResult {

    private final RouterContext routeContext = new RouterContext();

    private final List<ConditionContext> conditionContexts = new ArrayList<ConditionContext>();

    private final MergeContext mergeContext = new MergeContext();

    //===================================================================== get set method start ======================================================================

    public RouterContext getRouteContext() {
        return routeContext;
    }

    public List<ConditionContext> getConditionContexts() {
        return conditionContexts;
    }

    public MergeContext getMergeContext() {
        return mergeContext;
    }

    //===================================================================== get set method end ======================================================================

}
