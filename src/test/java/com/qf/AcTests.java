package com.qf;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * cwy 2019/7/15 20:02
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml"
})
public class AcTests {


    @Test
    public void sendSMS(){
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient("bf4463a129d359b07d20130026da2d30").init();
        // 生成6位随机数
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        // 将正确的验证码设置到session域中

        System.out.println(code);
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, "15735180864");
        param.put(YunpianClient.TEXT, "【云片网】您的验证码是" + code);
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
        //释放clnt
        clnt.close();

    }

}
