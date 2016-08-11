package com.elong.pb.newdda.client.parser;

import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.Token;
import org.testng.annotations.Test;

public class SQLLexerUnitTest {

    @Test
    public void testLexer() throws Exception {
        String sql = "SELECT * FROM T WHERE F1 = ? ORDER BY F2";
        Lexer lexer = new Lexer(sql);
        for (; ; ) {
            lexer.nextToken();
            Token tok = lexer.token();

            if (tok == Token.IDENTIFIER) {
                System.out.println(tok.name() + "\t\t" + lexer.stringVal());
            } else if (tok == Token.LITERAL_INT) {
                System.out.println(tok.name() + "\t\t" + lexer.numberString());
            } else {
                System.out.println(tok.name() + "\t\t\t" + tok.name);
            }

            if (tok == Token.WHERE) {
                System.out.println("where pos : " + lexer.pos());
            }

            if (tok == Token.EOF) {
                break;
            }
        }
    }

    @Test
    public void test_lexer2() throws Exception {
        String sql = "SELECT substr('''a''bc',0,3) FROM dual";
        Lexer lexer = new Lexer(sql);
        for (; ; ) {
            lexer.nextToken();
            Token tok = lexer.token();

            if (tok == Token.IDENTIFIER) {
                System.out.println(tok.name() + "\t\t" + lexer.stringVal());
            } else if (tok == Token.LITERAL_INT) {
                System.out.println(tok.name() + "\t\t" + lexer.numberString());
            } else if (tok == Token.LITERAL_CHARS) {
                System.out.println(tok.name() + "\t\t" + lexer.stringVal());
            } else {
                System.out.println(tok.name() + "\t\t\t" + tok.name);
            }

            if (tok == Token.EOF) {
                break;
            }
        }
    }

}
