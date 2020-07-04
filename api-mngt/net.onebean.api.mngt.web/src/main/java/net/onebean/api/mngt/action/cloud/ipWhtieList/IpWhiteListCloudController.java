package net.onebean.api.mngt.action.cloud.ipWhtieList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.IpWhiteListAddReq;
import net.onebean.api.mngt.api.model.IpWhiteListModifyRequest;
import net.onebean.api.mngt.api.model.IpWhiteListQueryReq;
import net.onebean.api.mngt.api.model.IpWhiteListVo;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.service.IpWhiteListService;
import net.onebean.core.base.BasePaginationRequest;
import net.onebean.core.base.BasePaginationResponse;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.util.DateUtils;
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
 * @author 0neBean
 * ip白名单
 */
@RestController
@RequestMapping("/cloud/ipWhiteList")
public class IpWhiteListCloudController {


    private final static Logger logger = LoggerFactory.getLogger(IpWhiteListCloudController.class);
    
    @Autowired 
    private IpWhiteListService ipWhiteListService;


    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated IpWhiteListAddReq request, BindingResult result) {
        logger.info("IpWhiteListController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("IpWhiteListController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(ipWhiteListService.add(request));
        } catch (BusinessException e) {
			logger.info("catch exception",e);
		    response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
			logger.info("catch exception",e);
		    response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "/delete",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> delete(@RequestBody @Validated IpWhiteListModifyRequest request, BindingResult result) {
        logger.info("IpWhiteListController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("IpWhiteListController delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(ipWhiteListService.deleteById(request.getId()));
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
    public BasePaginationResponse<IpWhiteListVo> find(@RequestBody BasePaginationRequest<IpWhiteListQueryReq> request) {
        logger.info("IpWhiteListController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<IpWhiteListVo> response = new BasePaginationResponse<>();
        try {
            IpWhiteListQueryReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            response = response.ok(ipWhiteListService.findByIpWhiteListQueryReq(param,page,sort),page);
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
