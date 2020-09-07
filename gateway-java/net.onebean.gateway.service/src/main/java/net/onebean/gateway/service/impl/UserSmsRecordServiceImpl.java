package net.onebean.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.model.UserSmsRecord;
import net.onebean.gateway.service.UserSmsRecordService;
import net.onebean.gateway.vo.FindUserSmsRecordReq;
import net.onebean.gateway.vo.UserSmsRecordVo;
import net.onebean.gateway.dao.UserSmsRecordDao;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
* @author Icc
* @description 短信发送记录 serviceImpl
* @date 2020-07-13 13:19:03
*/
@Service
public class UserSmsRecordServiceImpl extends BaseBiz<UserSmsRecord, UserSmsRecordDao> implements UserSmsRecordService {


    private final static Logger logger = LoggerFactory.getLogger(UserSmsRecordServiceImpl.class);

    @Override
    public List<UserSmsRecordVo> findUserSmsRecord(FindUserSmsRecordReq param, Pagination page, Sort sort) {
        String phoneNumber = Optional.ofNullable(param).map(FindUserSmsRecordReq::getPhoneNumber).orElse("");
        Integer uagId = Optional.ofNullable(param).map(FindUserSmsRecordReq::getUagId).orElse(0);

        logger.debug("method findUserSmsRecordReq param = " + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        logger.debug("method findUserSmsRecordReq page = " + JSON.toJSONString(page, SerializerFeature.WriteMapNullValue));
        logger.debug("method findUserSmsRecordReq sort = " + JSON.toJSONString(sort, SerializerFeature.WriteMapNullValue));

        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(phoneNumber)) {
            Condition condition = Condition.parseModelCondition("phoneNumber@string@eq");
            condition.setValue(phoneNumber);
            paramList.add(condition);
        }
        if (uagId > 0) {
            Condition condition = Condition.parseModelCondition("uagId@int@eq");
            condition.setValue(uagId);
            paramList.add(condition);
        }


        List<UserSmsRecord> list = this.find(page, paramList, sort);
        logger.debug("method findUserSmsRecordReq list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UserSmsRecordVo> res = JSON.parseArray(JSON.toJSONString(list), UserSmsRecordVo.class);
        logger.debug("method findUserSmsRecordReq res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }
}