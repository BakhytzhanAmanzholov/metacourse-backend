#!/usr/bin/env bash

mvn clean package

pgrep java | xargs kill -9
java -jar metacourse--
