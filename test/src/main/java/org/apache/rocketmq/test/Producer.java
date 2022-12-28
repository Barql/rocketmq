package org.apache.rocketmq.test;

import java.io.IOException;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException, IOException {

        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        /*
         * Launch the instance.
         */
        producer.setNamesrvAddr("127.0.0.1:9876"); // 配置nameServer地址
        producer.start();

        for (int i = 0; i < 1; i++) { // i 可以随便配置
            try {

                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                Message msg = new Message("abc" /* Topic */, "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                // msg.setDelayTimeLevel(10);
                /*
                * Call send message to deliver message to one of brokers.
                */
                SendResult sendResult = producer.send(msg);

                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();
    }
}