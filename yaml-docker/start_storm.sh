#!/bin/bash
kubectl create -f ~/file_yaml/storm/svc-zookeeper.yaml
kubectl create -f ~/file_yaml/storm/pod-zookeeper.yaml
sleep 10
kubectl create -f ~/file_yaml/storm/svc-storm-nimbus.yaml
kubectl create -f ~/file_yaml/storm/pod-storm-nimbus.yaml
sleep 10
kubectl create -f ~/file_yaml/storm/svc-storm-supervisor.yaml
kubectl create -f ~/file_yaml/storm/rc-storm-supervisor.yaml
kubectl create -f ~/file_yaml/storm/svc-storm-ui.yaml
kubectl create -f ~/file_yaml/storm/pod-storm-ui.yaml
