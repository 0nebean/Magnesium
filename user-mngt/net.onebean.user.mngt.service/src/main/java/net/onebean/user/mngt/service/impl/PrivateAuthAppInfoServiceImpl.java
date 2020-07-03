package net.onebean.user.mngt.service.impl;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.api.model.FindUagUserAppListResp;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import net.onebean.core.base.BaseBiz;
import net.onebean.user.mngt.model.PrivateAuthAppInfo;
import net.onebean.user.mngt.service.PrivateAuthAppInfoService;
import net.onebean.user.mngt.dao.PrivateAuthAppInfoDao;

import java.util.ArrayList;
import java.util.List;

/**
* @author 0neBean
* @description 私有令牌应用 serviceImpl
* @date 2020-07-03 21:15:42
*/
@Service
public class PrivateAuthAppInfoServiceImpl extends BaseBiz<PrivateAuthAppInfo, PrivateAuthAppInfoDao> implements PrivateAuthAppInfoService{

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