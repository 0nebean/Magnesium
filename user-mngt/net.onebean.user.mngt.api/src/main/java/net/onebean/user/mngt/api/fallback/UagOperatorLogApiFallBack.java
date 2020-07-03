package net.onebean.user.mngt.api.fallback;

import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.model.FindUagLogReq;
import net.onebean.user.mngt.api.model.UagLogVo;
import net.onebean.user.mngt.api.model.UagOperationLogMqReq;
import net.onebean.user.mngt.api.service.UagOperatorLogApi;

public class UagOperatorLogApiFallBack implements UagOperatorLogApi {

    @Override
    public BaseResponse<Boolean> saveUagOperatorLog(UagOperationLogMqReq req) {
        BaseResponse<Boolean> response = new BaseResponse<>();
        response.setErrCode("999");
        response.setErrMsg("fall back");
        return response;
    }

    @Override
    public BasePaginationResponse<UagLogVo> find(BasePaginationRequest<FindUagLogReq> request) {
        return null;
    }
}
