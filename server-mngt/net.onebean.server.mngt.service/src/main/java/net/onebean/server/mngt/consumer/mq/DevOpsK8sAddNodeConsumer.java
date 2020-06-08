package net.onebean.server.mngt.consumer.mq;

import com.rabbitmq.client.Channel;
import net.onebean.api.adapter.api.model.DevOpsK8sNotificationVo;
import net.onebean.core.error.BusinessException;
import net.onebean.server.mngt.common.ErrorCodesEnum;
import net.onebean.server.mngt.service.UpSteamNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DevOpsK8sAddNodeConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevOpsK8sAddNodeConsumer.class);

    @Autowired
    private UpSteamNodeService upSteamNodeService;

    @RabbitListener(queues = "devops.k8s.notification.add")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("DevOpsK8sAddNodeConsumer process access  text = " + text);
        try {
            //反序列化 参数校验非空
            DevOpsK8sNotificationVo param = upSteamNodeService.getDevOpsK8sNotificationVoFromJsontext(text);
            if (!upSteamNodeService.isRepeatByDevOpsK8sNotificationVo(param)) {
                //增加该条数据
                Boolean execResult = upSteamNodeService.addByDevOpsK8sNotificationVo(param);
                LOGGER.info("DevOpsK8sAddNodeConsumer execResult = " + execResult);
            }else{
                LOGGER.info("repeat data , do nothing!");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("DevOpsK8sAddNodeConsumer get err ,e = ", e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}