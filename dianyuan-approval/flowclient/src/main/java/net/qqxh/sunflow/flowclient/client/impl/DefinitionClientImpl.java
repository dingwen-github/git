package net.qqxh.sunflow.flowclient.client.impl;

import com.alibaba.fastjson.JSONObject;
import net.qqxh.sunflow.flowclient.client.DefinitionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefinitionClientImpl implements DefinitionClient {
    private static String flowserver="http://localhost:8080/";
    @Autowired
    RestTemplate restTemplate;
    public void demo(){
        /*restTemplate.getForObject("http://localhost:8080/user?userId=id",String.class);
        ResponseEntity<DefinitionClientImpl> response = restTemplate.getForEntity("http://localhost/get/{id}", DefinitionClientImpl.class, "id");
        User user = restTemplate.getForObject("http://localhost/get/{id}", User.class, id);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost/save", user, String.class);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/demo", HttpMethod.POST,request,String.class);*/
    }

    /**
     * restful 发起流程
     * @param variables
     * @param flowid
     * @return
     */
    @Override
    public JSONObject start(JSONObject variables,String flowid){
        return restTemplate.postForObject(flowserver+"/process-definition/{id}/start",variables,JSONObject.class,flowid);
    }

}
