package net.onebean.mngt.portal.action.cloud.apiInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.ApiInfoApi;
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
@RequestMapping("/apiInfo")
public class ApiInfoController {


    private final static Logger logger = LoggerFactory.getLogger(ApiInfoController.class);

    @Autowired
    private ApiInfoApi apiInfoApi;


    @UagOperationLog(description = "添加API信息")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated ApiInfoAddReq request, BindingResult result){
        logger.info("add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = apiInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<ApiInfoVo> find(@RequestBody BasePaginationRequest<ApiInfoFindReq> request){
        logger.info("find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ApiInfoVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = apiInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "编辑API信息")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = apiInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除API信息")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            System.out.println("guagua = "+JSON.toJSON(apiInfoApi.delete(request)));
            response = apiInfoApi.delete(request);
        } catch (BusinessException e) {
            logger.info("delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<ApiInfoVo> findById(@RequestBody @Validated ApiModifyReq request, BindingResult result){
        logger.info("findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ApiInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = apiInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("findById method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }


    @PostMapping(value = "/findApiByAppId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppApiRelationshipResp> findApiByAppId(@RequestBody @Validated FindApiByAppIdReq request, BindingResult result){
        logger.info("ApiInfoCloudController findApiByAppId method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ApiInfoCloudController findApiByAppId method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = apiInfoApi.findApiByAppId(request);
        } catch (BusinessException e) {
            logger.info("ApiInfoCloudController findApiByAppId method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ApiInfoCloudController findApiByAppId method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findAppApiRelationshipByServerIdAndAppId",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<AppApiRelationshipResp> findAppApiRelationshipByServerIdAndAppId(@RequestBody @Validated AppApiRelationshipReq request, BindingResult result){
        logger.info("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<AppApiRelationshipResp> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = apiInfoApi.findAppApiRelationshipByServerIdAndAppId(request);
        } catch (BusinessException e) {
            logger.info("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ApiInfoCloudController findAppApiRelationshipByServerIdAndAppId method catch Exception e = ",e);
        }
        return response;
    }

}
