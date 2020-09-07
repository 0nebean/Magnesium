package net.onebean.gateway.action.userInfo;

import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.service.PrivateAuthAppInfoService;
import net.onebean.gateway.service.UagUserInfoService;
import net.onebean.gateway.vo.*;
import net.onebean.sso.sdk.core.UagSsoUtils;
import net.onebean.gateway.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
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

import java.util.Optional;

@RestController
@RequestMapping("uagUserInfo")
public class UagUserInfoCloudController {


    private final static Logger logger = LoggerFactory.getLogger(UagUserInfoCloudController.class);

    @Autowired
    private UagUserInfoService uagUserInfoService;

    @Autowired
    private PrivateAuthAppInfoService privateAuthAppInfoService;

    @PostMapping(value = "find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UagUserInfoVo> find(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("UagUserInfoCloudController find method access "+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagUserInfoVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagUserInfoReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(uagUserInfoService.findByFindUagUserInfoReq(param,page,sort),page);
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UagUserInfoVo> findById(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request, BindingResult result) {
        logger.info("UagUserInfoCloudController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UagUserInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagUserInfoReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            response = response.ok(uagUserInfoService.findVoById(param));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @UagOperationLog(description = "添加用户账户")
    @PostMapping(value = "addAccount",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> addAccount(@RequestBody @Validated AddAccountReq request, BindingResult result) {
        logger.info("UagUserInfoCloudController addAccount method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = response.ok(uagUserInfoService.addAccountFromGatePage(request));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList() {
        logger.info("findUagUserAppList method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<FindUagUserAppListResp> response = new BasePaginationResponse<>();
        try {
            response = response.ok(privateAuthAppInfoService.findAll2FindUagUserAppListResp());
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "更改账户锁定状态")
    @PostMapping(value = "toggleIsLockStatus",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> toggleIsLockStatus(@RequestBody @Validated ToggleIsLockStatusReq request, BindingResult result) {
        logger.info("UagUserInfoCloudController toggleIsLockStatus method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = response.ok(uagUserInfoService.toggleIsLockStatus(request));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @UagOperationLog(description = "重置账户密码")
    @PostMapping(value = "restPassword",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> restPassword(@RequestBody @Validated ResetUserPasswordReq request, BindingResult result) {
        logger.info("UagUserInfoCloudController restPassword method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            UagSsoUtils.setUagUserInfoBySession(request);
            response = response.ok(uagUserInfoService.restPassword(request));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "编辑账户信息")
    @PostMapping(value = "modify",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> modify(@RequestBody @Validated UserInfoModifyReq request, BindingResult result) {
        logger.info("UagUserInfoCloudController modify method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = response.ok(uagUserInfoService.modify(request));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }
}

