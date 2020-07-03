package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.ServerInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class ServerInfoApiFallBack implements ServerInfoApi {

    @Override
    public BaseResponse<Boolean> add(ServerInfoAddReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<ServerInfoVo> find(BasePaginationRequest<ServerInfoAddReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> update(ServerInfoModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> delete(ServerInfoModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<ServerInfoVo> findById(ServerInfoModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> syncServerMachineNodeInfo() {
        return null;
    }

    @Override
    public BasePaginationResponse<ServerBasicInfo> findServerByName(FindServerByNameReq request) {
        return null;
    }
}
