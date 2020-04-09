package utils;

import cn.huangshuai.berry_pi_chat.BerryPiChatApplication;

import cn.huangshuai.berry_pi_chat.service.TuringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BerryPiChatApplication.class)
public class TuringServiceTest {

    @Test
    public void test() {
       TuringService util = new TuringService();
       System.out.println(util.getChatMsg("我的妈呀"));
    }
}