package net.onebean.gateway.action.serverInfo;

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
import net.onebean.gateway.service.ManualUpdateServerNodeService;
import net.onebean.gateway.service.ServerInfoService;
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
@RequestMapping("/serverInfo")
public class ServerInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoCloudController.class);

    @Autowired
    private ServerInfoService serverInfoService;
    @Autowired
    private ManualUpdateServerNodeService manualUpdateServerNodeService;


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
            response = response.ok(serverInfoService.saveServerInfoAddReq(request));
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
    @PostMapping(value = "/find", produces = {"application/json"}, consumes = {"application/json"})
    public BasePaginationResponse<ServerInfoVo> find(@RequestBody BasePaginationRequest<ServerInfoAddReq> request) {
        logger.info("ServerInfoController find method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerInfoVo> response = new BasePaginationResponse<>();
        try {
            ServerInfoAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC, "id"));
            logger.debug("ServerInfoController find method request = " + JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(serverInfoService.findServerInfo(param, page, sort), page);
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(serverInfoService.updateServerInfoModifyReq(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(serverInfoService.deleteServerInfo(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            Object id = Optional.ofNullable(request).map(ServerInfoModifyReq::getId).orElse(null);
            response = response.ok(serverInfoService.findVoById(id));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @UagOperationLog(description = "同步服务器节点信息")
    @PostMapping(value = "/syncServerMachineNodeInfo", produces = {"application/json"}, consumes = {"application/json"})
    public BaseResponse<Boolean> syncServerMachineNodeInfo() {
        logger.info("AppInfoController syncServerMachineNodeInfo method access" + DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            response = response.ok(manualUpdateServerNodeService.updateAllNginxUpSteamConf());
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(serverInfoService.findServerInfo(request));
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
