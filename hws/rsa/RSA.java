import java.util.*;
import java.math.*;
import java.io.*;
import java.security.*;
import java.text.*;

/** Holds a RSA key, either public or private (or possibly both).  In
 * addition to *n*, which must be in all keys, one (or both) of *d*
 * and e* must be set.  Which one(s) will determine whether it's a
 * public key (if only *e* is set), a private key (if only *d* is
 * set), or a dual key (if both *d* and *e* are set). This class is
 * meant to hold the values, and has no methods itself.
 */
class RSAKey {

    /// The decryption key, only included in private keys.
    BigInteger d;

    /// The encryption key, only used in public keys.
    BigInteger e;

    /// The value *n*, which was computed by p*q during the key generation
    BigInteger n;

    /// The bit length of p and also q during the key generation; n is
    /// expected to be twice that value
    int bitLength;
}


/** Holds the necessary cipher text information.  This class is meant
 * to hold the values, and has no methods itself.
 */
class CipherText {

    /// The list of encrypted blocks in the cipher text
    ArrayList<BigInteger> encryptedBlocks;

    /// The length of the original plaintext message
    int fileLength;

    /// The length of each block, in characters
    int blockLength;

    /// Default constructor, it initializes an empty object.
    CipherText() {
        encryptedBlocks = new ArrayList<BigInteger>();
    }
}


/** The main RSA class.  This is the class that contains all of the
 * functionality, and there are five methods that need to be completed
 * for this assignment: {@link generateKeys()}, {@link encrypt()},
 * {@link decrypt()}, {@link sign()}, {@link checkSign()}, and {@link
 * crack()}.
 */
public class RSA {

    /** Whether to print verbose messages.  This is useful for
     * debugging, as it will never be set to true (via the -verbose
     * flag) during grading.
     */
    static boolean verbose = false;

    /** Whether the values of p and q are displayed during key
     * generation.  This is useful for debugging, and it *is*
     * something that we will test during grading.
     */
    static boolean showPandQ = false;

    /** The hash algorithm to be used.  While there are many
     * available, we'll use SHA-256 for this code.
     */
    final static String hashAlgorithm = "SHA-256";

    /** The random number generator
     */
    static Random random = new Random();


    /** Write a {@link RSAKey} key (public or private) to a file.
     *
     * This method is provided to enforce the format of the key files.
     * Key files are ASCII text files with exactly four lines:
     *
     * - The string "public" or "private"
     * - The bit length of the keys; this is expected to fit within a
     *   standard `int`
     * - Either *e* (if it's a public key) or *d* (if it's a private
     *   key); this is expected to require a BigInteger to store it
     * - *n*; this is expected to require a BigInteger to store it
     *
     * If the passed key is either public or private, then the method
     * will write only one key file.  If *both* d and e are set in the
     * passed in RSAKey, then the method will write both files (this
     * is typically only done after key generation).
     *
     * @param key The {@link RSAKey} to write to the file
     * @param keyName The base of the file name.  If the base is
     * 'foo', then this file will write foo-public.key and/or
     * foo-private.key, as appropriate.
     *
     * @throws Exception If the file handling code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    static void writeKeyToFile (RSAKey key, String keyName) throws Exception {
        if ( key.e != null ) { // it's a public key
            PrintStream output = new PrintStream(new File(keyName+"-public.key"));
            output.println("public\n" + key.bitLength + "\n" + key.e + "\n" + key.n);
            output.close();
        }
        if ( key.d != null ) { // it's a private key
            PrintStream output = new PrintStream(new File(keyName+"-private.key"));
            output.println("private\n" + key.bitLength + "\n" + key.d + "\n" + key.n);
            output.close();
        }
    }

    /** Read a {@link RSAKey} key (public or private) to a file and
     * return it.
     *
     * This method is provided to enforce the format of the key files.
     * Key files are ASCII text files with exactly four lines:
     *
     * - The string "public" or "private"
     * - The bit length of the keys; this is expected to fit within a
     *   standard `int`
     * - Either *e* (if it's a public key) or *d* (if it's a private
     *   key); this is expected to require a BigInteger to store it
     * - *n*; this is expected to require a BigInteger to store it
     *
     * The method will set either *d* or *e* in the returned RSAKey,
     * depending on whether the key read in is a public key (e will be
     * set) or a private key (d will be set).
     *
     * @param filename The key file name; it is assumed to be the
     * correct format, such as that written by {@link
     * writeKeyToFile()}, as there is no real error checking on the
     * file format.
     *
     * @return The RSAKey read in from the file.
     *
     * @throws Exception If the file handling code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    static RSAKey readKeyFromFile (String filename) throws Exception {
        Scanner filein = new Scanner(new File(filename));
        RSAKey key = new RSAKey();
        boolean isPublic = filein.next().equals("public");
        key.bitLength = filein.nextInt();
        if ( isPublic )
            key.e = new BigInteger(filein.next());
        else
            key.d = new BigInteger(filein.next());
        key.n = new BigInteger(filein.next());
        return key;
    }

    /** Read in cipher text from the provided file.
     *
     * This method is provided to enforce the correct format for
     * cipher text files.  Cipher text files are ASCII text files that
     * contain the following:
     *
     * - The first line will have two `int` values, separated by a
     *   single space: the original size of the plain text, and the
     *   number of characters in each block
     * - Each block will have a single number, one per line; this is
     *   expected to require a BigInteger to store it
     *
     * @param filename The file name of the cipher text to read in
     *
     * @return A {@link CipherText} object representing the read in
     * cipher text
     *
     * @throws Exception If the file handling code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    static CipherText readCipherTextFromFile (String filename) throws Exception {
        Scanner filein = new Scanner(new File(filename));
        CipherText c = new CipherText();
        c.fileLength = filein.nextInt();
        c.blockLength = filein.nextInt();
        while ( filein.hasNext() ) {
            String num = filein.next();
            if ( num != "" )
                c.encryptedBlocks.add(new BigInteger(num));
        }
        return c;
    }

    /** Write cipher text to a file.
     *
     * This method is provided to enforce the correct format for
     * cipher text files.  Cipher text files are ASCII text files that
     * contain the following:
     *
     * - The first line will have two `int` values, separated by a
     *   single space: the original size of the plain text, and the
     *   number of characters in each block
     * - Each block will have a single number, one per line; this is
     *   expected to require a BigInteger to store it
     *
     * @param filename The file name to write the cipher text to
     * @param cipherText The {@link CipherText} object that contains
     * all the data to be written to the file.
     *
     * @throws Exception If the file handling code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    static void writeCipherTextToFile (String filename, CipherText cipherText) throws Exception {
        PrintStream output = new PrintStream(new File(filename));
        output.println(cipherText.fileLength + " " + cipherText.blockLength);
        for ( BigInteger num: cipherText.encryptedBlocks )
            output.println(num);
        output.close();
    }

    /** Read in the contents of a file into a string.
     *
     * This file just reads in the entire file into a String.
     *
     * @param filename The file to read in
     *
     * @return A String containing the entire contents of the passed
     * file
     *
     * @throws Exception If the file handling code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    static String getFileContents (String filename) throws Exception {
        File file = new File(filename);
        long fileLength = file.length();
        FileInputStream fin = new FileInputStream(file);
        byte[] data = new byte[(int)fileLength];
        fin.read(data);
        fin.close();
        return new String(data);
    }

    /** Convert a hash to a more human-readable string
     *
     * The java.security.MessageDigest routines will return a hash as
     * a `byte` array.  This method will convert that byte array to
     * the more recognizable human-readable form: hexadecimal format.
     *
     * @param hash The `byte` array that is returned by the
     * computation of the hash (via the digest() routine in
     * java.security.MessageDigest)
     *
     * @return A String containing the printable hexadecimal
     * representation of the hash
     */
    static String convertHash (byte hash[]) {
        int hashSize = 0;
        try {
            hashSize = MessageDigest.getInstance(hashAlgorithm).getDigestLength() * 2;
        } catch (Exception e) {
            System.out.println ("Your Java installation does not support the '" + hashAlgorithm +
                                "' hashing method; change that in RSA.java to continue.");
            System.exit(1);
        }
        char chash[] = new char[hashSize];
        for ( int i = 0; i < hashSize/2; i++ ) {
            int hashValue = hash[i];
            if ( hashValue < 0 )
                hashValue += 256;
            if ( hashValue/16 < 10 )
                chash[2*i] = (char) ('0' + hashValue/16);
            else
                chash[2*i] = (char) ('a' + hashValue/16 - 10);
            if ( hashValue%16 < 10 )
                chash[2*i+1] = (char) ('0' + hashValue%16);
            else
                chash[2*i+1] = (char) ('a' + hashValue%16 - 10);
        }
        return new String(chash);
    }

    /** Converting a block to a BigInteger
     *
     * This method will convert a block of ASCII text into a
     * BigInteger; that BigInteger will be used as the plaintext for
     * the various routines in this code.  It must be able to be
     * completely reversed with the corresponding convertToASCII()
     * method.  Note that this method converts the ENTIRE passed block
     * to a BigInteger -- if the input text is to be split into
     * multiple blocks, then that is to be done BEFORE calling this
     * method.
     *
     * @param text The ASCII text to convert into a BigInteger.
     *
     * @return A BigInteger that represents the passed in text.
     */
    public static BigInteger convertFromASCII(String text) {
        BigInteger ret = BigInteger.ZERO, ff = new BigInteger("256");
        for ( int i = 0; i < text.length(); i++ ) {
            ret = ret.multiply(ff);
            ret = ret.add(new BigInteger(Integer.toString((int)text.charAt(i))));
        }
        return ret;
    }

    /** Converting a block to ASCII
     *
     * This method will convert a BigInteger block, used in the
     * encryption routines in this code, into the corresponding ASCII
     * text.  It must be able to be completely reversed with the
     * corresponding convertFromASCII() method.  Note that this method
     * converts the ENTIRE passed block from a BigInteger -- it is
     * assumed that the BigInteger represents the entire block.
     *
     * @param block The BigInteger block to convert into ASCII.
     *
     * @return A String that is the ASCII representation of the
     * BigInteger parameter.
     */
    public static String convertToASCII(BigInteger block) {
        BigInteger ff = new BigInteger("256");
        String ret = "";
        while ( block.compareTo(BigInteger.ZERO) == 1 ) {
            ret = (char) block.mod(ff).intValue() + ret;
            block = block.divide(ff);
        }
        return ret;
    }

    /** RSA key generation; requires completion.
     *
     * This method will generate (and return) a pair of keys based on
     * the parameters provided.  This method uses the java.util.Random
     * random number generator.
     *
     * @param bitLength The bit length of *p* and *q* used in the
     * generation routines; this means that *n* will be approximately
     * *2n* bits in length.
     * @return The keys generated.  Note that in the returned {@link
     * RSAKey}, *both* *d* and *e* are set, which means it contains
     * the information for both public and private keys.
     *
     * @throws Exception It is not expected that this method will
     * throw an exception, but this is included in case a future
     * implementation does choose to do so.
     */
    public static RSAKey generateKeys (int bitLength) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return null;
    }

    /** Performs RSA encryption; requires completion.
     *
     * This method will perform RSA encryption on the passed plain
     * text using the passed *public* key, and return the cipher text.
     *
     * Note that the returned {@link CipherText} object must have all
     * three fields set: bitLength, blockLength, *and*
     * encryptedBlocks.
     *
     * @param key The public key to encrypt the plain text with
     * @param plainText The text to encrypt
     *
     * @return A properly initialized CipherText object containing the
     * encrypted message.
     *
     * @throws Exception It is not expected that this method will
     * throw an exception, but this is included in case a future
     * implementation does choose to do so.
     */
    public static CipherText encrypt (RSAKey key, String plainText) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return null;
    }

    /** Performs RSA decryption; requires completion.
     *
     * This method will perform RSA decryption on the passed cipher
     * text using the passed *private* key, and return the plain text.
     *
     * @param key The private key to encrypt the plain text with
     * @param cipherText a {@link CipherText} object containing the
     * cipher text to decrypt
     *
     * @return A String containing the decrypted message.
     *
     * @throws Exception It is not expected that this method will
     * throw an exception, but this is included in case a future
     * implementation does choose to do so.
     */
    public static String decrypt (RSAKey key, CipherText cipherText) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return null;
    }

    /** Performs RSA key cracking; requires completion.
     *
     * This will take a *SIGNIFICANT* amount of time if used on any
     * reasonable bit lengths; using it on a bit length of 10 is
     * reasonable to test.
     *
     * @param key The public key to crack
     *
     * @return A RSAKey containing the cracked *full* key (both d and
     * e are set, as well as the other fields)
     *
     * @throws Exception It is not expected that this method will
     * throw an exception, but this is included in case a future
     * implementation does choose to do so.
     */
    public static RSAKey crack (RSAKey key) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return null;
    }

    /** Performs RSA message signing; requires completion.
     *
     * Note that the encrypt() method is likely expecting a public key,
     * so you will have to copy d over to e before passing it into
     * encrypt()
     *
     * Note that the particular hash algorithm to be used is set in
     * the {@link hashAlgorithm} variable, and is likely SHA-256.
     *
     * @param key The private key to sign the message with.
     * @param plainText the message to sign
     *
     * @return The cipher text containing the encrypted hash of the
     * message
     *
     * @throws Exception If the method digest code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    public static CipherText sign (RSAKey key, String plainText) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return null;
    }

    /** Performs RSA message signature checking; requires completion.
     *
     * Note that the decrypt() method is likely expecting a private
     * key, so you will have to copy e over to d before passing it
     * into encrypt()
     *
     * Note that the particular hash algorithm to be used is set in
     * the {@link hashAlgorithm} variable, and is likely SHA-256.
     *
     * @param key The public key to sign the message with.
     * @param plainText The message to check the signature of
     * @param signature The encrypted signature to check
     *
     * @return True of false, depending if the signatures match (or
     * not)
     *
     * @throws Exception If the method digest code within the method
     * throws an exception, this method will pass that method up the
     * throw stack.
     */
    public static boolean checkSign (RSAKey key, String plainText, CipherText signature) throws Exception {
        // your code here; a dummy return statement is put below to allow this to compile
        return false;
    }

    /** Parses the parameters and allows program execution.
     *
     * The `main()` method will parse the command line parameters, and
     * call the appropriate methods within this class.  You should not
     * have to modify this main() method at all.
     *
     * The command line parameters are specified at {@link cmdparam}.
     *
     * @param args The command-line arguments
     *
     * @throws Exception Many of the invoked methods will pass up an
     * exception thrown by their file handling routines (or the
     * message digest routines).  The main method will pass that up as
     * well (which means program termination).
     */
    public static void main (String[] args) throws Exception {
        String outputFileName = "output.txt", inputFileName = "input.txt", keyName = "default";

        for ( int i = 0; i < args.length; i++ ) {

            if ( args[i].equals("-verbose") )
                verbose = !verbose;

            else if ( args[i].equals("-output") )
                outputFileName = args[++i];

            else if ( args[i].equals("-input") )
                inputFileName = args[++i];

            else if ( args[i].equals("-key") )
                keyName = args[++i];

            else if ( args[i].equals("-showpandq") )
                showPandQ = true;

            else if ( args[i].equals("-keygen") ) {
                int bitLength = Integer.parseInt(args[++i]);
                RSAKey key = generateKeys(bitLength);
                writeKeyToFile (key,keyName);
            }

            else if ( args[i].equals("-encrypt") ) {
                RSAKey key = readKeyFromFile (keyName + "-public.key");
                String plainText = getFileContents(inputFileName);
                CipherText cipherText = encrypt(key, plainText);
                writeCipherTextToFile(outputFileName, cipherText);
            }

            else if ( args[i].equals("-decrypt") ) {
                RSAKey key = readKeyFromFile (keyName + "-private.key");
                CipherText cipherText = readCipherTextFromFile(inputFileName);
                String plainText = decrypt(key, cipherText);
                PrintWriter fileout = new PrintWriter(outputFileName);
                fileout.print(plainText);
                fileout.close();
            }

            else if ( args[i].equals("-sign") ) {
                RSAKey key = readKeyFromFile (keyName + "-private.key");
                String plainText = getFileContents(inputFileName);
                CipherText signature = sign(key,plainText);
                writeCipherTextToFile(inputFileName+".sign", signature);
            }

            else if ( args[i].equals("-checksign") ) {
                RSAKey key = readKeyFromFile (keyName + "-public.key");
                String plainText = getFileContents(inputFileName);
                CipherText signature = readCipherTextFromFile(inputFileName+".sign");
                boolean result = checkSign(key,plainText,signature);
                if ( !result )
                    System.out.println("Signatures do not match!");
            }

            else if ( args[i].equals("-crack") ) {
                RSAKey key = readKeyFromFile (keyName + "-public.key");
                RSAKey crack = crack(key);
                writeKeyToFile (crack,keyName+"-cracked");
            }

            else if ( args[i].equals("-seed") )
                random = new Random(Integer.parseInt(args[++i]));

            else {
                System.out.println("Unknown parameter: '" + args[i] + "', exiting.");
                System.exit(1);
            }

        }
    }
}
