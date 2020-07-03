package net.onebean.api.mngt.service;


import net.onebean.api.mngt.vo.GetAccTokenVo;

/**
 * @author 0neBean
 */
public interface UagInfoCacheService {
    /**
     * 缓存accessToken
     * @param getAccTokenVo 实体类
     * @return bool
     */
    Boolean cacheAccessToken(GetAccTokenVo getAccTokenVo);
}
