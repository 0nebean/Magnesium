package net.onebean.user.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.user.mngt.api.model.UagOperationLogMqReq;
import net.onebean.user.mngt.dao.UagOperatorLogDao;
import net.onebean.user.mngt.model.UagOperatorLog;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagOperatorLogService;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.user.mngt.api.model.FindUagLogReq;
import net.onebean.user.mngt.api.model.UagLogVo;
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
* @author 0neBean
* @description 操作日志 serviceImpl
* @date 2019-06-27 23:45:23
*/
@Service
public class UagOperatorLogServiceImpl extends BaseBiz<UagOperatorLog, UagOperatorLogDao> implements UagOperatorLogService {


    private final static Logger logger = LoggerFactory.getLogger(net.onebean.user.mngt.service.impl.UagOperatorLogServiceImpl.class);

    @Override
    public Boolean saveUagOperatorLog(UagOperationLogMqReq req) {
        String description = Optional.of(req).map(UagOperationLogMqReq::getDescription).orElse("");
        String appId = Optional.of(req).map(UagOperationLogMqReq::getAppName).orElse("");
        String uagUserId = Optional.of(req).map(UagOperationLogMqReq::getUagUserId).orElse("");
        String uagUserNickName = Optional.of(req).map(UagOperationLogMqReq::getUagUserNickName).orElse("");
        UagOperatorLog uagOperatorLog = new UagOperatorLog();
        uagOperatorLog.setAppName(appId);
        uagOperatorLog.setOperatorId(Parse.toInt(uagUserId));
        uagOperatorLog.setOperatorName(uagUserNickName);
        uagOperatorLog.setOperatorDescription(description);
        this.save(uagOperatorLog);
        return true;
    }

    @Override
    public List<UagLogVo> findUagLogInfo(FindUagLogReq param, Pagination page, Sort sort) {
        String operatorName = Optional.ofNullable(param).map(FindUagLogReq::getOperatorName).orElse("");
        String appName = Optional.ofNullable(param).map(FindUagLogReq::getAppName).orElse("");


        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(operatorName)) {
            Condition condition = Condition.parseModelCondition("operatorName@string@like");
            condition.setValue(operatorName);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(appName)) {
            Condition condition = Condition.parseModelCondition("appName@string@like");
            condition.setValue(appName);
            paramList.add(condition);
        }


        List<UagOperatorLog> list = this.find(page, paramList, sort);
        logger.debug("method findUagLogInfo list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UagLogVo> res = JSON.parseArray(JSON.toJSONString(list), UagLogVo.class);
        logger.debug("method findUagLogInfo res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }
}