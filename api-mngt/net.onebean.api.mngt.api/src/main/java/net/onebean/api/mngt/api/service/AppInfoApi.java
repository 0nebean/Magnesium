package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.AppInfoApiFallBack;
import net.onebean.api.mngt.api.model.*;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = AppInfoApiFallBack.class)
public interface AppInfoApi {

    @PostMapping(value = "/cloud/appInfo/findUagUserAppList",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList();


    @PostMapping(value = "/cloud/appInfo/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated AppInfoAddRequest request);


    @PostMapping(value = "/cloud/appInfo/update",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> update(@RequestBody @Validated AppInfoModifyRequest request);


    @PostMapping(value = "/cloud/appInfo/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody AppInfoVo appInfoVo);


    @PostMapping(value = "/cloud/appInfo/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<AppInfoVo> find(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request);


    @PostMapping(value = "/cloud/appInfo/findById",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppInfoVo> findById(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request);


    @PostMapping(value = "/cloud/appInfo/bindingApi",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> bindingApi(@RequestBody @Validated AppBindingApiReq request);


    @PostMapping(value = "/cloud/appInfo/unBindingApi",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> unBindingApi(@RequestBody @Validated AppBindingApiReq request);


    @PostMapping(value = "/cloud/appInfo/syncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> syncAppApiRelationship();
}
