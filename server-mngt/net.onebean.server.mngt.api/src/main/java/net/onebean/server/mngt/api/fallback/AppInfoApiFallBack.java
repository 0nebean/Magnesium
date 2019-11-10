package net.onebean.server.mngt.api.fallback;

import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.server.mngt.api.model.*;
import net.onebean.server.mngt.api.service.AppInfoApi;
import org.springframework.stereotype.Component;

@Component
public class AppInfoApiFallBack implements AppInfoApi {
    @Override
    public BaseResponse<AppInfoCloudVo> findByAppIdAndSecret(FindAppByAppIdAndSecretReq request) {
        BaseResponse<AppInfoCloudVo> baseResponse = new BaseResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<AppInfoSyncCloudResp> findBindAppInfo() {
        BasePaginationResponse<AppInfoSyncCloudResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }


    @Override
    public BasePaginationResponse<FindBasicMenuListResp> findBasicMenuList() {
        BasePaginationResponse<FindBasicMenuListResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }

    @Override
    public BasePaginationResponse<FindUagUserAppListResp> findUagUserAppList() {
        BasePaginationResponse<FindUagUserAppListResp> baseResponse = new BasePaginationResponse<>();
        baseResponse.setErrCode("999");
        baseResponse.setErrMsg("fall back");
        return baseResponse;
    }
}
