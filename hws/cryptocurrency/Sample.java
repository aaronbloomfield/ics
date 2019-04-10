import java.security.*;
import java.io.*;
import java.nio.file.*;
import java.security.spec.*;
import java.util.*;

public class Sample {

    // this converts an array of bytes into a hexadecimal number in
    // text format
    static String getHexString(byte[] b) {
	String result = "";
	for (int i = 0; i < b.length; i++) {
	    int val = b[i];
	    if ( val < 0 )
		val += 256;
	    if ( val <= 0xf )
		result += "0";
	    result += Integer.toString(val, 16);
	}
	return result;
    }

    // this converts a hexadecimal number in text format into an array
    // of bytes
    static byte[] getByteArray(String hexstring) {
	byte[] ret = new byte[hexstring.length()/2];
	for (int i = 0; i < hexstring.length(); i += 2) {
	    String hex = hexstring.substring(i,i+2);
	    if ( hex.equals("") )
		continue;
	    ret[i/2] = (byte) Integer.parseInt(hex,16);
	}
	return ret;
    }
    
    // This will write the public/private key pair to a file in text
    // format.  It is adapted from the code from
    // https://snipplr.com/view/18368/saveload--private-and-public-key-tofrom-a-file/
    static void SaveKeyPair(String filename, KeyPair keyPair) throws Exception {
	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
	PrintWriter fout = new PrintWriter(new FileOutputStream(filename));
	fout.println(getHexString(x509EncodedKeySpec.getEncoded()));
	fout.println(getHexString(pkcs8EncodedKeySpec.getEncoded()));
	fout.close();
    }

    // This will read a public/private key pair from a file.  It is
    // adapted from the code from
    // https://snipplr.com/view/18368/saveload--private-and-public-key-tofrom-a-file/
    static KeyPair LoadKeyPair(String filename) throws Exception {
	// Read wallet
	Scanner sin = new Scanner(new File(filename));
	byte[] encodedPublicKey = getByteArray(sin.next());
	byte[] encodedPrivateKey = getByteArray(sin.next());
	sin.close();
	// Generate KeyPair.
	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
	PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
	PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
	PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
	return new KeyPair(publicKey, privateKey);
    }

    // This will get the SHA-256 hash of a file, and is the same as
    // calling the `sha256sum` command line program
    static String getHashOfFile(String filename) throws Exception {
	byte[] filebytes = Files.readAllBytes(Paths.get(filename));
	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	byte[] encodedHash = digest.digest(filebytes);
	return getHexString(encodedHash);
    }

}
