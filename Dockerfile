FROM php:8.2-apache

WORKDIR /var/www/html

COPY / /var/www/html/

RUN apt-get update && apt-get install -y openjdk-11-jdk

RUN cd /var/www/html/src/web && mkdir uploads
RUN chmod -R 777 /var/www/html/*

EXPOSE 80

