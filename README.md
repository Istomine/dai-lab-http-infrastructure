# Labo 5 HTTP INFRSASTRUCTURE

## Goal

Le but de ce laboratoire est de mettre en place une infrastructure web qui contient les points suivants:
- Mise en place d'un site web statique
- Deploiement du site web avec un docker compose
- Creation d'une api HTTP en Java
- Mise en place d'un reverse proxy avec Traefik
- Mise en place de load balancing pour la scability avec Traefik
- Mise en place du load balancing avec round-robin et sticky session
- Mise en place du chiffrement avec HTTPS

Dans ce rapport nous allons apporter des precisions sur les differentes etapes 

## Step 1

Le but de cette etape est de mettre un place un site statique avec Docker et Nginx.

Nous avons créé un dossier sweb avec la structure suivante. Le dossier www qui correspond au fichier html/css du site web, un Dockerfile ainsi qu'un nginx.conf.

Nous allons decrire nos fichiers de configuration de nginx ainsi que le Dockerfile

### Nginx.conf

Le fichier commence par `worker_processes 1` qui sert a specifier a nginx que nous voulons utiliser un processus pour gerer les requetes. Vu que le site est un simple site statique il nous semble pas utile de mettre plus de process.

La ligne `events {
    worker_connections 1024;
}
` sert a specifier le nombre de connection simultané. Vu que le labo sert a simuler une grosse architecture nous avons mis un nombre un peu grand.