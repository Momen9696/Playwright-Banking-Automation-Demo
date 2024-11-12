package utilis;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtility {
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final String secret = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDD7aL4q2saBFeuUaMhvbCtTgBf0niQr5yqy";

    // Derive AES key from password
    public static SecretKey getAESKeyFromTokenPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    // Generate 16-byte random nonce
    public static byte[] getRandomNonce(int length) {
        byte[] nonce = new byte[length];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // Encryption method
    public static String encrypt(String plaintext) throws Exception {
        byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
        byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);

        SecretKey aesKeyFromPassword = getAESKeyFromTokenPassword(secret.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(plaintext.getBytes(UTF_8));

        // Concatenate IV + Salt + CipherText
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + salt.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(salt);
        byteBuffer.put(cipherText);

        // Encode to Base64
        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    // Decryption method
    public static String decrypt(String cText) throws Exception {
        byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

        ByteBuffer bb = ByteBuffer.wrap(decode);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        SecretKey aesKeyFromPassword = getAESKeyFromTokenPassword(secret.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, UTF_8);
    }

    // Main method to test encryption and decryption
    public static void main(String[] args) throws Exception {
        String password = "Add a password before starting execution";

        // Encrypt the password
        String encryptedPassword = encrypt(password);
        System.out.println("Encrypted Password: " + encryptedPassword);

        // Decrypt the password
        String decryptedPassword = decrypt(encryptedPassword);
        System.out.println("Decrypted Password: " + decryptedPassword);
    }
}
