package net.onebean.api.mngt.api.api.fallback;

import net.onebean.api.mngt.api.model.IpWhiteListAddReq;
import net.onebean.api.mngt.api.model.IpWhiteListModifyRequest;
import net.onebean.api.mngt.api.model.IpWhiteListQueryReq;
import net.onebean.api.mngt.api.model.IpWhiteListVo;
import net.onebean.api.mngt.api.service.IpWhiteListApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;

public class IpWhiteListApiFallBack implements IpWhiteListApi {

    @Override
    public BaseResponse<Long> add(IpWhiteListAddReq request) {
        return null;
    }

    @Override
    public BaseResponse<Integer> delete(IpWhiteListModifyRequest request) {
        return null;
    }

    @Override
    public BasePaginationResponse<IpWhiteListVo> find(BasePaginationRequest<IpWhiteListQueryReq> request) {
        return null;
    }
}
