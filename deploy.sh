#!/bin/bash
set -e
cd ~/TaskManagementApp/task-client/
ng build
rm -rf ~/TaskManagementApp/src/main/resources/static/*
cp -r ~/TaskManagementApp/task-client/dist/task-client/browser/* ~/TaskManagementApp/src/main/resources/static/
echo "Copied. Restart Spring."
