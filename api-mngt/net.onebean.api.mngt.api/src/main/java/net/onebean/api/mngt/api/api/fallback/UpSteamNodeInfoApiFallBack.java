package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.UpSteamNodeInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class UpSteamNodeInfoApiFallBack implements UpSteamNodeInfoApi {
    @Override
    public BaseResponse<Long> add(UpSteamNodeAddReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<UpSteamNodeVo> find(BasePaginationRequest<UpSteamNodeAddReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> update(UpSteamNodeModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(UpSteamNodeModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<UpSteamNodeVo> findById(UpSteamNodeModifyReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<UpSteamVersionVo> findVersionListByNodeName(FindVersionListByNodeNameReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> setRunningStatusDownByDevOpsK8sNotificationVo(SetRunningStatusDown request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> updateSelectedVersion(UpdateSelectedVersionReq request) {
        return null;
    }
}
