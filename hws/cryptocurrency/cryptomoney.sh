#!/bin/bash

# the first command-line parameter is in $1, the second in $2, etc.

case "$1" in

    name) echo "AaronDollar(TM)"
	  # additional parameters provided: (none)
	  ;;

    genesis) java CMoney genesis block_0.txt
	     # additional parameters provided: (none)
             ;;

    generate) java CMoney generate $2
	      # additional parameters provided: the wallet file name
              ;;

    address) java CMoney address $2
	     # additional parameters provided: the file name of the wallet
	     ;;

    fund) java CMoney fund $2 $3 $4
	  # additional parameters provided: destination wallet
	  # address, the amount, and the transaction file name
          ;;

    transfer) java CMoney transfer $2 $3 $4 $5
	      # additional parameters provided: source wallet file
	      # name, destination address, amount, and the transaction
	      # file name
	      ;;

    balance) java CMoney balance $2
	     # additional parameters provided: wallet address
	     ;;

    verify) java CMoney verify $2 $3
	    # additional parameters provided: wallet file name,
	    # transaction file name
	    ;;

    createblock) java CMoney createblock
		 # additional parameters provided: (none)
		 ;;
    
    validate) java CMoney validate
	      # additional parameters provided: (none)
	      ;;

    *) echo Unknown function: $1
       ;;

esac
