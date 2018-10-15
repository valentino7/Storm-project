package sdcc2018.storm.control;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.StormSubmitter;
import org.apache.storm.mongodb.bolt.MongoUpdateBolt;
import org.apache.storm.mongodb.common.QueryFilterCreator;
import org.apache.storm.mongodb.common.SimpleQueryFilterCreator;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.control.bolt.*;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import org.apache.storm.tuple.Fields;
import sdcc2018.storm.entity.KafkaTranslator;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST;

public class TopologyGreeenDuration {

    private Properties properties;

    public TopologyGreeenDuration()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }
    public static void main(String[] args) throws Exception {
        new TopologyGreeenDuration().runMain(args);
    }

    public void runMain(String[] args) throws Exception {
        Config conf=this.getConfig();
        if (args != null && args.length > 0) {
            System.out.println("argument1=" + args[0]);
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(properties.getProperty("topologyName3"), conf, this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
        } else {
            System.out.println("Create local cluster");
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(properties.getProperty("topologyName3"), conf,this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
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
        config.setMessageTimeoutSecs(60000);
        return config;
    }

     protected StormTopology getTopologyKafkaSpout(KafkaSpoutConfig<String, JsonNode> spoutConfig) {
         String urlMongoDB=properties.getProperty("urlMongoDB");
         String collectionName= properties.getProperty("collectionNameControl");
         MongoMapper mapperUpdate = new CustomMongoUpdateMapperControl()
                 .withFields(Costant.PHASE);

         QueryFilterCreator updateQueryCreator = new SimpleQueryFilterCreator().withField(Costant.ID_INTERSECTION);

         MongoUpdateBolt mongoDBWebster = new MongoUpdateBolt(urlMongoDB, collectionName, updateQueryCreator, mapperUpdate);

         //if a new document should be inserted if there are no matches to the query filter
         mongoDBWebster.withUpsert(true);

        final TopologyBuilder tp = new TopologyBuilder();
        tp.setSpout(Costant.KAFKA_SPOUT, new KafkaSpout<>(spoutConfig), Costant.NUM_SPOUT_QUERY_2);
         tp.setBolt(Costant.FILTER_CONTROL, new FilterControlBolt(), Costant.NUM_FILTER_CONTROL).shuffleGrouping(Costant.KAFKA_SPOUT);
         tp.setBolt(Costant.SUM_BOLT, new SumBolt().withTumblingWindow(Duration.seconds(20)), Costant.NUM_SUM_BOLT)
                 .fieldsGrouping(Costant.FILTER_CONTROL, new Fields(Costant.ID_INTERSECTION));
         tp.setBolt(Costant.WEBSTER_BOLT, new WebsterBolt(), Costant.NUM_WEBSTER_BOLT)
                 .shuffleGrouping(Costant.SUM_BOLT);
        tp.setBolt(Costant.MONGODBWEBSTER,mongoDBWebster,Costant.NUM_MONGOBOLT15M).shuffleGrouping(Costant.WEBSTER_BOLT);
        return tp.createTopology();
    }
    public static KafkaSpoutConfig<String, JsonNode> getKafkaSpoutConfig(String bootstrapServers,String topicName,Properties properties) {

        KafkaTranslator kafkaTranslator = new KafkaTranslator();
        KafkaSpoutConfig.Builder<String,JsonNode> kafkaSpoutConfigBuilder = new KafkaSpoutConfig.Builder(bootstrapServers, StringDeserializer.class, org.apache.kafka.connect.json.JsonDeserializer.class, topicName);
        return kafkaSpoutConfigBuilder
                .setProp(ConsumerConfig.GROUP_ID_CONFIG,properties.getProperty("nameConsumerGroup3"))
                //.setRetry(getRetryService())
                //.setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(LATEST)
                //.setMaxUncommittedOffsets(250)
                .setRecordTranslator(kafkaTranslator)
                .build();
    }

}
