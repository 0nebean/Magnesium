package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.ServerInfoApiFallBack;
import net.onebean.api.mngt.api.model.*;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = ServerInfoApiFallBack.class)
public interface ServerInfoApi {

    @PostMapping(value = "/cloud/serverInfo/add", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<Boolean> add(@RequestBody @Validated ServerInfoAddReq request);

    @PostMapping(value = "/cloud/serverInfo/find", produces = {"application/json"}, consumes = {"application/json"})
    BasePaginationResponse<ServerInfoVo> find(@RequestBody BasePaginationRequest<ServerInfoAddReq> request);


    @PostMapping(value = "/cloud/serverInfo/update", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<Boolean> update(@RequestBody @Validated ServerInfoModifyReq request);

    @PostMapping(value = "/cloud/serverInfo/delete", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<Boolean> delete(@RequestBody @Validated ServerInfoModifyReq request);

    @PostMapping(value = "/cloud/serverInfo/findById", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<ServerInfoVo> findById(@RequestBody @Validated ServerInfoModifyReq request);


    @PostMapping(value = "/cloud/serverInfo/syncServerMachineNodeInfo", produces = {"application/json"}, consumes = {"application/json"})
    BaseResponse<Boolean> syncServerMachineNodeInfo();

    @PostMapping(value = "/cloud/serverInfo/findServerByName", produces = {"application/json"}, consumes = {"application/json"})
    BasePaginationResponse<ServerBasicInfo> findServerByName(@RequestBody @Validated FindServerByNameReq request);

}
