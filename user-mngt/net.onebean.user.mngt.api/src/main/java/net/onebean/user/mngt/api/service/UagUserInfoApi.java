package net.onebean.user.mngt.api.service;

import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.fallback.UagOperatorLogApiFallBack;
import net.onebean.user.mngt.api.fallback.UagUserInfoApiFallBack;
import net.onebean.user.mngt.api.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-mngt", fallback = UagUserInfoApiFallBack.class)
public interface UagUserInfoApi {

    @PostMapping(value = "/uagUserInfo/cloud/findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList();

    @PostMapping(value = "/uagUserInfo/cloud/initUagAccountTable", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<Boolean> initUagAccountTable(@RequestBody @Validated InitUagAccountTableReq request);

    @PostMapping(value = "/uagUserInfo/cloud/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UagUserInfoVo> find(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request);

    @PostMapping(value = "/uagUserInfo/cloud/findById",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<UagUserInfoVo> findById(@RequestBody @Validated BasePaginationRequest<FindUagUserInfoReq> request);

    @PostMapping(value = "/uagUserInfo/cloud/addAccount",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> addAccount(@RequestBody @Validated AddAccountReq request);

    @PostMapping(value = "/uagUserInfo/cloud/toggleIsLockStatus",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> toggleIsLockStatus(@RequestBody @Validated ToggleIsLockStatusReq request);

    @PostMapping(value = "/uagUserInfo/cloud/restPassword",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> restPassword(@RequestBody @Validated ResetUserPasswordReq request);

    @PostMapping(value = "/uagUserInfo/cloud/modify",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> modify(@RequestBody @Validated UserInfoModifyReq request);
}
