package net.onebean.mngt.portal.action.cloud.upsteamNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.UpSteamNodeInfoApi;
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
@RequestMapping("/upSteamNodeInfo")
public class UpSteamNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNodeInfoCloudController.class);

    @Autowired
    private UpSteamNodeInfoApi upSteamNodeInfoApi;


    @UagOperationLog(description = "添加服务节点")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated UpSteamNodeAddReq request, BindingResult result){
        logger.info("UpSteamNodeInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNodeInfoController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNodeInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNodeInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UpSteamNodeVo> find(@RequestBody BasePaginationRequest<UpSteamNodeAddReq> request){
        logger.info("UpSteamNodeInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpSteamNodeVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("UpSteamNodeInfoController find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = upSteamNodeInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNodeInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "编辑服务节点")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated UpSteamNodeModifyReq request, BindingResult result){
        logger.info("UpSteamNodeInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNodeInfoController update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNodeInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNodeInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除服务节点")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated UpSteamNodeModifyReq request, BindingResult result){
        logger.info("UpSteamNodeInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = upSteamNodeInfoApi.delete(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNodeInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<UpSteamNodeVo> findById(@RequestBody @Validated UpSteamNodeModifyReq request, BindingResult result){
        logger.info("UpSteamNodeInfoController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<UpSteamNodeVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = upSteamNodeInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("UpSteamNodeInfoController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }


    @PostMapping(value = "/findVersionListByNodeName",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UpSteamVersionVo> findVersionListByNodeName(@RequestBody @Validated FindVersionListByNodeNameReq request, BindingResult result){
        logger.info("findVersionListByNodeName method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpSteamVersionVo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            response = upSteamNodeInfoApi.findVersionListByNodeName(request);
        } catch (BusinessException e) {
            logger.info("findVersionListByNodeName method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findVersionListByNodeName method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/setRunningStatusDownByDevOpsK8sNotificationVo",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> setRunningStatusDownByDevOpsK8sNotificationVo(@RequestBody @Validated SetRunningStatusDown request, BindingResult result){
        logger.info("setRunningStatusDownByDevOpsK8sNotificationVo method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("setRunningStatusDownByDevOpsK8sNotificationVo method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = upSteamNodeInfoApi.setRunningStatusDownByDevOpsK8sNotificationVo(request);
        } catch (BusinessException e) {
            logger.info("setRunningStatusDownByDevOpsK8sNotificationVo method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("setRunningStatusDownByDevOpsK8sNotificationVo method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/updateSelectedVersion",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> updateSelectedVersion(@RequestBody @Validated UpdateSelectedVersionReq request, BindingResult result){
        logger.info("updateSelectedVersion method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("updateSelectedVersion method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = upSteamNodeInfoApi.updateSelectedVersion(request);
        } catch (BusinessException e) {
            logger.info("updateSelectedVersion method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("updateSelectedVersion method catch Exception e = ",e);
        }
        return response;
    }
}
