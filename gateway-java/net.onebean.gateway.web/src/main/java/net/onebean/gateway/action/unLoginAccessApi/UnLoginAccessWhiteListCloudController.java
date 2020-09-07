package net.onebean.gateway.action.unLoginAccessApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.gateway.vo.UnLoginAccessWhiteListAddReq;
import net.onebean.gateway.vo.UnLoginAccessWhiteListModfiyReq;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.annotation.UagOperationLog;
import net.onebean.gateway.model.UnLoginAccessApiWhiteList;
import net.onebean.gateway.service.UnLoginAccessApiWhiteListService;
import net.onebean.util.DateUtils;
import net.onebean.sso.sdk.core.UagSsoUtils;
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
 *  易开伙伴 未登录访问接口白名单
 * @author Icc
 */
@RestController
@RequestMapping("/unLoginAccessWhiteList")
public class UnLoginAccessWhiteListCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UnLoginAccessWhiteListCloudController.class);

    @Autowired
    private UnLoginAccessApiWhiteListService accessWhiteListService;


    @UagOperationLog(description = "添加未登录API访问白名单")
    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated UnLoginAccessWhiteListAddReq request, BindingResult result) {
        logger.info("UnLoginAccessWhiteListController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UnLoginAccessWhiteListController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            UnLoginAccessApiWhiteList target = new UnLoginAccessApiWhiteList();
            BeanUtils.copyProperties(target,request);
            logger.debug("UnLoginAccessWhiteListController add method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
            UagSsoUtils.setUagUserInfoBySession(request);
            accessWhiteListService.save(target);
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

    @UagOperationLog(description = "删除未登录API访问白名单")
    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody UnLoginAccessWhiteListModfiyReq request) {
        logger.info("UnLoginAccessWhiteListController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            logger.debug("UnLoginAccessWhiteListController delete method appInfoVo = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            Object id = Optional.ofNullable(request).map(UnLoginAccessWhiteListModfiyReq::getId).orElse(null);
            response = response.ok(accessWhiteListService.deleteById(id));
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "/find",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> find(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            UnLoginAccessApiWhiteListVo param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(accessWhiteListService.findByRsSalesUnLoginAccessWhiteListQueryRequest(param,page,sort),page);
        } catch (BusinessException e) {
            logger.info("catch exception",e);
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            logger.info("catch exception",e);
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

    @PostMapping(value = "/findUnBindingData",produces = {"application/json"},consumes = {"application/json"})
    public BasePaginationResponse<UnLoginAccessApiWhiteListVo> findUnBindingData(@RequestBody BasePaginationRequest<UnLoginAccessApiWhiteListVo> request) {
        logger.info("UnLoginAccessWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UnLoginAccessApiWhiteListVo> response = new BasePaginationResponse<>();
        try {
            UnLoginAccessApiWhiteListVo param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(accessWhiteListService.findUnBindingData(param,page,sort),page);
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
