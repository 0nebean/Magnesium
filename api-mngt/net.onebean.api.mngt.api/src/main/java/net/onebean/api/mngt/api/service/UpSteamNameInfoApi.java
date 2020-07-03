package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.UpSteamNameInfoApiFallBack;
import net.onebean.api.mngt.api.model.UpsteamNameAddReq;
import net.onebean.api.mngt.api.model.UpsteamNameModifyReq;
import net.onebean.api.mngt.api.model.UpsteamNameVo;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = UpSteamNameInfoApiFallBack.class)
public interface UpSteamNameInfoApi {


    @PostMapping(value = "/cloud/upSteamName/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated UpsteamNameAddReq request);

    @PostMapping(value = "/cloud/upSteamName/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UpsteamNameVo> find(@RequestBody BasePaginationRequest<UpsteamNameAddReq> request);

    @PostMapping(value = "/cloud/upSteamName/update",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> update(@RequestBody @Validated UpsteamNameModifyReq request);

    @PostMapping(value = "/cloud/upSteamName/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody @Validated UpsteamNameModifyReq request);
}
