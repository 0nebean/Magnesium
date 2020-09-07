package net.onebean.gateway.component;

import net.onebean.core.model.UagLoginSessionInfo;
import net.onebean.gateway.annotation.UagOperationLog;
import net.onebean.gateway.service.UagOperatorLogService;
import net.onebean.sso.sdk.core.UagSsoUtils;
import net.onebean.gateway.vo.UagOperationLogMqReq;
import net.onebean.util.PropUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@Aspect
public class UagOperationLogAop {


    private final String ExpGetResultDataPonit = "(execution(* net.onebean..action..*.*(..))) && @annotation(uagOperationLog)";

    @Autowired
    private UagOperatorLogService uagOperatorLogService;

    /**
     * 环绕aop 设置枚举映射值
     * @param proceedingJoinPoint 切入点
     * @param uagOperationLog 注解
     * @return obj
     * @throws Throwable 抛出异常
     */
    @Around(value = ExpGetResultDataPonit)
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint, UagOperationLog uagOperationLog) throws Throwable {

        UagLoginSessionInfo uagLoginSessionInfo  = UagSsoUtils.getCurrentUagLoginSessionInfo();
        String description = uagOperationLog.description();
        String appId = Optional.ofNullable(PropUtil.getInstance().getConfig(PropUtil.DEFLAULT_NAME_SPACE,"spring.application.name")).orElse("");
        String uagUserId =  Optional.ofNullable(uagLoginSessionInfo).map(UagLoginSessionInfo::getUagUserId).orElse("");
        String uagUserNickName = Optional.ofNullable(uagLoginSessionInfo).map(UagLoginSessionInfo::getUagUserNickName).orElse("");
        UagOperationLogMqReq req = new UagOperationLogMqReq(description,appId,uagUserId,uagUserNickName);
        uagOperatorLogService.saveUagOperatorLog(req);
        return proceedingJoinPoint.proceed();
    }
}