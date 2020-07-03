package net.onebean.api.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.*;
import net.onebean.api.mngt.api.model.enumModel.DeployTypeEnum;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.dao.ServerInfoDao;
import net.onebean.api.mngt.model.ServerInfo;
import net.onebean.api.mngt.service.ApiInfoService;
import net.onebean.api.mngt.service.ServerInfoService;
import net.onebean.api.mngt.vo.ServerHostNodeVo;
import net.onebean.api.mngt.vo.UpSteamSyncNodeVo;
import net.onebean.core.base.BaseBiz;
import net.onebean.core.base.YesOrNoEnum;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.util.CollectionUtil;
import net.onebean.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 0neBean
 * @description server info serviceImpl
 * @date 2019-01-21 18:05:28
 */
@Service
public class ServerInfoServiceImpl extends BaseBiz<ServerInfo, ServerInfoDao> implements ServerInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoServiceImpl.class);


    @Autowired
    private ApiInfoService apiInfoService;


    @Override
    public List<ServerInfoVo> findServerInfo(ServerInfoAddReq req, Pagination pagination, Sort sort) {

        String serverName = Optional.ofNullable(req).map(ServerInfoAddReq::getServerName).orElse(null);
        String upsteamNodeName = Optional.ofNullable(req).map(ServerInfoAddReq::getUpsteamNodeName).orElse(null);
        String selectedVersion = Optional.ofNullable(req).map(ServerInfoAddReq::getSelectedVersion).orElse(null);
        String deployTypeParam = Optional.ofNullable(req).map(ServerInfoAddReq::getDeployType).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(serverName)) {
            Condition condition = Condition.parseModelCondition("serverName@string@like");
            condition.setValue(serverName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(upsteamNodeName)) {
            Condition condition = Condition.parseModelCondition("upsteamNodeName@string@eq");
            condition.setValue(upsteamNodeName);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(selectedVersion)) {
            Condition condition = Condition.parseModelCondition("selectedVersion@string@eq");
            condition.setValue(selectedVersion);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(deployTypeParam)) {
            Condition condition = Condition.parseModelCondition("deployType@string@eq");
            condition.setValue(deployTypeParam);
            paramList.add(condition);
        }

        List<ServerInfo> list = this.find(pagination, paramList, sort);
        logger.debug("ServerInfoServiceImpl method findServerInfo list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<ServerInfoVo> res = JSON.parseArray(JSON.toJSONString(list), ServerInfoVo.class);
        logger.debug("ServerInfoServiceImpl method findServerInfo res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }


        res = res.stream().peek(o -> {
            String deployType = Optional.of(o).map(ServerInfoVo::getDeployType).orElse("");
            String isFront = Optional.of(o).map(ServerInfoVo::getIsFront).orElse("");
            if (deployType.equals(DeployTypeEnum.PHYSICAL.getKey())) {
                o.setSelectedVersion("-");
            }
            if (isFront.equals(YesOrNoEnum.NO.getKey())) {
                o.setServerHost("-");
            }

        }).collect(Collectors.toList());

        return res;
    }

    @Override
    public ServerInfoVo findVoById(Object id) {
        ServerInfo serverInfo = this.findById(id);
        ServerInfoVo serverInfoVo = new ServerInfoVo();
        if (null != serverInfo) {
            try {
                BeanUtils.copyProperties(serverInfoVo, serverInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + "bean of serverInfoVo");
            }
        } else {
            return null;
        }
        return serverInfoVo;
    }

    @Override
    public List<ServerInfoVo> findServerInfo(FindServerByNameReq req) {
        String serverName = Optional.ofNullable(req).map(FindServerByNameReq::getServerName).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(serverName)) {
            Condition condition = Condition.parseModelCondition("serverName@string@like");
            condition.setValue(serverName);
            paramList.add(condition);
        } else {
            return Collections.emptyList();
        }

        List<ServerInfo> list = this.find(null, paramList, null);
        logger.debug("ServerInfoServiceImpl method findServerInfo list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<ServerInfoVo> res = JSON.parseArray(JSON.toJSONString(list), ServerInfoVo.class);
        logger.debug("ServerInfoServiceImpl method findServerInfo res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }


    @Override
    public Integer deleteById(Object id) {
        String bindAppName = baseDao.findBindAppNameByServerId(Parse.toLong(id));
        if (StringUtils.isNotEmpty(bindAppName)) {
            throw new BusinessException(ErrorCodesEnum.DATA_BINDING_ERR.code(), ErrorCodesEnum.DATA_BINDING_ERR.msg() + " binding app of " + bindAppName);
        }
        return super.deleteById(id);
    }

    @Override
    public Boolean deleteServerInfo(ServerInfoModifyReq request) {
        String id = Optional.ofNullable(request).map(ServerInfoModifyReq::getId).orElse(null);
        this.deleteById(id);
        List<Long> ids = apiInfoService.findApiByServerId(id).stream().map(ApiInfoCloudVo::getId).map(a -> Parse.toLong(a)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(ids)) {
            apiInfoService.deleteByIds(ids);
        }
        return true;
    }

    @Override
    public Boolean saveServerInfoAddReq(ServerInfoAddReq request) {
        ServerInfo serverInfo = new ServerInfo();
        try {
            BeanUtils.copyProperties(serverInfo, request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + " serverInfo ref err");
        }
        String deployType = Optional.of(serverInfo).map(ServerInfo::getDeployType).orElse("");
        String selectedVersion = Optional.of(serverInfo).map(ServerInfo::getSelectedVersion).orElse("");
        if (deployType.equals(DeployTypeEnum.KUBERNETES.getKey()) && StringUtils.isEmpty(selectedVersion)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " selectedVersion can not be empty");
        }

        String isFront = Optional.of(serverInfo).map(ServerInfo::getIsFront).orElse("");
        ;
        String isSsl = Optional.of(serverInfo).map(ServerInfo::getIsSsl).orElse("");
        ;
        String serverHost = Optional.of(serverInfo).map(ServerInfo::getServerHost).orElse("");
        ;
        String listenPort = Optional.of(serverInfo).map(ServerInfo::getListenPort).orElse("");
        ;
        String sslListenPort = Optional.of(serverInfo).map(ServerInfo::getSslListenPort).orElse("");
        ;
        String sslCrtKeyPath = Optional.of(serverInfo).map(ServerInfo::getSslCrtKeyPath).orElse("");
        ;
        String sslCrtPath = Optional.of(serverInfo).map(ServerInfo::getSslCrtPath).orElse("");
        String upSteamNodeNamex = Optional.of(serverInfo).map(ServerInfo::getUpsteamNodeName).orElse("");

        /*如果是前端项目*/
        if (isFront.equals(YesOrNoEnum.Yes.getKey()) && (StringUtils.isEmpty(isSsl) || StringUtils.isEmpty(serverHost) || StringUtils.isEmpty(listenPort))) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " isSsl & serverHost & listenPort can not be empty");
        }
        /*如果开启了Https*/
        if (isSsl.equals(YesOrNoEnum.Yes.getKey()) && (StringUtils.isEmpty(sslListenPort) || StringUtils.isEmpty(sslCrtKeyPath) || StringUtils.isEmpty(sslCrtPath))) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " sslListenPort & sslCrtKeyPath & sslCrtPath can not be empty");
        }

        if (this.isRepeatServerInfoByUpSteamNodeName(upSteamNodeNamex, null)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg() + " 该服务节点已被绑定，无法选择！");
        }

        this.save(serverInfo);
        return true;
    }

    @Override
    public Boolean isRepeatServerInfoByUpSteamNodeName(String upSteamNodeName, Long id) {
        Integer count = baseDao.countServerInfoByUpSteamNodeName(upSteamNodeName, id);
        count = (count == null) ? 0 : count;
        return count > 0;
    }

    @Override
    public List<String> findUpSteamNodeIdsByServerName(String serverName) {
        return baseDao.findUpSteamNodeIdsByServerName(serverName);
    }

    @Override
    public Boolean updateServerInfoModifyReq(ServerInfoModifyReq request) {
        ServerInfo serverInfo = new ServerInfo();
        try {
            BeanUtils.copyProperties(serverInfo, request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg() + "bean of ServerInfoModifyReq");
        }
        logger.debug("ServerInfoController update method target = " + JSON.toJSONString(serverInfo, SerializerFeature.WriteMapNullValue));


        String upSteamNodeNamex = Optional.of(serverInfo).map(ServerInfo::getUpsteamNodeName).orElse("");
        Long id = Optional.of(serverInfo).map(ServerInfo::getId).orElse(null);

        if (this.isRepeatServerInfoByUpSteamNodeName(upSteamNodeNamex, id)) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg() + " 该服务节点已被绑定，无法选择！");
        }

        this.update(serverInfo);
        return true;
    }

    @Override
    public List<ServerHostNodeVo> findSyncHostNode(List<UpSteamSyncNodeVo> upSteamNodeVos) {
        List<ServerHostNodeVo> list = baseDao.findSyncHostNode();
        List<ServerHostNodeVo> resp = new ArrayList<>();
        for (UpSteamSyncNodeVo u : upSteamNodeVos) {
            String nodeName = Optional.of(u).map(UpSteamSyncNodeVo::getNodeName).orElse("");
            for (ServerHostNodeVo s : list) {
                String upSteamNodeName = Optional.of(s).map(ServerHostNodeVo::getUpsteamNodeName).orElse("");
                if (nodeName.equals(upSteamNodeName)){
                    resp.add(s);
                }
            }
        }
        return resp;
    }
}