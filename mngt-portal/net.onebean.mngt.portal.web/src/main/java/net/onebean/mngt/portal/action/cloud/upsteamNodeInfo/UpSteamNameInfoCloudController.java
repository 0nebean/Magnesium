package net.onebean.mngt.portal.action.cloud.upsteamNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.UpsteamNameAddReq;
import net.onebean.api.mngt.api.model.UpsteamNameModifyReq;
import net.onebean.api.mngt.api.model.UpsteamNameVo;
import net.onebean.api.mngt.api.service.UpSteamNameInfoApi;
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

@RestController
@RequestMapping("/upSteamName")
public class UpSteamNameInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNameInfoCloudController.class);

    @Autowired
    private UpSteamNameInfoApi upSteamNameInfoApi;

    @UagOperationLog(description = "添加服务节点关联名称")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated UpsteamNameAddReq request, BindingResult result){
        logger.info("UpSteamNameInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNameInfoController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNameInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNameInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UpsteamNameVo> find(@RequestBody BasePaginationRequest<UpsteamNameAddReq> request){
        logger.info("UpSteamNameInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpsteamNameVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("UpSteamNameInfoController find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = upSteamNameInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNameInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "编辑服务节点关联名称")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated UpsteamNameModifyReq request, BindingResult result){
        logger.info("UpSteamNameInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNameInfoController update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNameInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNameInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "删除服务节点关联名称")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated UpsteamNameModifyReq request, BindingResult result){
        logger.info("UpSteamNameInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNameInfoApi.delete(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNameInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }


}
