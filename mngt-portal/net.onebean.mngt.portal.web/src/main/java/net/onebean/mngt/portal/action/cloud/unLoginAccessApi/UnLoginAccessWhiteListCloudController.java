package net.onebean.mngt.portal.action.cloud.unLoginAccessApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.UnLoginAccessApiWhiteListVo;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListAddReq;
import net.onebean.api.mngt.api.model.UnLoginAccessWhiteListModfiyReq;
import net.onebean.api.mngt.api.service.UnLoginAccessWhiteListApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.mngt.portal.common.ErrorCodesEnum;
import net.onebean.user.mngt.log.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
import net.onebean.sso.sdk.core.UagSsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  易开伙伴 未登录访问接口白名单
 * @author 0neBean
 */
@RestController
@RequestMapping("/unLoginAccessWhiteList")
public class UnLoginAccessWhiteListCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UnLoginAccessWhiteListCloudController.class);

    @Autowired
    private UnLoginAccessWhiteListApi unLoginAccessWhiteListApi;


    @UagOperationLog(description = "添加未登录API访问白名单")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated UnLoginAccessWhiteListAddReq request, BindingResult result) {
        logger.info("UnLoginAccessWhiteListController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UnLoginAccessWhiteListController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = unLoginAccessWhiteListApi.add(request);
        } catch (BusinessException e) {
            logger.info("UnLoginAccessWhiteListController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("UnLoginAccessWhiteListController add method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除未登录API访问白名单")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody UnLoginAccessWhiteListModfiyReq request) {
        logger.info("UnLoginAccessWhiteListController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            logger.debug("UnLoginAccessWhiteListController delete method appInfoVo = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = unLoginAccessWhiteListApi.delete(request);
        } catch (BusinessException e) {
            logger.info("UnLoginAccessWhiteListController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("UnLoginAccessWhiteListController delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> find(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            response = unLoginAccessWhiteListApi.find(request);
        } catch (BusinessException e) {
            logger.info("UnLoginAccessWhiteListController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("UnLoginAccessWhiteListController find method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findUnBindingData",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> findUnBindingData(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            response = unLoginAccessWhiteListApi.findUnBindingData(request);
        } catch (BusinessException e) {
            logger.info("UnLoginAccessWhiteListController findUnBindingData method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("UnLoginAccessWhiteListController findUnBindingData method catch Exception e = ",e);
        }
        return response;
    }


}
