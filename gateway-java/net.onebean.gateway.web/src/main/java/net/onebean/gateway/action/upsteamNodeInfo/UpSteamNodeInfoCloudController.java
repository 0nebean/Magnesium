package net.onebean.gateway.action.upsteamNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.Json.EnableEnumDeserialize;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.annotation.UagOperationLog;
import net.onebean.gateway.service.UpSteamNodeService;
import net.onebean.gateway.vo.*;
import net.onebean.util.DateUtils;
import net.onebean.sso.sdk.core.UagSsoUtils;
import net.onebean.gateway.vo.*;
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
@RequestMapping("/upSteamNodeInfo")
public class UpSteamNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNodeInfoCloudController.class);

    @Autowired
    private UpSteamNodeService upSteamNodeService;


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
            response = response.ok(upSteamNodeService.add(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @EnableEnumDeserialize
    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UpSteamNodeVo> find(@RequestBody BasePaginationRequest<UpSteamNodeAddReq> request){
        logger.info("UpSteamNodeInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpSteamNodeVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("UpSteamNodeInfoController find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UpSteamNodeAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(upSteamNodeService.findUpSteamNodeVo(param,page,sort),page);
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(upSteamNodeService.update(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            String id = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getId).orElse(null);
            response = response.ok(upSteamNodeService.deleteById(id));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            Object id = Optional.ofNullable(request).map(UpSteamNodeModifyReq::getId).orElse(null);
            response = response.ok(upSteamNodeService.findVoById(id));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            logger.debug("findVersionListByNodeName method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(upSteamNodeService.findVersionListByNodeName(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(upSteamNodeService.setRunningStatusDownByDevOpsK8sNotificationVo(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(upSteamNodeService.updateSelectedVersion(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }
}
