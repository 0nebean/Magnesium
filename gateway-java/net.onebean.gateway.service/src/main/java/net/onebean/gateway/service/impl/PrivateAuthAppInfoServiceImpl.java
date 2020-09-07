package net.onebean.gateway.service.impl;

import net.onebean.core.base.BaseBiz;
import net.onebean.core.error.BusinessException;
import net.onebean.gateway.common.ErrorCodesEnum;
import net.onebean.gateway.model.PrivateAuthAppInfo;
import net.onebean.gateway.service.PrivateAuthAppInfoService;
import net.onebean.gateway.vo.FindUagUserAppListResp;
import net.onebean.gateway.dao.PrivateAuthAppInfoDao;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Icc
* @description 私有令牌应用 serviceImpl
* @date 2020-07-03 21:15:42
*/
@Service
public class PrivateAuthAppInfoServiceImpl extends BaseBiz<PrivateAuthAppInfo, PrivateAuthAppInfoDao> implements PrivateAuthAppInfoService {

    @Override
    public List<FindUagUserAppListResp> findAll2FindUagUserAppListResp() {
        List<PrivateAuthAppInfo> list = this.findAll();
        List<FindUagUserAppListResp> res = new ArrayList<>();
        for (PrivateAuthAppInfo authAppInfo : list) {
            FindUagUserAppListResp resp = new FindUagUserAppListResp();
            try {
                BeanUtils.copyProperties(resp,authAppInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
            }
            res.add(resp);
        }
        return res;
    }
}