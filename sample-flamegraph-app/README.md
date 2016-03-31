# Application Sample

## Setup
Il faut installer sur la machine hôte wrk https://github.com/wg/wrk/wiki/Installing-Wrk-on-Linux
La VM vagrant démarre avec un réseaux privé sur l'ip 192.168.33.10


## Exemple 1 - fibo

- Démarrer l'application de sample
```
[vagrant@localhost flamegraph-sample-app] gradle bootRun
```
- Démarrer le tire de charge depuis la machine hôte
```
wrk -t10 -c20 -d30s http://192.168.33.10:8080/fibo/10
```
- Génerrer le Flamegraph
```
[vagrant@localhost flamegraph-sample-app] perf-java-flames pid
```

## Exemple 2 - offcpu

L'application génére un appelle à un service distant. 
Le offcpu permet de reperer la latence 

- Démarrer l'application de sample
```
[vagrant@localhost flamegraph-sample-app] gradle bootRun
```
- Démarrer l'application de sample sur la machine hôte
```
SERVER_PORT=9000 gradle bootRun
```
- Démarrer le tire de charge depuis la machine hôte
```
 wrk -L   -t10 -c20 -d5m -R10 http://192.168.33.10:8080/proxy
```
- Génerrer le Flamegraph
```
[vagrant@localhost vagrant] ./offcpu.sh
```