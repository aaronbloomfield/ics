rem setup: create the message1.txt and message2.txt files
del -f message[12].txt
echo "Two things are infinite: the universe and human stupidity;" > message1.txt
echo "and I'm not sure about the the universe." >> message1.txt
echo "by Albert Einstein" >> message1.txt
echo "The quick brown fox jumped over the lazy dog." > message2.txt
rem 1: create keys alice-public.key and alice-private.key
./rsa.bat -key alice -keygen 200
rem 2: create keys bob-public.key and bob-private.key
./rsa.bat -key bob -keygen 200
rem 3: alice is going to encrypt a message for bob
./rsa.bat -key bob -input message1.txt -output encrypted1.txt -encrypt
rem 4: bob will decrypt the message
./rsa.bat -key bob -input encrypted1.txt -output message1b.txt -decrypt
rem 5: are they the same?
comp message1.txt message1b.txt
rem 6: bob now sends a message to alice
./rsa.bat -key alice -input message2.txt -output encrypted2.txt -encrypt
rem 7: alice will decrypt the message
./rsa.bat -key alice -input encrypted2.txt -output message2b.txt -decrypt
rem 8: are they the same?
comp message2.txt message2b.txt
rem 9: alice signs her message1.txt
./rsa.bat -key alice -input message1.txt -sign
rem 10: bob checks that sign; they should match
./rsa.bat -key alice -input message1.txt -checksign
rem 11: modify message1.txt
move message1.txt message1.txt.bak
echo hi >> message1.txt
rem 12: bob checks that sign; they should NOT match
./rsa.bat -key alice -input message1.txt -checksign
rem 13: restore message1.txt
move message1.txt.bak message1.txt
./rsa.bat -key alice -input message1.txt -checksign
rem 14: charlie generates an easy-to-crack key
./rsa.bat -key charlie -keygen 10
rem eve tries to crack alice's key
./rsa.bat -key charlie -crack
rem 15: is the cracked key the same as the original key?
comp charlie-cracked-private.key charlie-private.key
rem 16: clean up files (commented out by default)
rem del alice*.key bob*.key charlie*.key message*.sign message?b.txt encrypted?.txt message?.txt
