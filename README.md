# Labo 5 HTTP Infrastructure

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

Ensuite nous allons voir les 2 lignes suivantes : `listen 80` et `server_name localhost`. La premiere signifie que l'on ecoute le port 80. Ce port est utilisé par le protocole HTTP. La deuxieme serta specifier le hostname. Ce qui veut dire que ce serveur gerera toute les connections qui auront dans le champ `Host` de leur requete HTTP localhost.

Ensuite nous avons la regée 
```
location / {
            root /usr/share/nginx/html;
            index index.html;
        }
```
Cette configuration indique que pour les url / (qui correspond a root) qu'il faudra regarder les fichier dans le dossier mis apres `root`. Et que si aucun fichier n'est specifié qu'il serve le fichier mis apres l'option `index`.

Pour que cela fonctionne il faut aussi ajouter une regle qui permettront d'inclure les fichier css. Ceci peut etre fait avec la config suivante 
```
location ~ \.css$ {
            root /usr/share/nginx/html;
            types {
                text/css css; 
                }
        }
```
Cette regle s'applique a tout les fichiers qui finissent par .css et indique au serveur que le type des fichier est css. 

### Dockerfile

Le dockerfile est basé sur l'image publique nginx. 

Les 2 premieres lignes sont
```
COPY www /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
```
Ces 2 lignes servent a prendre les fichier du coté machine (en premier les fichier du site et ensuite le fichier de config nginx).

Il les copie aux bons endroits du container docker.

Ensuite nous utilions la commnde `EXPOSE 80` pour exposer le port 80 hors du container et permettre a l'exterieur de lui addresser des requetes sur ce port.

Puis la derniere commande est `CMD ["nginx", "-g", "daemon off;"]` qui lance nging avec les options `-g` et `daemon:off`. Le flag `-g` qui permet de passer des options a nginx et `deamin:off` permet d'utiliser nginx pas en background qui est un good practice pour dans un container.

### Test

Pour tester si cette étape fonctionne il suffit de build l'image docker, lancer l'image puis de se rendre a l'addresse `localhost:80` et il devrait avoir le site statique qui s'affiche avec le css

## Step 2

Pour cette etape, nous devons simplifier le deploiement de notre simple application avec un docker compose. En outre, il doit etre possible de build notre Dockerfile via le docker compose

Pour ceci il faut créer un fichier compose.yaml

### Compose.yaml

Pour pouvoir dans un premier temps lancer l'application avec docker compose. Dans ce fichier nous declarons un service `sweb` qui utiliseras l'image du dossier sweb. Puis pour pouvoir le build il faudra ajouter la directive build comme ceci: 
``` yaml
version: '3'
services:

  sweb:
    build:
      context: ./sweb
      dockerfile: Dockerfile
    volumes:
      - ./sweb/www:/usr/share/nginx/html
```
La ligne version indique la version de docker compose qu'on utilise et la ligne services indique le debut de la section ou on va déclarer nos services.
La section build permet de construire notre service. context indique ou se trouve le repertoire avec les fichiers sources nécessaire à la construction du service et dockerfile indique le nom du fichier Dockerfile
La section volumes indique ou le contenue du repertoire ."/sweb/www" doit être monté dans le conteneur. Ici, il va être monté dans le répertoire "/usr/share/nginx/html"

## Step 3

## Step 4
Dans cette partie nous allons mettre en place le reverse-proxy. Pour cela, nosu allons utilisé Traefik

``` yaml
version: '3'
services:

  sweb:
    build:
      context: ./sweb
      dockerfile: Dockerfile
    volumes:
      - ./sweb/www:/usr/share/nginx/html
    deploy:
      replicas: 1
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.sweb.rule=Host(`localhost`)"

  api:
    build:
      context: ./api
      dockerfile: Dockerfile
    deploy:
      replicas: 1
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.api.rule=Host(`localhost`)"
      - "traefik.http.services.api.loadbalancer.server.port=7000"
      - "traefik.http.routers.api.rule=PathPrefix(`/api`)"
       # Stripper
      - "traefik.http.routers.api.middlewares=api-strip"
      - "traefik.http.middlewares.api-strip.stripprefix.prefixes=/api"

  reverse-proxy:
    image: traefik:v2.10
    command:
     - "--api.insecure=true"
     - "--providers.docker"
     - "--entrypoints.web.address=:80"
    ports:
      - "80:80"
      - "8080:8080"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```
``` yaml
sweb:
    deploy:
      replicas: 1
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.sweb.rule=Host(`localhost`)"
```
Cette partie à été rajouté en plus de la configuration de base. La section deploy replicas indique combien de service on veut deployer

La sections labels est utilisé par traefik. La premiere ligne indique qu'on active traefik pour le service sweb et la seconde ligne définit une règle qui indique que le service doit être accessible via localhost.

## Step 5

## Step 6
