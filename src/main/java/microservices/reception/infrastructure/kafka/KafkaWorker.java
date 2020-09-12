package microservices.reception.infrastructure.kafka;

import com.alibaba.fastjson.JSONObject;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import microservices.reception.core.GlobalEventHandler;

final public class KafkaWorker {
    public void serve(Set<String> topics, Properties properties, GlobalEventHandler globalEventHandler) {
        Logger logger = LoggerFactory.getLogger(KafkaWorker.class);
        Consumer<String, String> consumer;

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(topics);

        while (true) {
            for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(100))) {
                JSONObject jsonObject = JSONObject.parseObject(record.value());

                try {
                    globalEventHandler.process(jsonObject);
                } catch (Exception exception) {
                    logger.error("An error has occurred while processing events.", exception);
                }
            }

            consumer.commitAsync();
        }
    }
}
