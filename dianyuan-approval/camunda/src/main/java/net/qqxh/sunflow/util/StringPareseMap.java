package net.qqxh.sunflow.util;

import java.util.HashMap;
import java.util.Map;

/**
 * url参数转map
 *
 * @author
 * @create 2019-06-14 17:50
 **/
public class StringPareseMap {
    /**
     * @param urlparam 带分隔的url参数
     * @return
     */
    public static Map<String,Object> Split(String urlparam){
        Map<String,Object> map = new HashMap<String,Object>();
        String[] param =  urlparam.split("&");
        for(String keyvalue:param){
            String[] pair = keyvalue.split("=");
            if(pair.length==2){
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}
