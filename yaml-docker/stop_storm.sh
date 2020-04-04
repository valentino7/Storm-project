#!/bin/bash
echo stop storm...
kubectl delete -f ~/file_yaml/storm/svc-zookeeper.yaml
kubectl delete -f ~/file_yaml/storm/pod-zookeeper.yaml
kubectl delete -f ~/file_yaml/storm/svc-storm-nimbus.yaml
kubectl delete -f ~/file_yaml/storm/pod-storm-nimbus.yaml
kubectl delete -f ~/file_yaml/storm/svc-storm-supervisor.yaml
kubectl delete -f ~/file_yaml/storm/rc-storm-supervisor.yaml
kubectl delete -f ~/file_yaml/storm/svc-storm-ui.yaml
kubectl delete -f ~/file_yaml/storm/pod-storm-ui.yaml
