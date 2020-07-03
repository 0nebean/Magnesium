package net.onebean.mngt.portal.action.cloud.serverMachineNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.ServerMachineNodeAddReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeInfoModifyReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeVo;
import net.onebean.api.mngt.api.service.ServerMachineNodeInfoApi;
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
@RequestMapping("/serverMachineNodeInfo")
public class ServerMachineNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(ServerMachineNodeInfoCloudController.class);

    @Autowired
    private ServerMachineNodeInfoApi serverMachineNodeInfoApi;


    @UagOperationLog(description = "添加服务器节点")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated ServerMachineNodeAddReq request, BindingResult result){
        logger.info("ServerMachineNodeInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverMachineNodeInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("ServerMachineNodeInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("add method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<ServerMachineNodeVo> find(@RequestBody BasePaginationRequest<ServerMachineNodeAddReq> request){
        logger.info("ServerMachineNodeInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerMachineNodeVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = serverMachineNodeInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("ServerMachineNodeInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("find method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "更新服务器节点")
    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated ServerMachineNodeInfoModifyReq request, BindingResult result){
        logger.info("ServerMachineNodeInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverMachineNodeInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("ServerMachineNodeInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("update method catch Exception e = ",e);
        }
        return response;
    }

    @UagOperationLog(description = "删除服务器节点")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated ServerMachineNodeInfoModifyReq request, BindingResult result){
        logger.info("ServerMachineNodeInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverMachineNodeInfoApi.delete(request);
        } catch (BusinessException e) {
            logger.info("ServerMachineNodeInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("delete method catch Exception e = ",e);
        }
        return response;
    }

    @PostMapping(value = "/findById",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<ServerMachineNodeVo> findById(@RequestBody @Validated ServerMachineNodeInfoModifyReq request, BindingResult result){
        logger.info("ServerMachineNodeInfoController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ServerMachineNodeVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = serverMachineNodeInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("ServerMachineNodeInfoController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("findById method catch Exception e = ",e);
        }
        return response;
    }





}
