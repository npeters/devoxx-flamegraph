Application Sample

# Exemple 1 - fibo

- Démarrage de l'application de sample
```
[vagrant@localhost flamegraph-sample] gradle bootRun
```

-  Démarrage de tire de charge depuis la machine hôte
```
wrk -t10 -c20 -d30s http://localhost:8080/fibo/10
```

- Flamegraph
```
[vagrant@localhost flamegraph-sample] sudo perf-java-flames pid
```

# Exemple 2 - cpuoff
