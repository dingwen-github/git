package net.qqxh.sunflow.flowclient.client;

import com.alibaba.fastjson.JSONArray;

import java.util.Map;

public interface TaskClient {
    public JSONArray list(Map params);
}
