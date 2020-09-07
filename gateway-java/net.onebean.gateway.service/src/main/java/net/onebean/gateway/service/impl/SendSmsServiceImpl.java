package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.model.UserSmsRecord;
import net.onebean.gateway.service.SendSmsService;
import net.onebean.gateway.service.UagInfoCacheService;
import net.onebean.gateway.service.UserSmsRecordService;
import net.onebean.gateway.vo.SendLoginSmsReq;
import net.onebean.gateway.vo.SendLoginSmsRestReq;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;
import net.onebean.util.UagUtils;
import net.onebean.util.WebUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SendSmsServiceImpl implements SendSmsService {

    private final static Logger logger = LoggerFactory.getLogger(SendSmsServiceImpl.class);

    @Autowired
    private UagInfoCacheService cacheService;
    @Autowired
    private UserSmsRecordService smsRecordService;

    private final static String UAG_LOGIN_SMS_TIME_OUT = "uag.sms.login.time.out";

    /**
     * @return 随机4位短信验证码
     */
    private static String generateVerifyCode() {
        return (int) (Math.random() * 9000 + 1000) + "";
    }

    @Override
    @Transactional
    public Boolean sendLoginSms(SendLoginSmsRestReq req) {
        logger.info(" sendLoginSms method access ,req = " + JSON.toJSONString(req, SerializerFeature.WRITE_MAP_NULL_FEATURES));
        SendLoginSmsReq cloudPram = new SendLoginSmsReq();
        String deviceToken = UagUtils.getCurrentDeviceToken();

        String uagId = UagUtils.getCurrentLoginUserId();
        if (StringUtils.isEmpty(deviceToken)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " filed of deviceToken is empty");
        }
        try {
            BeanUtils.copyProperties(cloudPram, req);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
        }
        try {
            Long timeOut = Parse.toLong(PropUtil.getInstance().getConfig(UAG_LOGIN_SMS_TIME_OUT, PropUtil.DEFLAULT_NAME_SPACE));
            cloudPram.setTimeOut(timeOut);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.READ_VALUE_FROM_APOLLO.code(), ErrorCodesEnum.READ_VALUE_FROM_APOLLO.msg() + " filed of timeOut load from apollo failure");
        }
        cloudPram.setSmsCode(generateVerifyCode());
        cloudPram.setDeviceToken(deviceToken);


        String telPhone = Optional.of(cloudPram).map(SendLoginSmsReq::getTelPhone).orElse(null);
        String smsCode = Optional.of(cloudPram).map(SendLoginSmsReq::getSmsCode).orElse(null);
        if (StringUtils.isEmpty(telPhone)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " filed of telPhone is empty");
        }
        if (StringUtils.isEmpty(smsCode)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " filed of telPhone is smsCode");
        }

        //如果发送短信太频繁 报错
        if (!cacheService.checkSmsAnHour(telPhone) || !cacheService.checkIpAddressDay(WebUtils.getIpAddress())) {
            throw new BusinessException(ErrorCodesEnum.TOO_BUSY_ERR.code(), ErrorCodesEnum.TOO_BUSY_ERR.msg());
        }


        logger.info(" sendLoginSms method sending sms");
        //发送短信验证码
        boolean sendRes = true;
//        boolean sendRes = doSendSmsCode(smsCode,telPhone);
        if (!sendRes){
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERROR.code(), ErrorCodesEnum.CLOUD_API_ERROR.msg() + " send sms fail,telPhone = "+telPhone);
        }

        logger.info(" sendLoginSms method cache redis sms");
        /*同步回写缓存信息*/
        Boolean flag = cacheService.cacheSmsInfo(cloudPram);
        if (!flag) {
            throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(), ErrorCodesEnum.PUT_CACHE_ERR.msg() + " set login sms cache failure");
        }
        //发送短信计数
        cacheService.countSmsAnHour(telPhone);
        cacheService.countIpAddressDay(WebUtils.getIpAddress());
        logger.info(" saveUserSmsRecord started");
        //保存发送短信记录
        UserSmsRecord smsRecord = new UserSmsRecord(Parse.toInt(uagId), telPhone, smsCode);
        smsRecordService.save(smsRecord);
        //记录短信到业务系统
        return true;
    }

    @Override
    public Boolean doSendSmsCode(String code, String mobile){

        return true;
    }
}
