# ICS Cryptocurrency assignment

import hashlib
import binascii
import rsa

# gets the hash of a file; from https://stackoverflow.com/a/44873382
def hashFile(filename):
    h = hashlib.sha256()
    with open(filename, 'rb', buffering=0) as f:
        for b in iter(lambda : f.read(128*1024), b''):
            h.update(b)
    return h.hexdigest()

# given an array of bytes, return a hex reprenstation of it
def bytesToString(data):
    return binascii.hexlify(data)

# given a hex reprensetation, convert it to an array of bytes
def stringToBytes(hexstr):
    return binascii.a2b_hex(hexstr)

# Load the wallet keys from a filename
def loadWallet(filename):
    with open(filename, mode='rb') as file:
        keydata = file.read()
    privkey = rsa.PrivateKey.load_pkcs1(keydata)
    pubkey = rsa.PublicKey.load_pkcs1(keydata)
    return pubkey, privkey

# save the wallet to a file
def saveWallet(pubkey, privkey, filename):
    # Save the keys to a key format (outputs bytes)
    pubkeyBytes = pubkey.save_pkcs1(format='PEM')
    privkeyBytes = privkey.save_pkcs1(format='PEM')
    # Convert those bytes to strings to write to a file (gibberish, but a string...)
    pubkeyString = pubkeyBytes.decode('ascii')
    privkeyString = privkeyBytes.decode('ascii')
    # Write both keys to the wallet file
    with open(filename, 'w') as file:
        file.write(pubkeyString)
        file.write(privkeyString)
    return


# some code snippets for how to use the rsa module
#
# ----------------------------------------
# signing a transaction statement: let:
#
# - 'keystring' be the contents of the saved key file
# - 'message' be what we are verifying: the first four lines of the transaction statement
#
# privkey = rsa.PrivateKey.load_pkcs1(keystring)
# sig = rsa.sign(bytes(message.encode('ascii')), privkey, 'SHA-256')
#
# You can write the signature to the file as `sig.hex()`
#
# ----------------------------------------
# verifying a signature: let:
#
# - 'keystring' be the contents of the saved key file
# - 'sig' be the signature in the transaction statement, which is a series of hex characters
# - 'message' be what we are verifying: the first four lines of the transaction statement (note that this code takes the hash for you)
#
# pubkey = rsa.PublicKey.load_pkcs1(keystring)
# rsa.verify(bytes(message.encode('ascii')), bytes.fromhex(sig), pubkey)