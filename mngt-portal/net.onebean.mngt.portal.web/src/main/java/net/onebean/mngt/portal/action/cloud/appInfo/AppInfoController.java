package net.onebean.mngt.portal.action.cloud.appInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.AppInfoApi;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.log.annotation.UagOperationLog;
import net.onebean.util.DateUtils;
import net.onebean.sso.sdk.core.UagSsoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 0neBean
 * 应用信息的CRUD
 */
@RestController
@RequestMapping("/appInfo")
public class AppInfoController {

    private final static Logger logger = LoggerFactory.getLogger(AppInfoController.class);

    @Autowired
    private AppInfoApi appInfoApi;


    @UagOperationLog(description = "新增应用信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody AppInfoAddRequest request) {
        logger.info("AppInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            UagSsoUtils.setUagUserInfoBySession(request);
            response = appInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController add method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "更新应用信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody AppInfoModifyRequest request) {
        logger.info("AppInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            UagSsoUtils.setUagUserInfoBySession(request);
            response = appInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController update method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "删除应用信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody AppInfoVo appInfoVo) {
        logger.info("AppInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            UagSsoUtils.setUagUserInfoBySession(appInfoVo);
            response = appInfoApi.delete(appInfoVo);
        } catch (BusinessException e) {
            logger.info("AppInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<AppInfoVo> find(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request) {
        logger.info("AppInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<AppInfoVo> response = new BasePaginationResponse<>();
        try {
            response = appInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController find method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppInfoVo> findById(@RequestBody BasePaginationRequest<AppInfoQueryRequest> request) {
        logger.info("AppInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppInfoVo> response = new BaseResponse<>();
        try {
            response = appInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController find method catch Exception e = ",e);
        }
        return response;
    }



    @UagOperationLog(description = "绑定API")
    @PostMapping(value = "/bindingApi",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> bindingApi(@RequestBody AppBindingApiReq request){
        logger.info("AppInfoController bindApi method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            logger.debug("AppInfoController bindApi method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = appInfoApi.bindingApi(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController bindApi method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController bindApi method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "取消绑定API")
    @PostMapping(value = "/unBindingApi",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> unBindingApi(@RequestBody AppBindingApiReq request){
        logger.info("AppInfoController unBindApi method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            logger.debug("AppInfoController unBindApi method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = appInfoApi.unBindingApi(request);
        } catch (BusinessException e) {
            logger.info("AppInfoController unBindApi method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController unBindApi method catch Exception e = ",e);
        }
        return response;
    }


    @UagOperationLog(description = "同步应用和API的关联关系")
    @PostMapping(value = "/syncAppApiRelationship",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Boolean> syncAppApiRelationship(){
        logger.info("AppInfoController syncAppApiRelationship method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = appInfoApi.syncAppApiRelationship();
        } catch (BusinessException e) {
            logger.info("AppInfoController syncAppApiRelationship method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController syncAppApiRelationship method catch Exception e = ",e);
        }
        return response;
    }
}
