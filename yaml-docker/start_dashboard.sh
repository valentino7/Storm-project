#!/bin/bash
kubectl create -f ~/file_yaml/dashboard/dashboard-deploy.yml
kubectl create -f ~/file_yaml/dashboard/dashboard-admin.yml
gnome-terminal -e "bash -c \"kubectl proxy; exec bash\""

echo http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/
# go to http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/
