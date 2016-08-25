package com.zr.app;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzr14796 on 2016/6/14.
 */
public class ListChangValue {
    public static List<Map<String, String>> changeListValue(List<Map<String, String>> oriList){
        for (Map recordMap : oriList){
            if (recordMap.containsKey("per")){
                String per =EcsUtil.nvl(recordMap.get("per"));
                per = EcsUtil.abandonPercent(per);
                recordMap.put("per", per);

            }
        }
        return oriList;
    }
}
