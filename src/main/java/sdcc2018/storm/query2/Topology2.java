package sdcc2018.storm.query2;

import com.fasterxml.jackson.databind.JsonNode;
import sdcc2018.storm.entity.Costant;
import sdcc2018.storm.entity.KafkaTranslator;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.mongodb.bolt.MongoUpdateBolt;
import org.apache.storm.mongodb.common.QueryFilterCreator;
import org.apache.storm.mongodb.common.SimpleQueryFilterCreator;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import org.apache.storm.tuple.Fields;
import sdcc2018.storm.query2.bolt.*;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST;

public class Topology2 {

    private Properties properties;

    public Topology2()throws Exception{
        properties=new Properties();
        InputStream is=this.getClass().getResourceAsStream("/config.properties");
        properties.load(is);
    }
    public static void main(String[] args) throws Exception {
        new Topology2().runMain(args);
    }

    protected void runMain(String[] args) throws Exception {
        Config conf=this.getConfig();
        if (args != null && args.length > 0) {
            System.out.println("argument1=" + args[0]);
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(properties.getProperty("topologyName2"), conf, this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
        } else {
            System.out.println("Create local cluster");
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(properties.getProperty("topologyName2"), conf,this.getTopologyKafkaSpout(getKafkaSpoutConfig(properties.getProperty("kafka.brokerurl"),properties.getProperty("kafka.topic"),this.properties)));
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
         String collectionName= properties.getProperty("collectionNameMedian");
         MongoMapper mapperUpdate = new CustomMongoUpdateMapperQuery2()
                 .withFields(Costant.ID, Costant.RANK_TOPK);

         QueryFilterCreator updateQueryCreator = new SimpleQueryFilterCreator().withField(Costant.ID);

         MongoUpdateBolt updateBolt15M = new MongoUpdateBolt(urlMongoDB, collectionName, updateQueryCreator, mapperUpdate);
         MongoUpdateBolt updateBolt1H = new MongoUpdateBolt(urlMongoDB, collectionName, updateQueryCreator, mapperUpdate);
         MongoUpdateBolt updateBolt24H = new MongoUpdateBolt(urlMongoDB, collectionName, updateQueryCreator, mapperUpdate);

         //if a new document should be inserted if there are no matches to the query filter
         updateBolt15M.withUpsert(true);
         updateBolt1H.withUpsert(true);
         updateBolt24H.withUpsert(true);

        final TopologyBuilder tp = new TopologyBuilder();
        tp.setSpout(Costant.KAFKA_SPOUT, new KafkaSpout<>(spoutConfig), Costant.NUM_SPOUT_QUERY_1);

         tp.setBolt(Costant.FILTER_QUERY_2, new FilterMedianBolt(), Costant.NUM_FILTER_QUERY2).shuffleGrouping(Costant.KAFKA_SPOUT);

         tp.setBolt(Costant.MEDIAN15M_BOLT, new MedianBolt().withTumblingWindow(Duration.seconds(10)), Costant.NUM_MEDIAN_15M_BOLT)
                 .fieldsGrouping(Costant.FILTER_QUERY_2,Costant.STREAM_15M, new Fields(Costant.ID));

         tp.setBolt(Costant.MEDIAN1H_BOLT, new MedianBolt().withTumblingWindow(Duration.seconds(20)), Costant.NUM_MEDIAN_1H_BOLT)
                 .fieldsGrouping(Costant.FILTER_QUERY_2,Costant.STREAM_1H, new Fields(Costant.ID));

         tp.setBolt(Costant.MEDIAN24H_BOLT, new MedianBolt().withTumblingWindow(Duration.seconds(40)), Costant.NUM_MEDIAN_24H_BOLT)
                 .fieldsGrouping(Costant.FILTER_QUERY_2,Costant.STREAM_24H, new Fields(Costant.ID));

         tp.setBolt(Costant.GLOBAL15M_MEDIAN, new GlobalMedianBolt(Costant.ID15M, Costant.NUM_MEDIAN_15M_BOLT), Costant.NUM_GLOBAL_BOLT)
                 .shuffleGrouping(Costant.MEDIAN15M_BOLT);

         tp.setBolt(Costant.GLOBAL1H_MEDIAN, new GlobalMedianBolt(Costant.ID1H, Costant.NUM_MEDIAN_1H_BOLT), Costant.NUM_GLOBAL_BOLT)
                 .shuffleGrouping(Costant.MEDIAN1H_BOLT);

         tp.setBolt(Costant.GLOBAL24H_MEDIAN, new GlobalMedianBolt(Costant.ID24H, Costant.NUM_MEDIAN_24H_BOLT), Costant.NUM_GLOBAL_BOLT)
                 .shuffleGrouping(Costant.MEDIAN24H_BOLT);
        tp.setBolt(Costant.MONGODB15M,updateBolt15M,Costant.NUM_MONGOBOLT15M).shuffleGrouping(Costant.GLOBAL15M_MEDIAN);
        tp.setBolt(Costant.MONGODB1H,updateBolt1H,Costant.NUM_MONGOBOLT1H).shuffleGrouping(Costant.GLOBAL1H_MEDIAN);
        tp.setBolt(Costant.MONGODB24H,updateBolt24H,Costant.NUM_MONGOBOLT24H).shuffleGrouping(Costant.GLOBAL24H_MEDIAN);
        return tp.createTopology();
    }
    public static KafkaSpoutConfig<String, JsonNode> getKafkaSpoutConfig(String bootstrapServers, String topicName, Properties properties) {
        KafkaTranslator kafkaTranslator = new KafkaTranslator();
        KafkaSpoutConfig.Builder<String,JsonNode> kafkaSpoutConfigBuilder = new KafkaSpoutConfig.Builder(bootstrapServers, StringDeserializer.class, org.apache.kafka.connect.json.JsonDeserializer.class, topicName);
        return kafkaSpoutConfigBuilder
                .setProp(ConsumerConfig.GROUP_ID_CONFIG,properties.getProperty("nameConsumerGroup2"))
                //.setRetry(getRetryService())
                //.setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(LATEST)
                //.setMaxUncommittedOffsets(250)
                .setRecordTranslator(kafkaTranslator)
                .build();
    }
}
