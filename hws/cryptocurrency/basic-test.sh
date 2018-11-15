#!/bin/bash

./cryptomoney.sh name
./cryptomoney.sh genesis
./cryptomoney.sh generate alice.wallet.txt
export alice=`./cryptomoney.sh address alice.wallet.txt`
echo alice.wallet.txt wallet signature: $alice
./cryptomoney.sh fund $alice 100 01-alice-funding.txt
./cryptomoney.sh generate bob.wallet.txt
export bob=`./cryptomoney.sh address bob.wallet.txt`
echo bob.wallet.txt wallet signature: $bob
./cryptomoney.sh fund $bob 100 02-bob-funding.txt
./cryptomoney.sh transfer alice.wallet.txt $bob 12.5 03-alice-to-bob.txt
./cryptomoney.sh transfer bob.wallet.txt $alice 2.5 04-bob-to-alice.txt
./cryptomoney.sh verify alice.wallet.txt 01-alice-funding.txt
./cryptomoney.sh verify bob.wallet.txt 02-bob-funding.txt
./cryptomoney.sh verify alice.wallet.txt 03-alice-to-bob.txt
./cryptomoney.sh verify bob.wallet.txt 04-bob-to-alice.txt
cat ledger.txt
./cryptomoney.sh balance $alice
./cryptomoney.sh balance $bob
./cryptomoney.sh sign
./cryptomoney.sh validate
