package net.onebean.gateway.action.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.service.AccessTokenService;
import net.onebean.gateway.service.AppInfoService;
import net.onebean.gateway.service.SendSmsService;
import net.onebean.gateway.service.UagUserInfoService;
import net.onebean.gateway.vo.*;
import net.onebean.util.DateUtils;
import net.onebean.util.UagUtils;
import net.onebean.gateway.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    private UagUserInfoService uagUserInfoService;
    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private AppInfoService appInfoService;

    @PostMapping(value = "/getAccessToken",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<GetAccessTokenResponse> getAccessToken(@RequestBody @Validated GetAccTokenRequest request, BindingResult result){
        BaseResponse<GetAccessTokenResponse> response = new BaseResponse<>();
        logger.info("OauthController getToken method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(accessTokenService.getAccessToken(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "/getCurrentAppLoginType",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<String> getCurrentAppLoginType(){
        logger.info("AppInfoController getCurrentAppLoginType method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<String> response = new BaseResponse<>();
        try {
            response = response.ok(appInfoService.getCurrentAppLoginType(UagUtils.getCurrentAppId()));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "/sendLoginSms", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> sendLoginSms(@RequestBody @Validated SendLoginSmsRestReq request, BindingResult result) {
        logger.info("SalesAuthController sendLoginSms method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("SalesAuthController sendLoginSms method target = " + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
            response = response.ok(sendSmsService.sendLoginSms(request));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "checkSmsCode",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> checkSmsCode(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("checkSmsCode method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.checkSmsCode(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "smsCodeLoginRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeLoginRegister(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeLoginRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.smsCodeLoginRegister(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "smsCodeLogin",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeLogin(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeLogin method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.smsCodeLogin(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "smsCodeRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> smsCodeRegister(@RequestBody @Validated SmsCodeLoginRegisterReq req, BindingResult result) {
        logger.info("smsCodeRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.smsCodeRegister(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }



    @PostMapping(value = "passwordLogin",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> passwordLogin(@RequestBody @Validated PasswordLoginReq req, BindingResult result){
        logger.info("passwordLogin method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.passwordLogin(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }



    @PostMapping(value = "passwordRegister",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> passwordRegister(@RequestBody @Validated PasswordLoginReq req, BindingResult result){
        logger.info("passwordRegister method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.passwordRegister(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }



    @PostMapping(value = "logOut",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> uagLogOut() {
        logger.info("uagLogOut method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            CheckUagLoginStatusReq req = new CheckUagLoginStatusReq();
            req.setAppId(UagUtils.getCurrentAppId());
            req.setDeviceToken(UagUtils.getCurrentDeviceToken());
            response = response.ok(uagUserInfoService.uagLogOut(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "uagSsoLogOut",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> uagSsoLogOut(@RequestBody @Validated CheckUagLoginStatusReq req, BindingResult result) {
        logger.info("uagSsoLogOut method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.uagLogOut(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "checkUagLoginInfo",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagLoginInfo> checkUagLoginInfo(@RequestBody @Validated CheckUagLoginStatusReq req, BindingResult result) {
        logger.info("checkUagLoginInfo method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagLoginInfo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.checkUagLoginStatus(req));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

}
