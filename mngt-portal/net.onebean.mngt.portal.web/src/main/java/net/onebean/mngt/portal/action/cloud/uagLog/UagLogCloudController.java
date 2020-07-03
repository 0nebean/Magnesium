package net.onebean.mngt.portal.action.cloud.uagLog;


import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.mngt.portal.common.ErrorCodesEnum;
import net.onebean.user.mngt.api.model.FindUagLogReq;
import net.onebean.user.mngt.api.model.UagLogVo;
import net.onebean.user.mngt.api.service.UagOperatorLogApi;
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

@RestController
@RequestMapping("uagLog")
public class UagLogCloudController {


    private final static Logger logger = LoggerFactory.getLogger(UagLogCloudController.class);

    @Autowired
    private UagOperatorLogApi uagOperatorLogApi;


    @PostMapping(value = "find", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<UagLogVo> find(@RequestBody @Validated BasePaginationRequest<FindUagLogReq> request, BindingResult result) {
        logger.info("find method access " + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UagLogVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = uagOperatorLogApi.find(request);
        } catch (BusinessException e) {
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }
}
