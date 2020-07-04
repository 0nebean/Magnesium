package net.onebean.api.mngt.action.cloud.upsteamNodeInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.UpsteamNameAddReq;
import net.onebean.api.mngt.api.model.UpsteamNameModifyReq;
import net.onebean.api.mngt.api.model.UpsteamNameVo;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.service.UpsteamNameService;
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

@RestController
@RequestMapping("/cloud/upSteamName")
public class UpSteamNameInfoCloudController {

    private final static Logger logger = LoggerFactory.getLogger(UpSteamNameInfoCloudController.class);

    @Autowired
    private UpsteamNameService upsteamNameService;


    @PostMapping(value = "/add",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Long> add(@RequestBody @Validated UpsteamNameAddReq request, BindingResult result){
        logger.info("UpSteamNameInfoController add method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Long> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNameInfoController add method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(upsteamNameService.add(request));
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
    public BasePaginationResponse<UpsteamNameVo> find(@RequestBody BasePaginationRequest<UpsteamNameAddReq> request){
        logger.info("UpSteamNameInfoController find method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BasePaginationResponse<UpsteamNameVo> response = new BasePaginationResponse<>();
        try {
            UpsteamNameAddReq param = Optional.ofNullable(request).map(BasePaginationRequest::getData).orElse(null);
            Pagination page = Optional.ofNullable(request).map(BasePaginationRequest::getPage).orElse(new Pagination());
            Sort sort = Optional.ofNullable(request).map(BasePaginationRequest::getSort).orElse(new Sort(Sort.DESC,"id"));
            logger.debug("UpSteamNameInfoController find method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(upsteamNameService.findUpsteamNameVo(param,page,sort),page);
        } catch (BusinessException e) {
			logger.info("catch exception",e);
		    response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
			logger.info("catch exception",e);
		    response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }


    @PostMapping(value = "/update",produces = {"application/json"},consumes = {"application/json"})
    public BaseResponse<Integer> update(@RequestBody @Validated UpsteamNameModifyReq request, BindingResult result){
        logger.info("UpSteamNameInfoController update method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("UpSteamNameInfoController update method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(upsteamNameService.update(request));
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
    public BaseResponse<Integer> delete(@RequestBody @Validated UpsteamNameModifyReq request, BindingResult result){
        logger.info("UpSteamNameInfoController delete method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Integer> response = new BaseResponse<>();
        try {
            if (result.hasErrors()) {
                 response = response.failure(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),result.getAllErrors().get(0).getDefaultMessage());
                return response;
            }
            logger.debug("delete method request = "+ JSON.toJSONString(request, SerializerFeature.WriteMapNullValue));
            response = response.ok(upsteamNameService.delete(request));
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
