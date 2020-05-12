package com.xkcoding.sms.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.xkcoding.sms.config.SMSProperties;
import com.xkcoding.sms.service.SMSService;
import com.xkcoding.sms.util.ronglian.CCPRestSDK;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class RongLianSMSServiceImpl implements SMSService {
    private String appId;
    private String accountSid;
    private String accountToken;
    private Map<String, String> template;


    public RongLianSMSServiceImpl(SMSProperties.RongLianConfig rongLianConfig) {
        BeanUtils.copyProperties(rongLianConfig, this);
    }

    /**
     * 容联平台<br/>
     * 发送短信 / 批量发送相同内容短信<br/>
     * 批量发送不同内容短信，则循环调用该方法
     *
     * @param userPhone      接收用户手机号码 /
     *                       批量发送相同内容短信则用 "," 隔开<br/>
     * @param msgTemplateKey 短信模板ID(详见SMSConstant)
     * @param msgContent     短信内容(根据对应的短信模板传入相应的数据)
     * @return 1:代表短息发送成功
     */
    @Override
    @Async("taskExecutor")
    public Integer sendMessage(String userPhone, String msgTemplateKey, String... msgContent) {
        String msgTemplateId = template.get(msgTemplateKey);
        if (StringUtils.isEmpty(msgTemplateId)) {
            throw new RuntimeException("短信模板不存在");
        }
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(this.getAccountSid(), this.getAccountToken());// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(this.getAppId());// 初始化应用ID
        HashMap<String, Object> result = restAPI.sendTemplateSMS(userPhone, msgTemplateId, msgContent);

        if (!"000000".equals(result.get("statusCode"))) {
            //异常返回输出错误码和错误信息
            log.error("短息发送失败 错误码={} 错误信息= {}", result.get("statusCode"), result.get("statusMsg"));
            throw new RuntimeException(String.format("短息发送失败 错误码={%s} 错误信息= {%s}",
                    result.get("statusCode"), result.get("statusMsg")));
        }
        log.info("{} 短息发送成功", userPhone);
        return 1;
    }
}
