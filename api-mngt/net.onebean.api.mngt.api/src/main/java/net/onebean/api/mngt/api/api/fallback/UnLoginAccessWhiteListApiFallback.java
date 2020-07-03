package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.UnLoginAccessApiWhiteListVo;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListAddReq;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListModfiyReq;
import net.onebean.api.mngt.api.service.UnLoginAccessWhiteListApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class UnLoginAccessWhiteListApiFallback implements UnLoginAccessWhiteListApi {
    @Override
    public BaseResponse<Long> add(UnLoginAccessWhiteListAddReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(UnLoginAccessWhiteListModfiyReq appInfoVo) {
        return null;
    }

    @Override
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> find(BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        return null;
    }

    @Override
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> findUnBindingData(BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        return null;
    }
}
