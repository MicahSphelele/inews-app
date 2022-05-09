#!/bin/bash

touch app.properties

echo -e "KEY_STORE=\\"$1\\"" >> app.properties
echo -e "KEY_STORE_ALIAS=\\"$2\\"" >> app.properties
echo -e "KEY_STORE_PASS=\\"$3\\"" >> app.properties
echo -e "TEST_STRING=\\"$4\\"" >> app.properties
cat app.properties