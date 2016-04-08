# Flamegraph on JVM running in a Docker container

Le generation du fichier de mapping des symboles de l'application Java s'execute directement dans le container.

En revanche les autres commandes s'executent sur la machine hôte car le conteneur Docker utilise le kernel de la machine hôte.

Le script perf-java-flames.sh se presente donc ainsi :

```
[HOST]          perf record <PID ON HOST>
[CONTAINER]     create-java-perf-map.sh <PID INSIDE CONTAINER>
[HOST]          rename perf-<PID INSIDE CONTAINER>.map -> perf-<PID ON HOST>.map
[HOST]          perf script
[HOST]          stackcollapse-perf.pl | flamegraph.pl
```

## Build

Construction de l'image Docker avec :

```
gcload-docker-build.sh
```

L'image docker contient :
* Java
* perf-map-agent
* les binaires du projet sample gcload

## Run

Lancement de l'application Java dans le conteneur Docker

```
gcload-docker-run.sh
```

## Flamegraph

Generation du flamegraph en enchainant les actions décrites ci-dessus

```
gcload-docker-flamegraph.sh
```
