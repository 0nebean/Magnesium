package net.onebean.api.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.api.model.ServerMachineNodeAddReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeInfoModifyReq;
import net.onebean.api.mngt.api.model.ServerMachineNodeVo;
import net.onebean.api.mngt.api.model.enumModel.ServerMachineTypeEnum;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.dao.ServerMachineNodeDao;
import net.onebean.api.mngt.model.ServerMachineNode;
import net.onebean.api.mngt.service.ServerMachineNodeService;
import net.onebean.api.mngt.vo.ServerMachineNodeSyncVo;
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
* @author 0nebean
* @description 服务器节点 serviceImpl
* @date 2019-11-01 14:19:09
*/
@Service
public class ServerMachineNodeServiceImpl extends BaseBiz<ServerMachineNode, ServerMachineNodeDao> implements ServerMachineNodeService{

    private final static Logger logger = LoggerFactory.getLogger(ServerMachineNodeServiceImpl.class);

    @Override
    public List<ServerMachineNodeVo> findServerMachineNodeVo(ServerMachineNodeAddReq param, Pagination page, Sort sort) {

        String ipAddress = Optional.ofNullable(param).map(ServerMachineNodeAddReq::getIpAddress).orElse(null);
        String serverMachineType = Optional.ofNullable(param).map(ServerMachineNodeAddReq::getServerMachineType).orElse(null);
        List<Condition> paramList = new ArrayList<>();

        if (StringUtils.isNotEmpty(ipAddress)){
            Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
            condition.setValue(ipAddress);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(serverMachineType)){
            Condition condition = Condition.parseModelCondition("serverMachineType@string@eq");
            condition.setValue(serverMachineType);
            paramList.add(condition);
        }
        List<ServerMachineNode> list = this.find(page,paramList,sort);
        logger.debug("ServerMachineNodeServiceImpl method findServerMachineNode list = "+ JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)){
            return Collections.emptyList();
        }

        List<ServerMachineNodeVo> res = JSON.parseArray(JSON.toJSONString(list), ServerMachineNodeVo.class);
        logger.debug("ServerMachineNodeServiceImpl method findServerMachineNode res = "+ JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)){
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }

        return res;
    }

    @Override
    public ServerMachineNodeVo findVoById(Object id) {
        ServerMachineNode ServerMachineNode = this.findById(id);
        ServerMachineNodeVo ServerMachineNodeVo = new ServerMachineNodeVo();
        if (null != ServerMachineNode){
            try {
                BeanUtils.copyProperties(ServerMachineNodeVo,ServerMachineNode);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg()+"bean of ServerMachineNodeVo");
            }
        }else{
            return null;
        }
        return ServerMachineNodeVo;
    }

    @Override
    public Long add(ServerMachineNodeAddReq request) {
        ServerMachineNode ServerMachineNode = new ServerMachineNode();

        try {
            BeanUtils.copyProperties(ServerMachineNode,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        String ipAddress = Optional.ofNullable(request).map(ServerMachineNodeAddReq::getIpAddress).orElse(null);
        if (StringUtils.isEmpty(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of ipAddress");
        }
        if (isRepeatByIpAddressAndId(ipAddress,null)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        this.save(ServerMachineNode);
        return ServerMachineNode.getId();
    }

    @Override
    public Integer update(ServerMachineNodeInfoModifyReq request) {
        ServerMachineNode target = new ServerMachineNode();
        try {
            BeanUtils.copyProperties(target,request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        logger.debug("update method target = "+ JSON.toJSONString(target, SerializerFeature.WriteMapNullValue));
        String ipAddress = Optional.ofNullable(request).map(ServerMachineNodeInfoModifyReq::getIpAddress).orElse(null);
        String id = Optional.ofNullable(request).map(ServerMachineNodeInfoModifyReq::getId).orElse(null);
        if (StringUtils.isEmpty(ipAddress)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of ipAddress");
        }
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" field of id");
        }
        if (isRepeatByIpAddressAndId(ipAddress,id)){
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(),ErrorCodesEnum.DATA_REPEAT_ERR.msg());
        }
        return this.update(target);
    }

    @Override
    public Boolean isRepeatByIpAddressAndId(String ipAddress,Object id) {
        List<Condition> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(ipAddress)){
            Condition condition = Condition.parseModelCondition("ipAddress@string@eq");
            condition.setValue(ipAddress);
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
    public List<ServerMachineNodeSyncVo> findSyncOpenrestyEndPoint() {
        Condition param = Condition.parseModelCondition("serverMachineType@string@wq");
        param.setValue(ServerMachineTypeEnum.OPENRESTY.getKey());
        List<ServerMachineNode> list = this.find(null,param);
        List<ServerMachineNodeSyncVo> res;
        if (CollectionUtil.isNotEmpty(list)){
            try {
                res = JSON.parseArray(JSON.toJSONString(list), ServerMachineNodeSyncVo.class);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
        }else{
            res = Collections.emptyList();
        }
        return res;
    }

    @Override
    public List<ServerMachineNodeSyncVo> findSyncKubernetesEndPoint() {
        Condition param = Condition.parseModelCondition("serverMachineType@string@wq");
        param.setValue(ServerMachineTypeEnum.KUBERNETES_MASTER.getKey());
        List<ServerMachineNode> list = this.find(null,param);
        List<ServerMachineNodeSyncVo> res;
        if (CollectionUtil.isNotEmpty(list)){
            try {
                res = JSON.parseArray(JSON.toJSONString(list), ServerMachineNodeSyncVo.class);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
        }else{
            res = Collections.emptyList();
        }
        return res;
    }
}