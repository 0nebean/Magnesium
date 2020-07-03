package net.onebean.api.mngt.action.cloud.oauth;

import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.service.AccessTokenService;
import net.onebean.api.mngt.service.AppInfoService;
import net.onebean.api.mngt.vo.GetAccTokenRequest;
import net.onebean.api.mngt.vo.GetAccessTokenResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.util.DateUtils;
import net.onebean.util.UagUtils;
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
public class OauthController {

    private final static Logger logger = LoggerFactory.getLogger(OauthController.class);

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
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
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
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }
}
