#!/bin/bash

sudo systemctl stop app.service

#removing previous build ROOT folder
sudo rm -rf /home/ec2-user/*.jar

