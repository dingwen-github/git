package net.qqxh.sunflow.flowclient;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调用流程客户端
 *
 * @author hfz
 * @create 2019-06-14 16:14
 **/
public class FlowClient {
    /**
     * post请求传输map数据
     *
     * @param url url地址
     * @param map map数据
     * @param encoding 编码方式
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPostDataByMap(String url, Map<String, Object> map, String encoding) throws ClientProtocolException, IOException {
        String result = "";
        try {
            // 创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            // 装填参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), (String)entry.getValue()));
                }
            }

            // 设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));

            // 设置header信息
            // 指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            // 执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = httpClient.execute(httpPost);
            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            // 释放链接
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * post请求传输json数据
     *
     * @param url url地址
     * @param json json数据
     * @param encoding 编码方式
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }

    /**
     * get请求传输数据
     *
     * @param url
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }


}
