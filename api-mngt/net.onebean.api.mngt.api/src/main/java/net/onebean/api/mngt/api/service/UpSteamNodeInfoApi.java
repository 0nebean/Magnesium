package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.UpSteamNodeInfoApiFallBack;
import net.onebean.api.mngt.api.model.*;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = UpSteamNodeInfoApiFallBack.class)
public interface UpSteamNodeInfoApi {


    @PostMapping(value = "/cloud/upSteamNodeInfo/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated UpSteamNodeAddReq request);

    @PostMapping(value = "/cloud/upSteamNodeInfo/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UpSteamNodeVo> find(@RequestBody BasePaginationRequest<UpSteamNodeAddReq> request);


    @PostMapping(value = "/cloud/upSteamNodeInfo/update",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> update(@RequestBody @Validated UpSteamNodeModifyReq request);

    @PostMapping(value = "/cloud/upSteamNodeInfo/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody @Validated UpSteamNodeModifyReq request);

    @PostMapping(value = "/cloud/upSteamNodeInfo/findById",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<UpSteamNodeVo> findById(@RequestBody @Validated UpSteamNodeModifyReq request);


    @PostMapping(value = "/cloud/upSteamNodeInfo/findVersionListByNodeName",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UpSteamVersionVo> findVersionListByNodeName(@RequestBody @Validated FindVersionListByNodeNameReq request);

    @PostMapping(value = "/cloud/upSteamNodeInfo/setRunningStatusDownByDevOpsK8sNotificationVo",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> setRunningStatusDownByDevOpsK8sNotificationVo(@RequestBody @Validated SetRunningStatusDown request);

    @PostMapping(value = "/cloud/upSteamNodeInfo/updateSelectedVersion",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> updateSelectedVersion(@RequestBody @Validated UpdateSelectedVersionReq request);
}
