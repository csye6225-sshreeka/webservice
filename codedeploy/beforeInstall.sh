#!/bin/bash

# Stoping tomcat
sudo systemctl stop app.service
rm -rf /home/ec2-user/webapp-target/*.jar
