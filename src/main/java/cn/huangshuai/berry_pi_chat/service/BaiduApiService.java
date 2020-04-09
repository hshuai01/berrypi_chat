package cn.huangshuai.berry_pi_chat.service;

import cn.huangshuai.berry_pi_chat.utils.Constants;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class BaiduApiService {

    //设置APPID/AK/SK
    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";

    public void getVoice(String text){
        AipSpeech client = new AipSpeech(APP_ID,API_KEY,SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        TtsResponse res = client.synthesis(text, "zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, Constants.RES_MP3_FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }

    }


    public String getText(String pcmFilePath){
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        JSONObject res = client.asr(pcmFilePath, "pcm", 16000, null);
        if (res.get("result") == null){
            return "请重试";
        }
        String result = res.get("result").toString();
        result = result.substring(result.indexOf("\"")+1,result.lastIndexOf("\""));
        System.out.println(result);
        return result;
    }

//    public static void main(String[] args) {
//        BaiduApiService baiduApiService = new BaiduApiService();
//        baiduApiService.getText("D:\\ffmpeg\\ffmpeg\\bin\\hello.pcm");
//    }
}
