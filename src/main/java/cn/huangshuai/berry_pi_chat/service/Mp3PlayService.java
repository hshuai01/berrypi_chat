package cn.huangshuai.berry_pi_chat.service;

import cn.huangshuai.berry_pi_chat.utils.Constants;
import javazoom.jl.player.Player;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;

@Service
public class Mp3PlayService {

    /**
     * 播放mp3
     * @param fileName
     */
    public void play(String fileName) {
        try {
            Player player;
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(fileName));
            player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * 将录音文件保存到本地
     * @param file
     */
    public void saveMp3(MultipartFile file){
        String fileName = Constants.MP3_FILE_PATH;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null && file.getSize() > 0){
                File outFile = new File(fileName);
                fileOutputStream = new FileOutputStream(outFile);
                inputStream = file.getInputStream();
                IOUtils.copy(inputStream,fileOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
