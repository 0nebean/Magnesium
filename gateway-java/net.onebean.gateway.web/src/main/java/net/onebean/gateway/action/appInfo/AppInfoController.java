package net.onebean.gateway.action.appInfo;

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
import net.onebean.gateway.model.AppInfo;
import net.onebean.gateway.service.AppApiService;
import net.onebean.gateway.service.AppInfoService;
import net.onebean.gateway.service.AppServerService;
import net.onebean.gateway.vo.*;
import net.onebean.util.DateUtils;
import net.onebean.sso.sdk.core.UagSsoUtils;
import net.onebean.gateway.vo.*;
import org.apache.commons.beanutils.BeanUtils;
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

/**
 * @author Icc
 * 应用信息的CRUD
 */
@RestController
@RequestMapping("/appInfo")
public class AppInfoController {

    private final static Logger logger = LoggerFactory.getLogger(AppInfoController.class);

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private AppApiService appApiService;
    @Autowired
    private AppServerService appServerService;


    @UagOperationLog(description = "新增应用信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated AppInfoAddRequest request, BindingResult result) {
        logger.info("AppInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("AppInfoController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            AppInfo target = new AppInfo();
            BeanUtils.copyProperties(target,request);
            logger.debug("AppInfoController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(target);
            appInfoService.addAppInfo(target);
            response = response.ok(target.getId());
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "更新应用信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated AppInfoModifyRequest request, BindingResult result) {
        logger.info("AppInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("AppInfoController update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            AppInfo target = new AppInfo();
            BeanUtils.copyProperties(target,request);
            logger.debug("AppInfoController update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(target);
            response = response.ok(appInfoService.update(target));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "删除应用信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody AppInfoVo appInfoVo) {
        logger.info("AppInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            logger.debug("AppInfoController delete method appInfoVo = "+ JSON.toJSONString(appInfoVo, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(appInfoVo).map(AppInfoVo::getId).orElse(null);
            response = response.ok(appInfoService.deleteById(id));
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
    public BasePaginationResponse<AppInfoVo> find(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request) {
        logger.info("AppInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<AppInfoVo> response = new BasePaginationResponse<>();
        try {
            AppInfoQueryRequest param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(appInfoService.findByAppInfoQueryRequest(param,page,sort),page);
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
    public BaseResponse<AppInfoVo> findById(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request) {
        logger.info("AppInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppInfoVo> response = new BaseResponse<>();
        try {
            Object id = Optional.ofNullable(request).map(BasePaginationRequest::getData).map(AppInfoQueryRequest::getId).orElse(null);
            response = response.ok(appInfoService.findVoById(id));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }



    @UagOperationLog(description = "绑定API")
    @PostMapping(value = "/bindingApi",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> bindingApi(@RequestBody @Validated AppBindingApiReq request, BindingResult result){
        logger.info("AppInfoController bindApi method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("AppInfoController bindApi method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = response.ok(appApiService.bindApi(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "取消绑定API")
    @PostMapping(value = "/unBindingApi",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> unBindingApi(@RequestBody @Validated AppBindingApiReq request, BindingResult result){
        logger.info("AppInfoController unBindApi method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("AppInfoController unBindApi method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(appApiService.unBindApi(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "同步应用和API的关联关系")
    @PostMapping(value = "/syncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> syncAppApiRelationship(){
        logger.info("AppInfoController syncAppApiRelationship method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = response.ok(appServerService.syncAppApiRelationship());
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
