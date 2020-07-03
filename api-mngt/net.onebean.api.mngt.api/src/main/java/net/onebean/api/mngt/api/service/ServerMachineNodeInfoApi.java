package net.onebean.api.mngt.api.service;

import net.onebean.api.mngt.api.api.fallback.ServerMachineNodeInfoApiFallBack;
import net.onebean.api.mngt.api.model.ServerMachineNodeAddReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeInfoModifyReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeVo;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "api-mngt",fallback = ServerMachineNodeInfoApiFallBack.class)
public interface ServerMachineNodeInfoApi {

    @PostMapping(value = "/cloud/serverMachineNodeInfo/add",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Long> add(@RequestBody @Validated ServerMachineNodeAddReq request);

    @PostMapping(value = "/cloud/serverMachineNodeInfo/find",produces = {"application/json"},consumes = {"application/json"})
    BasePaginationResponse<ServerMachineNodeVo> find(@RequestBody BasePaginationRequest<ServerMachineNodeAddReq> request);

    @PostMapping(value = "/cloud/serverMachineNodeInfo/update",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> update(@RequestBody @Validated ServerMachineNodeInfoModifyReq request);

    @PostMapping(value = "/cloud/serverMachineNodeInfo/delete",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Integer> delete(@RequestBody @Validated ServerMachineNodeInfoModifyReq request);

    @PostMapping(value = "/cloud/serverMachineNodeInfo/findById",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<ServerMachineNodeVo> findById(@RequestBody @Validated ServerMachineNodeInfoModifyReq request);



}
