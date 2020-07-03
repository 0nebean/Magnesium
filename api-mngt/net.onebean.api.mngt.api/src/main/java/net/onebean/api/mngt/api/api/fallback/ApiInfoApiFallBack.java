package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.ApiInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class ApiInfoApiFallBack implements ApiInfoApi {
    @Override
    public BaseResponse<Long> add(ApiInfoAddReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<ApiInfoVo> find(BasePaginationRequest<ApiInfoFindReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> update(ApiModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(ApiModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<ApiInfoVo> findById(ApiModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<AppApiRelationshipResp> findApiByAppId(FindApiByAppIdReq request) {
        return null;
    }

    @Override
    public BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(AppApiRelationshipReq request) {
        return null;
    }
}
