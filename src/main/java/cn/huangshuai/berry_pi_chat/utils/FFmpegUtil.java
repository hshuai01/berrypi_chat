package cn.huangshuai.berry_pi_chat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFmpegUtil {

    //程序路径
    private String ffmpegPath;

    public FFmpegUtil(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public void compoundPcm(String mp3FilePath,String pcmFilePath) throws IOException {
        //ffmpeg -y -i input.mp3 -acodec pcm_s16le -f s16le -ac 2 -ar 16000 hello.pcm
        List<String> command = new ArrayList<>();
        //指令输入
        command.add(ffmpegPath);
        command.add("-y");
        command.add("-i");
        command.add(mp3FilePath);
        command.add("-acodec");
        command.add("pcm_s16le");
        command.add("-f");
        command.add("s16le");
        command.add("-ac");
        command.add("1");
        command.add("-ar");
        command.add("16000");
        command.add(pcmFilePath);

        ProcessBuilder builder= new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = br.readLine())!= null){

        }

        if (br != null){
            br.close();
        }
        if (inputStreamReader != null){
            inputStreamReader.close();
        }
        if (errorStream != null){
            errorStream.close();
        }

    }

//    public static void main(String[] args) {
//        FFmpegUtil fFmpegUtil = new FFmpegUtil("D:\\ffmpeg\\ffmpeg\\bin\\ffmpeg");
//        try {
//            fFmpegUtil.compoundPcm("D:\\ffmpeg\\ffmpeg\\bin\\input.mp3","D:\\ffmpeg\\ffmpeg\\bin\\out.pcm");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
