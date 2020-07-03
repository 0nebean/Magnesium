package net.onebean.mngt.portal.action.cloud.serverInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.service.ServerInfoApi;
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
@RequestMapping("/serverInfo")
public class ServerInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoCloudController.class);

    @Autowired
    private ServerInfoApi serverInfoApi;


    @UagOperationLog(description = "添加服务信息")
    @PostMapping(value = "/add", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> add(@RequestBody @Validated ServerInfoAddReq request, BindingResult result) {
        logger.info("ServerInfoController add method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController add method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverInfoApi.add(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoController add method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoController add method catch Exception e = ", e);
        }
        return response;
    }

    @PostMapping(value = "/find", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<ServerInfoVo> find(@RequestBody BasePaginationRequest<ServerInfoAddReq> request) {
        logger.info("ServerInfoController find method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerInfoVo> response = new BasePaginationResponse<>();
        try {
            logger.debug("ServerInfoController find method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = serverInfoApi.find(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoController find method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoController find method catch Exception e = ", e);
        }
        return response;
    }


    @UagOperationLog(description = "更新服务信息")
    @PostMapping(value = "/update", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> update(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result) {
        logger.info("ServerInfoController update method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController update method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverInfoApi.update(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoController update method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoController update method catch Exception e = ", e);
        }
        return response;
    }

    @UagOperationLog(description = "删除服务信息")
    @PostMapping(value = "/delete", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> delete(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result) {
        logger.info("ServerInfoController delete method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController delete method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            response = serverInfoApi.delete(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoController delete method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoController delete method catch Exception e = ", e);
        }
        return response;
    }

    @PostMapping(value = "/findById", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<ServerInfoVo> findById(@RequestBody @Validated ServerInfoModifyReq request, BindingResult result) {
        logger.info("ServerInfoController findById method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ServerInfoVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoController findById method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = serverInfoApi.findById(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoController findById method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoController findById method catch Exception e = ", e);
        }
        return response;
    }


    @UagOperationLog(description = "同步服务器节点信息")
    @PostMapping(value = "/syncServerMachineNodeInfo", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> syncServerMachineNodeInfo() {
        logger.info("AppInfoController syncServerMachineNodeInfo method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = serverInfoApi.syncServerMachineNodeInfo();
        } catch (BusinessException e) {
            logger.info("AppInfoController syncServerMachineNodeInfo method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("AppInfoController syncServerMachineNodeInfo method catch Exception e = ", e);
        }
        return response;
    }

    @PostMapping(value = "/findServerByName", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<ServerBasicInfo> findServerByName(@RequestBody @Validated FindServerByNameReq request, BindingResult result) {
        logger.info("ServerInfoCloudController findServerByName method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerBasicInfo> response = new BasePaginationResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("ServerInfoCloudController findServerByName method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = serverInfoApi.findServerByName(request);
        } catch (BusinessException e) {
            logger.info("ServerInfoCloudController findServerByName method BusinessException ex = ", e);
        } catch (Exception e) {
            logger.error("ServerInfoCloudController findServerByName method catch Exception e = ", e);
        }
        return response;
    }


}
