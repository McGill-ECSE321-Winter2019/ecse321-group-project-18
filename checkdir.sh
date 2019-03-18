#!/bin/sh

echo "check the JAR directory"
ls -lR Backend/build/


echo "Deploy to production"
cd AcademicManager-Web 
npm install
npm run build 

