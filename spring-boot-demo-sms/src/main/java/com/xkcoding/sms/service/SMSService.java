package com.xkcoding.sms.service;

public interface SMSService {

    /**
     * 发送短信
     *
     * @param userPhone      接收用户手机号码
     *                       批量发送相同内容短信则用 "," 隔开
     * @param msgTemplateKey 短信模板KEY(详见SMSConstant)
     * @param msgContent     短信内容(根据对应的短信模板传入相应的数据)
     * @return 1:代表短息发送成功
     */
    Integer sendMessage(String userPhone, String msgTemplateKey, String... msgContent);
}
