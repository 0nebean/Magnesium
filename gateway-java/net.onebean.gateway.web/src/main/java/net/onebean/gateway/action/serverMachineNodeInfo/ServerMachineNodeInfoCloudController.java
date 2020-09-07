package net.onebean.gateway.action.serverMachineNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.Json.EnableEnumDeserialize;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.vo.ServerMachineNodeAddReq;
import net.onebean.gateway.vo.ServerMachineNodeInfoModifyReq;
import net.onebean.gateway.vo.ServerMachineNodeVo;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.annotation.UagOperationLog;
import net.onebean.gateway.service.ServerMachineNodeService;
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

import java.util.Optional;

@RestController
@RequestMapping("/serverMachineNodeInfo")
public class ServerMachineNodeInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(ServerMachineNodeInfoCloudController.class);

    @Autowired
    private ServerMachineNodeService serverMachineNodeService;


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
            response = response.ok(serverMachineNodeService.add(request));
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
    public BasePaginationResponse<ServerMachineNodeVo> find(@RequestBody BasePaginationRequest<ServerMachineNodeAddReq> request){
        logger.info("ServerMachineNodeInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<ServerMachineNodeVo> response = new BasePaginationResponse<>();
        try {
            ServerMachineNodeAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            logger.debug("find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(serverMachineNodeService.findServerMachineNodeVo(param,page,sort),page);
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            response = response.ok(serverMachineNodeService.update(request));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
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
            String id = Optional.ofNullable(request).map(ServerMachineNodeInfoModifyReq::getId).orElse(null);
            response = response.ok(serverMachineNodeService.deleteById(id));
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
    public BaseResponse<ServerMachineNodeVo> findById(@RequestBody @Validated ServerMachineNodeInfoModifyReq request, BindingResult result){
        logger.info("ServerMachineNodeInfoController findById method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<ServerMachineNodeVo> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("findById method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(request).map(ServerMachineNodeInfoModifyReq::getId).orElse(null);
            response = response.ok(serverMachineNodeService.findVoById(id));
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
