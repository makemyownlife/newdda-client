package com.elong.pb.newdda.client.router.rule;

import java.util.List;

import static com.elong.pb.newdda.client.constants.ShardingConstants.AND_VALUE;

/**
 * Created by zhangyong on 2016/10/26.
 */
public class RangeDataSourceRule implements DataSourceRule {

    @Override
    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    @Override
    public String calculate(Long hash) {
        int realPos = (int) (hash & AND_VALUE);
        String targetDataSource = null;
        if (dataSourceList.size() == 1) {
            return dataSourceList.get(0);
        }
        //在虚拟的1024个分区中 查看位于哪个数据源分区中
        for (int i = 0; i < dataSourceRangeList.size(); i++) {
            String range = dataSourceRangeList.get(i);
            String[] arr = range.split(",");
            int start = Integer.valueOf(arr[0]);
            int end = Integer.valueOf(arr[1]);
            if (realPos >= start && realPos <= end) {
                targetDataSource = dataSourceList.get(i);
                break;
            }
        }
        return targetDataSource;
    }

    //======================================================================set get method start ==================================================================
    //数据源列表
    private List<String> dataSourceList;

    public void setDataSourceList(List<String> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    //数据源分区列表
    private List<String> dataSourceRangeList;

    public void setDataSourceRangeList(List<String> dataSourceRangeList) {
        this.dataSourceRangeList = dataSourceRangeList;
    }
//======================================================================set get method end ==================================================================

}
