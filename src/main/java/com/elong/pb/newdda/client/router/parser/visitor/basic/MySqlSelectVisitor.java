package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.sun.tools.corba.se.idl.constExpr.BinaryExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MySQL的SELECT语句访问器.
 */
public class MySqlSelectVisitor extends AbstractMySqlVisitor {

    private final static Logger logger = LoggerFactory.getLogger(MySqlSelectVisitor.class);

    public void preVisit(SQLObject x) {
        if (logger.isDebugEnabled()) {
            logger.debug(x.getClass().getSimpleName() + ":" + x);
        }
    }

    @Override
    public boolean visit(final MySqlSelectQueryBlock x) {
        if (x.getFrom() != null) {
            x.getFrom().setParent(x);
            x.getFrom().accept(this);
        }
        if (x.getWhere() != null) {
            x.getWhere().setParent(x);
            x.getWhere().accept(this);
        }
        return false;
    }

    //============================================================================   重写相关的visit astnode  start================================================================



    //============================================================================   重写相关的visit astnode  end================================================================

}
