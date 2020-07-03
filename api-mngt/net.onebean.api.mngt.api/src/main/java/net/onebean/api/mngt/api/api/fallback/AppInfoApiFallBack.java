package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.AppInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class AppInfoApiFallBack implements AppInfoApi {
    @Override
    public BaseResponse<Long> add(AppInfoAddRequest request) {
        BaseResponse<Long> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<Integer> update(AppInfoModifyRequest request) {
        BaseResponse<Integer> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<Integer> delete(AppInfoVo appInfoVo) {
        BaseResponse<Integer> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<AppInfoVo> find(BasePaginationRequest<AppInfoQueryRequest> request) {
        BasePaginationResponse<AppInfoVo> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<AppInfoVo> findById(BasePaginationRequest<AppInfoQueryRequest> request) {
        BaseResponse<AppInfoVo> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<Boolean> bindingApi(AppBindingApiReq request) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<Boolean> unBindingApi(AppBindingApiReq request) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BaseResponse<Boolean> syncAppApiRelationship() {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

}
