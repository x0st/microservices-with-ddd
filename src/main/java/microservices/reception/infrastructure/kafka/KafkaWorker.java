package microservices.reception.infrastructure.kafka;

import com.alibaba.fastjson.JSONObject;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;
import java.util.Set;

import microservices.reception.core.GlobalEventHandler;

final public class KafkaWorker {
    public void serve(Set<String> topics, Properties properties, GlobalEventHandler globalEventHandler) {
        Consumer<String, String> consumer;

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(topics);

        while (true) {
            for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(100))) {
                JSONObject jsonObject = JSONObject.parseObject(record.value());

                try {
                    globalEventHandler.process(jsonObject);
                } catch (Exception exception) {
                    // TODO: add logging
                }
            }

            consumer.commitAsync();
        }
    }
}
