package com.elong.pb.newdda.client.router.result.router;


/**
 * Created by zhangyong on 16/8/31.
 */
public class Table {

    private final String name;

    private final String alias;

    public Table(final String name, final String alias) {
       this.name = name;
        this.alias =alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

}
