package org.dromara.flowerapplet.config;


import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Configuration
public class RedisListenerConfig {
    @Autowired
    private IFolwerAppletOrderService folwerAppletOrderService;

    @Autowired
    private IFolwerAppletCreditOrderService folwerAppletCreditOrderService;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 订阅一个全部变化事件频道 __keyevent@*
//        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@*"));

        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@*__:expired"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        // 如果使用自定义方法myHandleMessage，就把方法名作为第二个参数添入
        // return new MessageListenerAdapter(new RedisKeyChangeSubscriber(), "myHandleMessage");
        // 如果不传入第二个参数，则会使用默认方法handleMessage
        return new MessageListenerAdapter(new RedisKeyChangeSubscriber());
    }

    public class RedisKeyChangeSubscriber {
        // 自定义处理方法
        public void myHandleMessage(String message) {
//            System.out.println("RedisKeyChangeSubscriber Received message: " + message);
            // 在这里处理收到的消息
            String expiredKey = message.toString();
            log.info("获取数据:"+expiredKey);
            if (expiredKey.startsWith("order:")) {
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
                // 处理实际业务
            }

            if(expiredKey.startsWith("CreditOrder:")){
                // 一般来说，我们会这样设置过期订单的key："order:255 "
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
                log.info("订单ID:"+orderId);
                // 处理实际业务
            }
        }

        // message对应redis数据的key
        public void handleMessage(String message) {
//            System.out.println("RedisKeyChangeSubscriber Received message: " + message);
            // 在这里处理收到的消息
            String expiredKey = message.toString();
            log.info("获取数据:"+expiredKey);
            if (expiredKey.startsWith("order:")) {
                // 一般来说，我们会这样设置过期订单的key："order:255 "
                String orderId = expiredKey.split(":")[1];
                FolwerAppletOrderVo folwerAppletOrderVo = folwerAppletOrderService.queryById(Long.valueOf(orderId));
                if (folwerAppletOrderVo != null){
                    if(!folwerAppletOrderVo.getStatus().equals(2L)){
                        FolwerAppletOrderBo bo = new FolwerAppletOrderBo();
                        BeanUtils.copyProperties(folwerAppletOrderVo, bo);
                        bo.setStatus(2L);
                        bo.setCancelTime(new Date());
                        bo.setCancelMsg("订单超时未支付，系统自动取消");
                        Boolean b = folwerAppletOrderService.updateByBo(bo);
                    }
                }
                log.info("订单ID:"+orderId);
                // 处理实际业务
            }

            if(expiredKey.startsWith("CreditOrder:")){
                // 一般来说，我们会这样设置过期订单的key："order:255 "
                String orderId = expiredKey.split(":")[1];
                FolwerAppletCreditOrderVo folwerAppletCreditOrderVo = folwerAppletCreditOrderService.queryById(Long.valueOf(orderId));
                if (folwerAppletCreditOrderVo != null){
                    if(!folwerAppletCreditOrderVo.getStatus().equals(6L)){
                        FolwerAppletCreditOrderBo bo = new FolwerAppletCreditOrderBo();
                        BeanUtils.copyProperties(folwerAppletCreditOrderVo, bo);
                        bo.setStatus(6L);
                        bo.setCancelTime(new Date());
                        bo.setCancelMsg("订单超时未支付，系统自动取消");
                        Boolean b = folwerAppletCreditOrderService.updateByBo(bo);
                    }
                }
                log.info("订单ID:"+orderId);
                // 处理实际业务
            }
        }
    }

}
