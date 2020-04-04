# Storm UI

Run with:

```
docker run -d --name storm-ui -p 8080:8080 --restart always --link nimbus:nimbus --link zookeeper:zookeeper adejonge/storm-ui
```
