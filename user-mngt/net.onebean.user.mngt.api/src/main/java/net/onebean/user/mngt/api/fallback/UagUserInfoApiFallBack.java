package net.onebean.user.mngt.api.fallback;

import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.model.*;
import net.onebean.user.mngt.api.service.UagUserInfoApi;

public class UagUserInfoApiFallBack implements UagUserInfoApi {
    @Override
    public BaseResponse<Boolean> initUagAccountTable(InitUagAccountTableReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<UagUserInfoVo> find(BasePaginationRequest<FindUagUserInfoReq> request) {
        return null;
    }

    @Override
    public BaseResponse<UagUserInfoVo> findById(BasePaginationRequest<FindUagUserInfoReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Long> addAccount(AddAccountReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> toggleIsLockStatus(ToggleIsLockStatusReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> restPassword(ResetUserPasswordReq request) {
        return null;
    }

    @Override
    public BaseResponse<Boolean> modify(UserInfoModifyReq request) {
        return null;
    }
}
