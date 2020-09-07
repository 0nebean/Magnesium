package net.onebean.user.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.model.UserSmsRecord;
import net.onebean.user.mngt.service.UserSmsRecordService;
import net.onebean.user.mngt.vo.SendLoginSmsReq;
import net.onebean.user.mngt.vo.SendLoginSmsRestReq;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.user.mngt.service.SendSmsService;
import net.onebean.user.mngt.service.UagInfoCacheService;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;
import net.onebean.util.UagUtils;
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

    private final static String UAG_LOGIN_SMS_TIME_OUT = "uag.login.sms.time.out";


    /**
     * @return 随机4位短信验证码
     */
    private static String generateVerifyCode() {
        return (int)(Math.random() * 9000 + 1000) + "";
    }

    @Override
    @Transactional
    public Boolean sendLoginSms(SendLoginSmsRestReq req) {
        logger.info(" sendLoginSms method access ,req = "+ JSON.toJSONString(req, SerializerFeature.WRITE_MAP_NULL_FEATURES));
        SendLoginSmsReq cloudPram = new SendLoginSmsReq();
        String deviceToken = UagUtils.getCurrentDeviceToken();
        String uagId = UagUtils.getCurrentLoginUserId();
        if (StringUtils.isEmpty(deviceToken)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of deviceToken is empty");
        }
        try {
            BeanUtils.copyProperties(cloudPram,req);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        try {
            Long timeOut = Parse.toLong(PropUtil.getInstance().getConfig(UAG_LOGIN_SMS_TIME_OUT, PropUtil.DEFLAULT_NAME_SPACE));
            cloudPram.setTimeOut(timeOut);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.READ_VALUE_FROM_APOLLO.code(),ErrorCodesEnum.READ_VALUE_FROM_APOLLO.msg()+" filed of timeOut load from apollo failure");
        }
        cloudPram.setSmsCode(generateVerifyCode());
        cloudPram.setDeviceToken(deviceToken);


        String telPhone = Optional.of(cloudPram).map(SendLoginSmsReq::getTelPhone).orElse(null);
        String smsCode = Optional.of(cloudPram).map(SendLoginSmsReq::getSmsCode).orElse(null);
        if (StringUtils.isEmpty(telPhone)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is empty");
        }
        if (StringUtils.isEmpty(smsCode)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is smsCode");
        }

        logger.info(" sendLoginSms method sending sms");
        //todo 发送短信验证码


        logger.info(" sendLoginSms method cache redis sms");
        /*同步回写缓存信息*/
        Boolean flag = cacheService.cacheSmsInfo(cloudPram);
        if (!flag){
            throw new BusinessException(ErrorCodesEnum.PUT_CACHE_ERR.code(),ErrorCodesEnum.PUT_CACHE_ERR.msg()+" set login sms cache failure");
        }

        UserSmsRecord smsRecord = new UserSmsRecord(Parse.toInt(uagId),telPhone,smsCode);
        smsRecordService.save(smsRecord);
        return true;
    }
}
