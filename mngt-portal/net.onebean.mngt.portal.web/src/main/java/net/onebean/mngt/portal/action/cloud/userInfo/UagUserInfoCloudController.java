package net.onebean.mngt.portal.action.cloud.userInfo;

import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.mngt.portal.common.ErrorCodesEnum;
import net.onebean.sso.sdk.core.UagSsoUtils;
import net.onebean.user.mngt.api.model.*;
import net.onebean.user.mngt.api.service.UagUserInfoApi;
import net.onebean.user.mngt.log.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
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
@RequestMapping("uagUserInfo")
public class UagUserInfoCloudController {


    private final static Logger logger = LoggerFactory.getLogger(UagUserInfoCloudController.class);

    @Autowired
    private UagUserInfoApi uagUserInfoApi;

    @PostMapping(value = "find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UagUserInfoVo> find(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("find method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagUserInfoVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = uagUserInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagUserInfoVo> findById(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagUserInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = uagUserInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("findById method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "添加用户账户")
    @PostMapping(value = "addAccount",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> addAccount(@RequestBody @Validated AddAccountReq request, BindingResult result) {
        logger.info("addAccount method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = uagUserInfoApi.addAccount(request);
        } catch (BusinessException e) {
            logger.info("addAccount method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("addAccount method catch Exception e = ",e);
        }
        return response;
    }


    @PostMapping(value = "findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList() {
        logger.info("findUagUserAppList method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<FindUagUserAppListResp> response = new BasePaginationResponse<>();
        try {
            response = uagUserInfoApi.findUagUserAppList();
        } catch (BusinessException e) {
            logger.info("findUagUserAppList method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findUagUserAppList method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "更改账户锁定状态")
    @PostMapping(value = "toggleIsLockStatus",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> toggleIsLockStatus(@RequestBody @Validated ToggleIsLockStatusReq request, BindingResult result) {
        logger.info("toggleIsLockStatus method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = uagUserInfoApi.toggleIsLockStatus(request);
        } catch (BusinessException e) {
            logger.info("toggleIsLockStatus method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("toggleIsLockStatus method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "重置账户密码")
    @PostMapping(value = "restPassword",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> restPassword(@RequestBody @Validated ResetUserPasswordReq request, BindingResult result) {
        logger.info("restPassword method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = uagUserInfoApi.restPassword(request);
        } catch (BusinessException e) {
            logger.info("restPassword method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("restPassword method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "编辑账户信息")
    @PostMapping(value = "modify",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> modify(@RequestBody @Validated UserInfoModifyReq request, BindingResult result) {
        logger.info("modify method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = uagUserInfoApi.modify(request);
        } catch (BusinessException e) {
            logger.info("modify method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("modify method catch Exception e = ",e);
        }
        return response;
    }
}

