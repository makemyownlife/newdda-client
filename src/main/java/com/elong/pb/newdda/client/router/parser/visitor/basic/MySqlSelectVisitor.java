package com.elong.pb.newdda.client.router.parser.visitor.basic;

/**
 * MySQL的SELECT语句访问器.
 */
public class MySqlSelectVisitor extends AbstractMySqlVisitor{

    public MySqlSelectVisitor(Appendable appender) {
        super(appender);
    }

}
