package net.qqxh.sunflow.flowclient.client;

import com.alibaba.fastjson.JSONObject;

public interface DefinitionClient {
    /**
     * restful 发起流程
     * @param variables
     * @param flowid
     * @return
     */
    JSONObject start(JSONObject variables, String flowid);
}
