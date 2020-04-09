package cn.huangshuai.berry_pi_chat.controller;

import cn.huangshuai.berry_pi_chat.service.BaiduApiService;
import cn.huangshuai.berry_pi_chat.service.Mp3PlayService;
import cn.huangshuai.berry_pi_chat.service.TuringService;
import cn.huangshuai.berry_pi_chat.utils.Constants;
import cn.huangshuai.berry_pi_chat.utils.FFmpegUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/hs")
public class PostController {

    @Autowired
    private TuringService turingService;

    @Autowired
    private BaiduApiService baiduApiService;

    @Autowired
    private Mp3PlayService mp3PlayService;


    @PostMapping("/chat")
    public String chat(MultipartFile file){
        String msg = "";
        try {
            //保存用户录音文件
            mp3PlayService.saveMp3(file);
            //将用户录音文件转格式
            FFmpegUtil fFmpegUtil = new FFmpegUtil(Constants.FFMPEG_PATH);
            fFmpegUtil.compoundPcm(Constants.MP3_FILE_PATH,Constants.PCM_FILE_PATH);
            //获取用户输入
            String text = baiduApiService.getText(Constants.PCM_FILE_PATH);
            msg = turingService.getChatMsg(text);
            baiduApiService.getVoice(msg);
            //播放音频
            mp3PlayService.play(Constants.RES_MP3_FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }



}
