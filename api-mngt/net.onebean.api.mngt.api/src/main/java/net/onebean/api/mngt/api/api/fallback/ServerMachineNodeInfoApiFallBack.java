package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.ServerMachineNodeAddReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeInfoModifyReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeVo;
import net.onebean.api.mngt.api.service.ServerMachineNodeInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class ServerMachineNodeInfoApiFallBack implements ServerMachineNodeInfoApi {

    @Override
    public BaseResponse<Long> add(ServerMachineNodeAddReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<ServerMachineNodeVo> find(BasePaginationRequest<ServerMachineNodeAddReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> update(ServerMachineNodeInfoModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(ServerMachineNodeInfoModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<ServerMachineNodeVo> findById(ServerMachineNodeInfoModifyReq request) {
        return null;
    }
}
