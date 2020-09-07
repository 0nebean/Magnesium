package net.onebean.gateway.action.uagLog;


import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.vo.FindUagLogReq;
import net.onebean.gateway.vo.UagLogVo;
import net.onebean.gateway.service.UagOperatorLogService;
import net.onebean.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("uagLog")
public class UagLogCloudController {


    private final static Logger logger = LoggerFactory.getLogger(UagLogCloudController.class);

    @Autowired
    private UagOperatorLogService uagOperatorLogService;



    @PostMapping(value = "find", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<UagLogVo> find(@RequestBody @Validated BasePaginationRequest<FindUagLogReq> request, BindingResult result) {
        logger.info("find method access " + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagLogVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            FindUagLogReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC, "id"));
            response = response.ok(uagOperatorLogService.findUagLogInfo(param, page, sort), page);
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }
}
