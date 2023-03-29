#!/bin/bash

echo The name of this cryptocurrency is:
./cryptomoney.sh name
echo Creation of the genesis block
./cryptomoney.sh genesis
echo Creating a wallet for alice into alice.wallet.txt
./cryptomoney.sh generate alice.wallet.txt
export alice=`./cryptomoney.sh address alice.wallet.txt`
echo alice.wallet.txt wallet signature: $alice
echo funding alice wallet with 100
./cryptomoney.sh fund $alice 100 01-alice-funding.txt
echo Creating a wallet for bob into alice.wallet.txt
./cryptomoney.sh generate bob.wallet.txt
export bob=`./cryptomoney.sh address bob.wallet.txt`
echo bob.wallet.txt wallet signature: $bob
echo funding bob wallet with 100
./cryptomoney.sh fund $bob 100 02-bob-funding.txt
echo transfering 12 from alice to bob
./cryptomoney.sh transfer alice.wallet.txt $bob 12 03-alice-to-bob.txt
echo transfering 2 from bob to alice
./cryptomoney.sh transfer bob.wallet.txt $alice 2 04-bob-to-alice.txt
echo verifying the last four transactions
./cryptomoney.sh verify alice.wallet.txt 01-alice-funding.txt
./cryptomoney.sh verify bob.wallet.txt 02-bob-funding.txt
./cryptomoney.sh verify alice.wallet.txt 03-alice-to-bob.txt
./cryptomoney.sh verify bob.wallet.txt 04-bob-to-alice.txt
echo displaying the mempool
cat mempool.txt
echo checking the balance of both alice and bob
./cryptomoney.sh balance $alice
./cryptomoney.sh balance $bob
echo mining the block with prefix of 2
./cryptomoney.sh mine 2
sha256sum block_1.txt
echo validating the cryptocurrency chain
./cryptomoney.sh validate
