package net.onebean.api.mngt.action.devops;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.api.mngt.common.ErrorCodesEnum;
import net.onebean.api.mngt.service.UpSteamNodeService;
import net.onebean.api.mngt.vo.DevOpsK8sNotificationVo;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.util.DateUtils;
import net.onebean.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * k8s devops controller
 * @author 0neBean
 */
@RestController
@RequestMapping("/kubernetes")
public class DevOpsKubernetesController {

    private final static Logger logger = LoggerFactory.getLogger(DevOpsKubernetesController.class);

    @Autowired
    private UpSteamNodeService upSteamNodeService;


    @GetMapping(value = "/addNode",produces = {"application/json"})
    public BaseResponse<Boolean> addNode(HttpServletRequest request) {
        logger.info("DevOpsKubernetesController addNode method access"+ DateUtils.getNowyyyy_MM_dd_HH_mm_ss());
        BaseResponse<Boolean> response = new BaseResponse<>();
        try {
            DevOpsK8sNotificationVo param = WebUtils.getParamVoFromHttpServletRequest(request, DevOpsK8sNotificationVo.class);
            logger.info("DevOpsKubernetesController addNode method target = " + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
            response = response.ok(upSteamNodeService.addByDevOpsK8sNotificationVo(param));
        } catch (BusinessException e) {
            response = response.failure(e.getCode(),e.getMsg());
        } catch (Exception e) {
            response = response.failure(ErrorCodesEnum.OTHER.code(),ErrorCodesEnum.OTHER.msg());
        }
        return response;
    }

}
