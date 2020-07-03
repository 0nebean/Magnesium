package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.UpsteamNameAddReq;
import net.onebean.api.mngt.api.model.UpsteamNameModifyReq;
import net.onebean.api.mngt.api.model.UpsteamNameVo;
import net.onebean.api.mngt.api.service.UpSteamNameInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UpSteamNameInfoApiFallBack implements UpSteamNameInfoApi {

    @Override
    public BaseResponse<Long> add(UpsteamNameAddReq request) {
        return null;
    }

    @Override
    public BasePaginationResponse<UpsteamNameVo> find(BasePaginationRequest<UpsteamNameAddReq> request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> update(UpsteamNameModifyReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(UpsteamNameModifyReq request) {
        return null;
    }
}
