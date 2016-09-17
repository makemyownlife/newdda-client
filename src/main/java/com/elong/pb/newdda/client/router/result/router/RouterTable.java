package com.elong.pb.newdda.client.router.result.router;


/**
 * Created by zhangyong on 16/8/31.
 */
public class RouterTable {

    private final String name;

    private final String alias;

    public RouterTable(final String name, final String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + (this.alias == null ? 0 : this.alias.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof RouterTable)) {
            return false;
        }

        RouterTable other = (RouterTable) obj;
        if (this.name == null && other.getName() != null) {
            return false;
        }
        if(!this.name.equals(other.getName())) {
            return false;
        }

        if (this.alias == null && other.getAlias() != null) {
            return false;
        }
        if(!this.alias.equals(other.getAlias())) {
            return false;
        }

        return true;
    }

}
