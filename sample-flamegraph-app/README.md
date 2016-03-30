# Application Sample

## Setup
Installer sur la machine hôte wrk https://github.com/wg/wrk/wiki/Installing-Wrk-on-Linux
La VM vagrant démarre avec un réseaux privé sur l'ip 192.168.33.10


## Exemple 1 - fibo

- Démarrage de l'application de sample
```
[vagrant@localhost flamegraph-sample-app] gradle bootRun
```
-  Démarrage de tire de charge depuis la machine hôte
```
wrk -t10 -c20 -d30s http://192.168.33.10:8080/fibo/10
```
- Flamegraph
```
[vagrant@localhost flamegraph-sample] sudo perf-java-flames pid
```

## Exemple 2 - cpuoff

TODO
- url /proxy
- cpuoff/cpuoff.sh
- SERVER_PORT=9000 gradle bootRun
