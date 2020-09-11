package microservices.reception.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import microservices.reception.core.GlobalEventHandler;
import microservices.reception.core.GlobalEventHandlerMap;
import microservices.reception.infrastructure.di.RepositoryModule;
import microservices.reception.infrastructure.di.SharedModule;
import microservices.reception.infrastructure.di.GlobalEventHandlerModule;
import microservices.reception.infrastructure.kafka.KafkaWorker;

final public class GlobalEventConsumerApplication {
    private final Injector injector;
    private final GlobalEventHandler globalEventHandler;

    private final String kafkaServers;
    private final String kafkaConsumerGroupId;
    private final Set<String> kafkaTopics;

    private GlobalEventConsumerApplication(
            String eventHandlerName,
            String kafkaServers,
            Set<String> kafkaTopics,
            String kafkaConsumerGroupId
    ) throws Exception {
        injector = Guice.createInjector(
                new RepositoryModule(),
                new GlobalEventHandlerModule(),
                new SharedModule()
        );

        this.kafkaTopics = kafkaTopics;
        this.kafkaServers = kafkaServers;
        this.kafkaConsumerGroupId = kafkaConsumerGroupId;
        this.globalEventHandler = injector.getInstance(GlobalEventHandlerMap.class).get(eventHandlerName);

        if (null == this.globalEventHandler) throw new Exception("No event handler exists with the given name.");
    }

    public static void main(String[] args) throws Exception {
        if (4 != args.length) throw new Exception(
                "You must provide arguments in the following order: " +
                        "[event handler name] [kafka servers] [kafka topics] [kafka consumer group id]."
        );

        new GlobalEventConsumerApplication(
                args[0],
                args[1],
                new HashSet<>(Arrays.asList(args[2].split(","))),
                args[3]
        ).serve();
    }

    public void serve() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaConsumerGroupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        new KafkaWorker().serve(this.kafkaTopics, properties, globalEventHandler);
    }
}
