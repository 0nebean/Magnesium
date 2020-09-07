package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.gateway.vo.UpsteamNameAddReq;
import net.onebean.gateway.vo.UpsteamNameModifyReq;
import net.onebean.gateway.vo.UpsteamNameVo;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.dao.UpsteamNameDao;
import net.onebean.gateway.model.UpsteamName;
import net.onebean.gateway.service.UpsteamNameService;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author Icc
* @description upsteam name serviceImpl
* @date 2019-03-01 17:40:12
*/
@Service
public class UpsteamNameServiceImpl extends BaseBiz<UpsteamName, UpsteamNameDao> implements UpsteamNameService {

    private final static Logger logger = LoggerFactory.getLogger(UpsteamNameServiceImpl.class);


    @Override
    public List<UpsteamNameVo> findUpsteamNameVo(UpsteamNameAddReq param, Pagination page, Sort sort) {
        String upsteamName = Optional.ofNullable(param).map(UpsteamNameAddReq::getUpsteamName).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(upsteamName)){
            Condition condition = Condition.parseModelCondition("upsteamName@string@like");
            condition.setValue(upsteamName);
            paramList.add(condition);
        }
        //find all
        List<UpsteamName> list = this.find(null,paramList,sort);
        logger.debug("UpsteamNameServiceImpl method findUpSteamNode list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<UpsteamNameVo> res = JSON.parseArray(JSON.toJSONString(list), UpsteamNameVo.class);
        logger.debug("UpsteamNameServiceImpl method findUpSteamNode res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public Boolean isRepeatByUpsteamName(String upsteamName,Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(upsteamName)){
            Condition condition = Condition.parseModelCondition("upsteamName@string@eq");
            condition.setValue(upsteamName);
            param.add(condition);
        }
        if (StringUtils.isNotEmpty(id)){
            Condition condition = Condition.parseModelCondition("id@string@nq");
            condition.setValue(id);
            param.add(condition);
        }

        return CollectionUtil.isNotEmpty(this.find(null,param));
    }

    @Override
    public Long add(UpsteamNameAddReq request) {
        UpsteamName node = new UpsteamName();
        try {
            BeanUtils.copyProperties(node,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        String upsteamName = Optional.ofNullable(request).map(UpsteamNameAddReq::getUpsteamName).orElse(null);
        if (StringUtils.isEmpty(upsteamName)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of upsteamName");
        }
        if (isRepeatByUpsteamName(upsteamName,null)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        this.save(node);

        return node.getId();
    }

    @Override
    public Integer update(UpsteamNameModifyReq request) {
        UpsteamName target = new UpsteamName();
        try {
            BeanUtils.copyProperties(target,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("UpsteamNameServiceImpl update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
        String upsteamName = Optional.ofNullable(request).map(UpsteamNameModifyReq::getUpsteamName).orElse(null);
        if (StringUtils.isEmpty(upsteamName)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of upsteamName");
        }
        String id = Optional.ofNullable(request).map(UpsteamNameModifyReq::getId).orElse(null);
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of id");
        }
        if (isRepeatByUpsteamName(upsteamName,id)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        return this.update(target);
    }

    @Override
    public Integer delete(UpsteamNameModifyReq request) {
        String id = Optional.ofNullable(request).map(UpsteamNameModifyReq::getId).orElse(null);
        UpsteamName target = this.findById(id);
        String upsteamName = Optional.ofNullable(target).map(UpsteamName::getUpsteamName).orElse(null);
        if (StringUtils.isEmpty(upsteamName)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of upsteamName");
        }
        if (countUseByUpsteamName(upsteamName) > 0){
            throw new BusinessException(ErrorCodesEnum.DATA_BINDING_ERR.code(),ErrorCodesEnum.DATA_BINDING_ERR.msg());
        }
        return this.deleteById(id);
    }

    @Override
    public Integer countUseByUpsteamName(String upsteamName) {
        return baseDao.countUseByUpsteamName(upsteamName);
    }
}