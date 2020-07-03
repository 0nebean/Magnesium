package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.ApiInfoApiFallBack;
import net.onebean.api.mngt.api.model.*;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = ApiInfoApiFallBack.class)
public interface ApiInfoApi {

    @PostMapping(value = "/cloud/apiInfo/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated ApiInfoAddReq request);

    @PostMapping(value = "/cloud/apiInfo/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<ApiInfoVo> find(@RequestBody BasePaginationRequest<ApiInfoFindReq> request);

    @PostMapping(value = "/cloud/apiInfo/update",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> update(@RequestBody @Validated ApiModifyReq request);

    @PostMapping(value = "/cloud/apiInfo/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody @Validated ApiModifyReq request);

    @PostMapping(value = "/cloud/apiInfo/findById",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<ApiInfoVo> findById(@RequestBody @Validated ApiModifyReq request);

    @PostMapping(value = "/cloud/apiInfo/findApiByAppId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppApiRelationshipResp> findApiByAppId(@RequestBody @Validated FindApiByAppIdReq request);

    @PostMapping(value = "/cloud/apiInfo/findAppApiRelationshipByServerIdAndAppId",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(@RequestBody @Validated AppApiRelationshipReq request);
}
