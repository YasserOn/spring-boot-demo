package com.xkcoding.sms.util.ali;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliYunSMSUtil {

    public static Integer sendMessage(String accessKeyId, String accessKeySecret,
                                      String userPhone, String signName, String msgTemplateId, String jsonParam) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", userPhone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", msgTemplateId);
        request.putQueryParameter("TemplateParam", jsonParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (200 != response.getHttpStatus()) {
                log.error("短息发送失败 错误信息= {}", response.getData());
                throw new RuntimeException(String.format("短息发送失败 错误信息= {%s}", response.getData()));
            }
            log.info("{} 短息发送成功", userPhone);
            return 1;

        } catch (ClientException e) {
            log.error("短息发送失败 错误码={} 错误信息= {}", e.getErrCode(), e.getMessage());
            throw new RuntimeException(String.format("短息发送失败 错误码={%s} 错误信息= {%s}}", e.getErrCode(), e.getMessage()));
        }
    }
}
