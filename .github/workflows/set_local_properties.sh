#!/bin/bash

touch local.properties
echo "KEY_STORE=\\"$1\\"" >> local.properties
echo "KEY_STORE_ALIAS=\\"$2\\"" >> local.properties
echo "KEY_STORE_PASS=$3" >> local.properties
echo "TEST_STRING=$4" >> local.properties