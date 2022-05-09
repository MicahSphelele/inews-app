#!/bin/bash

touch local.properties

echo -e "KEY_STORE=\\"$1\\"" >> local.properties
echo -e "KEY_STORE_ALIAS=\\"$2\\"" >> local.properties
echo -e"KEY_STORE_PASS=\\"$3\\"" >> local.properties
echo -e "TEST_STRING=\\"$4\\"" >> local.properties
cat local.properties