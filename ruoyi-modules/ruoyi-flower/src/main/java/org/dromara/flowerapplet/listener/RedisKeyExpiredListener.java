package org.dromara.flowerapplet.listener;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.redis.config.properties.RedissonProperties;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisKeyExpiredListener  implements MessageListener {

    @Autowired
    private IFolwerAppletOrderService folwerAppletOrderService;

    @Autowired
    private IFolwerAppletCreditOrderService folwerAppletCreditOrderService;

    /**
     * 监听过期事件
     * @param connectionFactory
     * @param listener
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       RedisKeyExpiredListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 配置监听的 Redis Key 过期事件的频道
        container.addMessageListener(listener, new PatternTopic("__keyevent@*__:expired"));

        return container;
    }

    @Bean
    public RedisKeyExpiredListener redisKeyExpiredListener() {
        return new RedisKeyExpiredListener();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
//        log.info("获取数据:"+expiredKey);
        if (expiredKey.startsWith("order:")) {
            // 处理实际业务
            // 一般来说，我们会这样设置过期订单的key："order:255 "
            String orderId = expiredKey.split(":")[1];
            FolwerAppletOrderVo folwerAppletOrderVo = folwerAppletOrderService.queryById(Long.valueOf(orderId));
            if (folwerAppletOrderVo != null){
                if(folwerAppletOrderVo.getStatus().equals(0L)){
                    FolwerAppletOrderBo bo = MapstructUtils.convert(folwerAppletOrderVo, FolwerAppletOrderBo.class);
                    bo.setStatus(2L);
                    bo.setCancelTime(new Date());
                    bo.setCancelMsg("订单超时未支付，系统自动取消");
                    Boolean b = folwerAppletOrderService.updateByBo(bo);
                }
            }
            log.info("订单ID:"+orderId);
        }

        // Legacy edge behavior: credit-mall timeout handling is preserved for compatibility,
        // but it is not part of the standard mall backbone transaction flow.
        if(expiredKey.startsWith("CreditOrder:")){
            // 一般来说，我们会这样设置过期订单的key："order:255 "
            // 处理实际业务
            String orderId = expiredKey.split(":")[1];
            FolwerAppletCreditOrderVo folwerAppletCreditOrderVo = folwerAppletCreditOrderService.queryById(Long.valueOf(orderId));
            if (folwerAppletCreditOrderVo != null){
                if(folwerAppletCreditOrderVo.getStatus().equals(0L)){
                    FolwerAppletCreditOrderBo convert = MapstructUtils.convert(folwerAppletCreditOrderVo, FolwerAppletCreditOrderBo.class);
                    convert.setStatus(2L);
                    convert.setCancelTime(new Date());
                    convert.setCancelMsg("订单超时未支付，系统自动取消");
                    Boolean b = folwerAppletCreditOrderService.updateByBo(convert);
                }
            }
            log.info("积分订单ID:"+orderId);

        }
    }
}
