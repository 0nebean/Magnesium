package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.ServerMachineNodeInfoApiFallBack;
import net.onebean.api.mngt.api.api.fallback.UnLoginAccessWhiteListApiFallback;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListAddReq;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListModfiyReq;
import net.onebean.api.mngt.api.model.UnLoginAccessApiWhiteListVo;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = UnLoginAccessWhiteListApiFallback.class)
public interface UnLoginAccessWhiteListApi {


    @PostMapping(value = "/cloud/unLoginAccessWhiteList/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated UnLoginAccessWhiteListAddReq request);

    @PostMapping(value = "/cloud/unLoginAccessWhiteList/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody UnLoginAccessWhiteListModfiyReq appInfoVo);

    @PostMapping(value = "/cloud/unLoginAccessWhiteList/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UnLoginAccessApiWhiteListVo> find(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request);

    @PostMapping(value = "/cloud/unLoginAccessWhiteList/findUnBindingData",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<UnLoginAccessApiWhiteListVo> findUnBindingData(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request);

}
