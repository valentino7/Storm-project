#!/bin/bash
echo stop kafka...
kubectl delete -f ~/file_yaml/kafka/kafka-broker.yml
kubectl delete -f ~/file_yaml/kafka/kafka-service.yml
kubectl delete -f ~/file_yaml/kafka/zookeeper.yml
