package com.xkcoding.sms.service.impl;

import cn.hutool.json.JSONUtil;
import com.xkcoding.sms.config.SMSProperties;
import com.xkcoding.sms.service.SMSService;
import com.xkcoding.sms.util.ali.AliYunSMSUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class ALiSMSServiceImpl implements SMSService {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private Map<String, SMSProperties.ALiConfig.ALiTemplate> template;


    public ALiSMSServiceImpl(SMSProperties.ALiConfig aLiConfig) {
        BeanUtils.copyProperties(aLiConfig, this);
    }

    @Override
    public Integer sendMessage(String userPhone, String msgTemplateKey, String... msgContent) {
        SMSProperties.ALiConfig.ALiTemplate template = this.template.get(msgTemplateKey);
        if (null == template) {
            throw new RuntimeException("短信模板不存在");
        }
        List<String> paramList = template.getParamList();
        if (CollectionUtils.isEmpty(paramList)) {
            throw new RuntimeException("短信模板参数length非法");
        }
        if (msgContent.length != paramList.size()) {
            throw new RuntimeException("短信模板参数与msgContent 长度不同");
        }
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++) {
            map.put(paramList.get(i), msgContent[i]);
        }
        return AliYunSMSUtil.sendMessage(accessKeyId, accessKeySecret, userPhone, signName, template.getCode(), JSONUtil.toJsonStr(map));
    }
}
