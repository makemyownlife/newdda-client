package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.exception.DatabaseTypeUnsupportedException;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlDeleteVisitor;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlInsertVisitor;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlSelectVisitor;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlUpdateVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL访问器注册表.
 */
public final class SqlVisitorRegistry {

    private static final Map<DatabaseType, Class<? extends SQLASTOutputVisitor>> SELECT_REGISTRY = new HashMap<DatabaseType, Class<? extends SQLASTOutputVisitor>>(DatabaseType.values().length);

    private static final Map<DatabaseType, Class<? extends SQLASTOutputVisitor>> INSERT_REGISTRY = new HashMap<DatabaseType, Class<? extends SQLASTOutputVisitor>>(DatabaseType.values().length);

    private static final Map<DatabaseType, Class<? extends SQLASTOutputVisitor>> UPDATE_REGISTRY = new HashMap<DatabaseType, Class<? extends SQLASTOutputVisitor>>(DatabaseType.values().length);

    private static final Map<DatabaseType, Class<? extends SQLASTOutputVisitor>> DELETE_REGISTRY = new HashMap<DatabaseType, Class<? extends SQLASTOutputVisitor>>(DatabaseType.values().length);

    static {
        registerSelectVistor();
        registerInsertVistor();
        registerUpdateVistor();
        registerDeleteVistor();
    }

    private static void registerSelectVistor() {
        SELECT_REGISTRY.put(DatabaseType.H2, MySqlSelectVisitor.class);
        SELECT_REGISTRY.put(DatabaseType.MySQL, MySqlSelectVisitor.class);
        // TODO 其他数据库
    }

    private static void registerInsertVistor() {
        INSERT_REGISTRY.put(DatabaseType.H2, MySqlInsertVisitor.class);
        INSERT_REGISTRY.put(DatabaseType.MySQL, MySqlInsertVisitor.class);
        // TODO 其他数据库
    }

    private static void registerUpdateVistor() {
        UPDATE_REGISTRY.put(DatabaseType.H2, MySqlUpdateVisitor.class);
        UPDATE_REGISTRY.put(DatabaseType.MySQL, MySqlUpdateVisitor.class);
        // TODO 其他数据库
    }

    private static void registerDeleteVistor() {
        DELETE_REGISTRY.put(DatabaseType.H2, MySqlDeleteVisitor.class);
        DELETE_REGISTRY.put(DatabaseType.MySQL, MySqlDeleteVisitor.class);
        // TODO 其他数据库
    }

    /**
     * 获取SELECT访问器.
     *
     * @param databaseType 数据库类型
     * @return SELECT访问器
     */
    public static Class<? extends SQLASTOutputVisitor> getSelectVistor(final DatabaseType databaseType) {
        return getVistor(databaseType, SELECT_REGISTRY);
    }

    /**
     * 获取INSERT访问器.
     *
     * @param databaseType 数据库类型
     * @return INSERT访问器
     */
    public static Class<? extends SQLASTOutputVisitor> getInsertVistor(final DatabaseType databaseType) {
        return getVistor(databaseType, INSERT_REGISTRY);
    }

    /**
     * 获取UPDATE访问器.
     *
     * @param databaseType 数据库类型
     * @return UPDATE访问器
     */
    public static Class<? extends SQLASTOutputVisitor> getUpdateVistor(final DatabaseType databaseType) {
        return getVistor(databaseType, UPDATE_REGISTRY);
    }

    /**
     * 获取DELETE访问器.
     *
     * @param databaseType 数据库类型
     * @return DELETE访问器
     */
    public static Class<? extends SQLASTOutputVisitor> getDeleteVistor(final DatabaseType databaseType) {
        return getVistor(databaseType, DELETE_REGISTRY);
    }

    private static Class<? extends SQLASTOutputVisitor> getVistor(final DatabaseType databaseType, final Map<DatabaseType, Class<? extends SQLASTOutputVisitor>> registry) {
        if (!registry.containsKey(databaseType)) {
            throw new DatabaseTypeUnsupportedException(databaseType.name());
        }
        return registry.get(databaseType);
    }
}
