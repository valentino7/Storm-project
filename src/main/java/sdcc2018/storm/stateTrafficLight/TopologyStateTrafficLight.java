package sdcc2018.storm.stateTrafficLight;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.StormSubmitter;
import org.apache.storm.mongodb.bolt.MongoUpdateBolt;
import org.apache.storm.mongodb.common.QueryFilterCreator;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.KafkaTranslator;
import sdcc2018.storm.stateTrafficLight.bolt.FilterStateBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.topology.TopologyBuilder;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST;

public class TopologyStateTrafficLight implements Serializable {
    private Properties properties;
    public TopologyStateTrafficLight()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }
    public static void main(String[] args) throws Exception {
        new TopologyStateTrafficLight().runMain(args);
    }

    public void runMain(String[] args) throws Exception {
        Config conf=this.getConfig();
        if (args != null && args.length > 0) {
            System.out.println("argument1=" + args[0]);
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(properties.getProperty("topologyName4"), conf, this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
        } else {
            System.out.println("Create local cluster");
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(properties.getProperty("topologyName4"), conf,this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
            //shutdown the cluster
            /*Thread.sleep(15000);
             cluster.killTopology(properties.getProperty("topologyName"));
             cluster.shutdown();
             System.exit(0);*/
        }
    }

    protected Config getConfig(){
        Config config = new Config();
        config.setDebug(false);
        config.setMessageTimeoutSecs(30000);
        return config;
    }

     protected StormTopology getTopologyKafkaSpout(KafkaSpoutConfig<String, JsonNode> spoutConfig) {
         String urlMongoDB=properties.getProperty("urlMongoDB");
         String collectionName= properties.getProperty("collectionNameStateTrafficLight");
         MongoMapper mapperUpdate = new CustomMongoUpdateMapperQuery4()
                 .withFields(Costant.ID_INTERSECTION, Costant.ID_SENSOR);

         QueryFilterCreator updateQueryCreator = new SimpleQueryFilterCreator().withField(Costant.ID_INTERSECTION);


         MongoUpdateBolt updateBoltStateSemaphore = new MongoUpdateBolt(urlMongoDB, collectionName, updateQueryCreator, mapperUpdate);

         //if a new document should be inserted if there are no matches to the query filter
         updateBoltStateSemaphore.withUpsert(true);

        final TopologyBuilder tp = new TopologyBuilder();
        tp.setSpout(Costant.KAFKA_SPOUT, new KafkaSpout<>(spoutConfig), Costant.NUM_SPOUT_QUERY_1);
        tp.setBolt(Costant.CHECK_STATE_BOLT,new FilterStateBolt(),Costant.NUM_CHECK_STATE_BOLT).shuffleGrouping(Costant.KAFKA_SPOUT);
        tp.setBolt(Costant.MONGODBSTATEBOLT,updateBoltStateSemaphore,Costant.NUM_MONGOBOLTSTATEBOLT).shuffleGrouping(Costant.CHECK_STATE_BOLT);
        return tp.createTopology();
    }
    public static KafkaSpoutConfig<String, JsonNode> getKafkaSpoutConfig(String bootstrapServers, String topicName, Properties properties) {

        KafkaTranslator kafkaTranslator = new KafkaTranslator();
        KafkaSpoutConfig.Builder<String,JsonNode> kafkaSpoutConfigBuilder = new KafkaSpoutConfig.Builder(bootstrapServers, StringDeserializer.class, org.apache.kafka.connect.json.JsonDeserializer.class, topicName);
        return kafkaSpoutConfigBuilder
                .setProp(ConsumerConfig.GROUP_ID_CONFIG,properties.getProperty("nameConsumerGroup4"))
                //.setRetry(getRetryService())
                //.setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(LATEST)
                //.setMaxUncommittedOffsets(250)
                .setRecordTranslator(kafkaTranslator)
                .build();
    }
    public static KafkaSpoutRetryService getRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(KafkaSpoutRetryExponentialBackoff.TimeInterval.microSeconds(500),
                KafkaSpoutRetryExponentialBackoff.TimeInterval.milliSeconds(2), Integer.MAX_VALUE, KafkaSpoutRetryExponentialBackoff.TimeInterval.seconds(10));
    }
}
