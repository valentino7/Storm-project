package sdcc2018.storm.simulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.Sensor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CustomKafkaProducer {

    public static void main(String args[]) throws IOException {

        Properties properties=new Properties();
        CustomKafkaProducer customKafkaProducer=new CustomKafkaProducer();
        InputStream is=customKafkaProducer.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
        String kafka_brokers=properties.getProperty("kafka.brokerurl");
        String kafka_topic=properties.getProperty("kafka.topic");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_brokers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.connect.json.JsonSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer<>(props);

        //metodo per inviare tuple ai bolt
        double max = 80;
        double min = 0;
        Sensor s;
        Random rand=new Random();
        ObjectMapper objectMapper=new ObjectMapper();
        while (true) {
            for (int i = 0; i < Costant.N_INTERSECTIONS; i++) {
                double saturation=0;//prendere dal db la saturazione
                for (int j = 0; j < Costant.SEM_INTERSEC; j++) {
                    s = new Sensor(i, j, min + rand.nextDouble() * (max - min), ThreadLocalRandom.current().nextInt(0, 100 + 1),saturation);
                    JsonNode jsonNode=objectMapper.valueToTree(s);
                    ProducerRecord<String,JsonNode>recordToSend=new ProducerRecord<>(kafka_topic,jsonNode);
                    System.err.println(recordToSend);
                    kafkaProducer.send(recordToSend);
                }
            }
            System.out.println("fine generazione tuple");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
