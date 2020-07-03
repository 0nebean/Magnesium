package net.onebean.mngt.portal.action.cloud.ipWhiteList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.IpWhiteListAddReq;
import net.onebean.api.mngt.api.model.IpWhiteListModifyRequest;
import net.onebean.api.mngt.api.model.IpWhiteListQueryReq;
import net.onebean.api.mngt.api.model.IpWhiteListVo;
import net.onebean.api.mngt.api.service.IpWhiteListApi;
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
 * @author 0neBean
 * ip白名单
 */
@RestController
@RequestMapping("/cloud/ipWhiteList")
public class IpWhiteListCloudController {


    private final static Logger logger = LoggerFactory.getLogger(IpWhiteListCloudController.class);
    
    @Autowired 
    private IpWhiteListApi ipWhiteListApi;

    @UagOperationLog(description = "添加IP白名单")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated IpWhiteListAddReq request, BindingResult result) {
        logger.info("IpWhiteListController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("IpWhiteListController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = ipWhiteListApi.add(request);
        } catch (BusinessException e) {
            logger.info("IpWhiteListController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("IpWhiteListController add method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "删除IP白名单")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated IpWhiteListModifyRequest request, BindingResult result) {
        logger.info("IpWhiteListController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("IpWhiteListController delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = ipWhiteListApi.delete(request);
        } catch (BusinessException e) {
            logger.info("IpWhiteListController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("IpWhiteListController delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<IpWhiteListVo> find(@RequestBody BasePaginationRequest<IpWhiteListQueryReq> request) {
        logger.info("IpWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<IpWhiteListVo> response = new BasePaginationResponse<>();
        try {
            response = ipWhiteListApi.find(request);
        } catch (BusinessException e) {
            logger.info("IpWhiteListController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("IpWhiteListController find method catch Exception e = ",e);
        }
        return response;
    }
}
