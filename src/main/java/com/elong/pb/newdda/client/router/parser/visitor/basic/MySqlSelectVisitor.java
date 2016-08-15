package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.ast.SQLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MySQL的SELECT语句访问器.
 */
public class MySqlSelectVisitor extends AbstractMySqlVisitor {

    private final static Logger logger = LoggerFactory.getLogger(MySqlSelectVisitor.class);

    public void preVisit(SQLObject x) {
        logger.info("preVisit:" + x.getClass() + ":" + x);
    }

}
