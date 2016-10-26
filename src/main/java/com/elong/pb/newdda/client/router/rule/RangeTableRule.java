package com.elong.pb.newdda.client.router.rule;

import java.util.List;

import static com.elong.pb.newdda.client.constants.ShardingConstants.AND_VALUE;

public class RangeTableRule implements TableRule {

    @Override
    public String calculate(String logicTableName, Long hash) {
        String targetTableName = null;
        int realPos = (int) (hash & AND_VALUE);
        if (tableRangeList != null) {
            int tableRangeSize = tableRangeList.size();
            for (int index = 0; index < tableRangeSize; index++) {
                String range = tableRangeList.get(index);
                String[] arr = range.split(",");
                int start = Integer.valueOf(arr[0]);
                int end = Integer.valueOf(arr[1]);
                if (realPos >= start && realPos <= end) {
                    String prefix = "";
                    if (index >= 0 && index < 10) {
                        prefix = "000";
                    }
                    if (index >= 10 && index < 100) {
                        prefix = "00";
                    }
                    if (index >= 100 && index < 1000) {
                        prefix = "0";
                    }
                    targetTableName = logicTableName + "_" + prefix + index;
                    break;
                }
            }
        }
        return targetTableName;
    }

    //======================================================================set get method start ==================================================================

    private List<String> tableRangeList;

    public List<String> getTableRangeList() {
        return tableRangeList;
    }

    public void setTableRangeList(List<String> tableRangeList) {
        this.tableRangeList = tableRangeList;
    }

    //======================================================================set get method end ==================================================================

}
