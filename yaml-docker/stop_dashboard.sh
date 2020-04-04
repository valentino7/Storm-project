#!/bin/bash
echo stop dashboard...
kubectl delete -f ~/file_yaml/dashboard/dashboard-deploy.yml
kubectl delete -f ~/file_yaml/dashboard/dashboard-admin.yml
