package microservices.hr.infrastructure;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

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
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 10 * 1024 * 1024);

        kafkaProducer = new KafkaProducer<String, String>(props);
    }

    public static void main(String[] args) throws Exception {
        if (0 == args.length) throw new Exception("You must provide one or more kafka server address.");

        new OutboxWorkerApplication(args).serve();
    }

    public void serve() {
        while (true) {
            mySQL.connection().transaction((connection) -> {
                int rowCounter = 0;
                String[] ids = new String[10];

                ResultSet resultSet = connection
                        .preparedStatement("SELECT id, name, data FROM outbox WHERE processed = ? LIMIT 10 FOR UPDATE")
                        .setInt(1, 0)
                        .withResult();

                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String topic = resultSet.getString(2);
                    String payload = resultSet.getString(3);

                    kafkaProducer.send(new ProducerRecord<>(topic, payload));
                    ids[rowCounter++] = id;
                }

                if (rowCounter > 0) {
                    connection.updateQuery()
                            .update("outbox")
                            .set("processed", 1)
                            .whereIn("id", ids)
                            .toPreparedStatement()
                            .withoutResult();
                }
            });
        }
    }
}
