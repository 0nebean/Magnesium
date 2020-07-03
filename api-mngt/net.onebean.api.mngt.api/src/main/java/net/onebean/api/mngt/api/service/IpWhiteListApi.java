package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.IpWhiteListApiFallBack;
import net.onebean.api.mngt.api.model.IpWhiteListAddReq;
import net.onebean.api.mngt.api.model.IpWhiteListModifyRequest;
import net.onebean.api.mngt.api.model.IpWhiteListQueryReq;
import net.onebean.api.mngt.api.model.IpWhiteListVo;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = IpWhiteListApiFallBack.class)
public interface IpWhiteListApi {

    @PostMapping(value = "/cloud/ipWhiteList/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated IpWhiteListAddReq request);

    @PostMapping(value = "/cloud/ipWhiteList/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody @Validated IpWhiteListModifyRequest request);

    @PostMapping(value = "/cloud/ipWhiteList/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<IpWhiteListVo> find(@RequestBody BasePaginationRequest<IpWhiteListQueryReq> request);
}
