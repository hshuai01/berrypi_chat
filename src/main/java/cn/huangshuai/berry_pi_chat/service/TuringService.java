package cn.huangshuai.berry_pi_chat.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class TuringService {
    //存储APIkey
    private final String API_KEY = "";
    //存储接口请求地址
    private final String API_URL = "http://openapi.tuling123.com/openapi/api/v2";
    // 用户id
    private final String USER_ID = "";

    /**
     * 获取可以传输的正确的json格式的请求字符串
     * @param reqMes 输入内容
     * @return
     */
    public  String getReqMes(String reqMes){
        // 请求json，里面包含reqType，perception，userInfo
        JSONObject reqJson = new JSONObject();
        // 输入类型:0-文本(默认)、1-图片、2-音频
        int reqType = 0;
        reqJson.put("reqType",reqType);

        // 输入信息,里面包含inputText，inputImage，selfInfo
        JSONObject perception = new JSONObject();
        // 输入的文本信息
        JSONObject inputText = new JSONObject();
        inputText.put("text",reqMes);
        perception.put("inputText",inputText);

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey",API_KEY);
        userInfo.put("userId",USER_ID);

        reqJson.put("perception",perception);
        reqJson.put("userInfo",userInfo);
        return reqJson.toString();
    }

    public  String turingPost(String url, String reqMes) {

        String status = "";
        String responseStr = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty("x-adviewrtb-version", "2.1");
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpUrlConnection.getOutputStream());
            // 发送请求参数
            out.write(reqMes);
            // flush输出流的缓冲
            out.flush();
            httpUrlConnection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                responseStr += line;
            }
            status = new Integer(httpUrlConnection.getResponseCode()).toString();
//            System.out.println("status=============="+status);
//            System.out.println("response=============="+responseStr);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return responseStr;
    }

    public  String getResultMes(String turingStr){

        try {
            JSONObject resultStr = JSONObject.fromObject(turingStr);

            List<Object> results = (List<Object>) resultStr.get("results");

            JSONObject resultObj = JSONObject.fromObject(results.get(0));

            JSONObject values = JSONObject.fromObject(resultObj.get("values"));

            return values.get("text").toString();
        }catch (Exception e){
            e.getMessage();
            return "暂时不支持这样的功能哟！";
        }


    }


    //最终获得的聊天消息
    public String getChatMsg(String msg){
        //获得的返回字符串
        String turingPostStr = turingPost(API_URL, getReqMes(msg));
        String json = getResultMes(turingPostStr);
        return json;
    }


}
