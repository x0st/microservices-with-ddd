package microservices.hr.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.Properties;

import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.database.mysql.ResultSet;
import microservices.hr.infrastructure.di.OutboxWorkerApplicationModule;

final public class OutboxWorkerApplication {
    private final Injector injector;
    private final MySQLClient mySQL;
    private final KafkaProducer<String, String> kafkaProducer;

    private OutboxWorkerApplication(String[] kafkaServers) {
        injector = Guice.createInjector(
                new OutboxWorkerApplicationModule()
        );

        mySQL = injector.getInstance(MySQLClient.class);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaServers));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 10 * 1024 * 1024);

        kafkaProducer = new KafkaProducer<String, String>(props);
    }

    public static void main(String[] args) throws Exception {
        if (0 == args.length) throw new Exception("You must provide one or more kafka server address.");

        new OutboxWorkerApplication(args).serve();
    }

    public void serve() throws Exception {
        while (true) {
            mySQL.connection().transaction((connection) -> {
                ArrayList<String> eventIdentifiers = new ArrayList<>(10);

                // Take a batch of events from MySQL
                ResultSet resultSet = connection
                        .preparedStatement("SELECT id, name, data FROM outbox WHERE processed = ? LIMIT 10 FOR UPDATE")
                        .setInt(1, 0)
                        .withResult();

                // Send the events to Kafka
                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String topic = resultSet.getString(2);
                    String payload = resultSet.getString(3);
                    eventIdentifiers.add(id);

                    kafkaProducer.send(new ProducerRecord<>(topic, payload));
                }

                // Mark the events as processed
                if (!eventIdentifiers.isEmpty()) {
                    connection.updateQuery()
                            .update("outbox")
                            .set("processed", 1)
                            .whereIn("id", eventIdentifiers)
                            .toPreparedStatement()
                            .withoutResult();
                }
            });
        }
    }
}
