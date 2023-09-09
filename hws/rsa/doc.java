/** @mainpage Main Page
 *
 * This documentation describes three classes provided in the skeleton
 * code for the <a
 * href="https://aaronbloomfield.github.io/ics/hws/rsa.html">RSA
 * homework</a>.
 *
 * - {@link CipherText}: a class to hold the cipher text for any RSA
 *   encrypted data
 * - {@link RSAKey}: a class to hold a public or private (or both) key
 * - {@link RSA}: the main class that implementes RSA.  There are six
 *   methods within that need to be completed for this assignment:
 *   {@link RSA.generateKeys()}, {@link RSA.encrypt()}, {@link
 *   RSA.decrypt()}, {@link RSA.sign()}, {@link RSA.checkSign()}, and
 *   {@link RSA.crack()}.
 *
 * Lastly, the command line parameters are specified at {@link
 * cmdparam}.
 */

/** @page cmdparam Command Line Parameters
 *
 * The {@link RSA.main()} methods accepts the following command-line
 * parameters:
 * 
 * - `-keygen <bitsize>`: this will generate RSA public/private keys of
 *   the specified bit size. The base name for the file to store the keys
 *   in will be specified by the `-key` parameter; see details below as
 *   to the file names and file format. You can divide by 3.321928, which
 *   is roughly log(10)/log(2), to get the number of decimal digits. The
 *   number of bits provided is for *p* (as well as *q*); *n* is roughly
 *   twice as many bits.  Note that *d* and *e* need to be roughly the
 *   same bit size (i.e. you can't have *e* be about the size of *p* and
 *   *q*, and *d* be about the size of *n*).  See the -key parameter,
 *   below, for how to write the key to a file.
 * - `-output <filename>`: this specifies the file name to save the
 *   results in. It can be the output from an encryption or
 *   decryption. The key file format is described in the {@link RSA.writeKeyToFile()}
 *   method.  A encrypted message file will
 *   follow the format described in the {@link
 *   RSA.writeCipherTextToFile()} method. If not specified, it should
 *   default to "output.txt".
 * - `-input <filename>`: the input file to be used to encrypt, decrypt,
 *   sign, check the sign of, or crack. This input file is specifically
 *   the plaintext, cipher text, or the signature, depending on what
 *   function is being called.  Note that the key would be specified by the `-key`
 *   parameter.  If not specified, it should default to "input.txt".
 * - `-showpandq`: this flag will cause the key generation to print out
 *   (to the screen only!) the values for the prime numbers *p* and *q*
 *   during the key generation phase - this is done for debugging
 *   purposes, and so we can check your code.
 * - `-key <keyfile>`: the key file.  For encryption, this is the public
 *   key file; for decryption, this is the private key file; for key
 *   generation, the file name to write the keys to; for cracking a
 *   message, this specifies the public key file. Note that this only
 *   specifies the key prefix name (such as 'alice' or 'bob') - to get
 *   the full key name, `-private.key`, `-public.key`, or
 *   `-cracked-private.key` is appended to the name.  If not specified,
 *   it should default to "key".
 * - `-encrypt`: this will use the public key (specified by the `-key`
 *   parameter) and encrypt the plaintext file (specified by the `-input`
 *   parameter), writing the ciphertext to another file (specified by the
 *   `-output` parameter).
 * - `-decrypt`: this will use the private key (specified by the `-key`
 *   parameter) and encrypt the ciphertext file (specified by the
 *   `-input` parameter), writing the plaintext to another file
 *   (specified by the `-output` parameter).
 * - `-verbose`: this flag need not do anything, but is used for
 *   debugging: a normal execution should not output anything (unless the
 *   signature doesn't match with `-checksign`); with this option, you
 *   can output all the status and debugging information that you would
 *   like.
 * - `-crack`: this will take the public key (specified by the `-key`
 *   parameter) and proceed to crack the RSA encryption by factoring *n*
 *   into *p* and *q*.  The output key (*d*,*n*) is written to
 *   \<foo\>-cracked-private.key, where \<foo\> is specified by the
 *   `-key` parameter.
 * - `-sign`: this will sign a message; given the message (specified by
 *   `-input`) and the private key (specified by `-key`), it will write
 *   the RSA‐encrypted MD5 hash to the output file. We assume it writes
 *   the sign to a file called \<filename\>.sign, where \<filename\> is
 *   the name given to the `-input` parameter - this can overwrite the
 *   existing \<filename\>.sign, if it exists.  A signature is just an
 *   encryption of the MD5 hash.  See the `java.security.MessageDigest`
 *   class for easy computation of MD5 hashes.  Also see the supplied
 *   Java code, below.
 * - `-checksign`: this will check a signed message; given the message
 *   (specified by `-input`) and the public key (specified by `-key`), it
 *   will verify the RSA encrypted MD5 hash to the signature
 *   file. Similar to the `-sign` parameter, the key is assumed to be in
 *   the \<filename\>.sign file.  This will print "signatures do not
 *   match" (or similar) ONLY if the signatures do not match; if they do
 *   match, then nothing is printed.
 */
