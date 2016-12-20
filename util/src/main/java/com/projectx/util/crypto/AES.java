package com.projectx.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * AES crypto helper class
 * 
 * http://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
 * 
 * @author Assaf Moldavsky
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {

	private String encryptionKey;
	
	private String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
    private String encryptionKeySpecAlgorithm = "AES";

	/**
	 * 
	 * @param encryptionKey	The key is assumed to be AES spec
	 */
    public AES( String encryptionKey ) {
    	
        this.encryptionKey = encryptionKey;
        
    }

    public String encrypt( String plainText ) throws Exception {
    	
        Cipher cipher = getCipher( Cipher.ENCRYPT_MODE );
        byte[] encryptedBytes = cipher.doFinal( plainText.getBytes() );

        return Base64.encodeBase64String( encryptedBytes );
        
    }

    public String decrypt( String encrypted ) throws Exception {
    	
        Cipher cipher = getCipher( Cipher.DECRYPT_MODE );
        byte[] plainBytes = cipher.doFinal( Base64.decodeBase64(encrypted) );

        return new String( plainBytes );
        
    }

    private Cipher getCipher( int cipherMode ) throws Exception {
    	
        
        SecretKeySpec keySpecification = new SecretKeySpec(
                encryptionKey.getBytes("UTF-8"), 
                encryptionKeySpecAlgorithm
        );
        Cipher cipher = Cipher.getInstance( encryptionAlgorithm );
        cipher.init( cipherMode, keySpecification );
        
        return cipher;
        
    }
    
    public static String generateEncryptionKey( String password, short keySize ) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException {
    	
    	// generate salt
    	SecureRandom random = new SecureRandom();
    	byte bytes[] = new byte[ 20 ];
    	random.nextBytes( bytes );
    	String salt = new String( bytes );
    	byte[] saltBytes = salt.getBytes( "UTF-8" );
    	
    	// generate the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA1" );
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), 
                saltBytes, 
                65536, 
                keySize
        );
        
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
    	
    	return new String( secret.getEncoded(), "UTF-8" );
    	
    }
	
}
