package com.silvercare.util;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import java.util.Arrays;

public class PasswordUtil {

    private static Integer SALT_LENGTH;
    private static String HASH_ALGORITHM;
    
    // statically load configs into PasswordUtil private static properties
    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = Db.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new Exception("Unable to find config.properties file.");
            }

            Properties prop = new Properties();
            prop.load(input);

            SALT_LENGTH = Integer.parseInt(prop.getProperty("password_util.salt_length"));
            HASH_ALGORITHM = prop.getProperty("password_util.hash_algorithm");
            
            if(SALT_LENGTH == null || HASH_ALGORITHM == null) {
            	throw new Exception("Mssing required properties in config.properties.");
            }

        } catch (Exception e) {
            System.out.println("Error loading DB config: " + e.getMessage());
        }
    }

    // hashPassword - to be used by UserManager in registering user or changing password
    public static String hashPassword(String plainPassword) {
        byte[] salt = generateSalt();
        byte[] hash = hash(plainPassword, salt);
        byte[] saltAndHash = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
        System.arraycopy(hash, 0, saltAndHash, salt.length, hash.length);
        return Base64.getEncoder().encodeToString(saltAndHash);
    }

    // verifyPassword - to be used by UserManager in verifying password
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        byte[] saltAndHash = Base64.getDecoder().decode(storedHash);
        byte[] salt = Arrays.copyOfRange(saltAndHash, 0, SALT_LENGTH);
        byte[] hash = Arrays.copyOfRange(saltAndHash, SALT_LENGTH, saltAndHash.length);
        byte[] inputHash = hash(plainPassword, salt);
        return Arrays.equals(hash, inputHash);
    }

    private static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return salt;
    }

    private static byte[] hash(String password, byte[] salt) {
    	try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
        return md.digest(password.getBytes());
    	} catch (NoSuchAlgorithmException e) {
    		System.out.print("Error on password hashing: " + e.getMessage());
    	}
    	return null;
    }
}