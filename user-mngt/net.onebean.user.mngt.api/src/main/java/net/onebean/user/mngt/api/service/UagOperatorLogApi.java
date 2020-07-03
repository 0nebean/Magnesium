package net.onebean.user.mngt.api.service;

import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.user.mngt.api.fallback.UagOperatorLogApiFallBack;
import net.onebean.user.mngt.api.model.FindUagLogReq;
import net.onebean.user.mngt.api.model.UagLogVo;
import net.onebean.user.mngt.api.model.UagOperationLogMqReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-mngt",fallback = UagOperatorLogApiFallBack.class)
public interface UagOperatorLogApi {

    @PostMapping(value = "/uagLog/saveUagOperatorLog",produces = {"application/json"},consumes = {"application/json"})
    BaseResponse<Boolean> saveUagOperatorLog(@RequestBody @Validated UagOperationLogMqReq req);

    @PostMapping(value = "/uagLog/find", produces = {"application/json"}, consumes = {"application/json"})
    BasePaginationResponse<UagLogVo> find(@RequestBody @Validated BasePaginationRequest<FindUagLogReq> request);

}
