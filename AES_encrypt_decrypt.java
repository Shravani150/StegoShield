import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

public class AES_encrypt_decrypt {

    public static void main(String[] args) throws Exception {

        // Generate AES-256 key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        // Generate 12-byte nonce
        byte[] nonce = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);

        // GCM parameters
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(128, nonce);

        // Encryption
        Cipher encryptCipher =
                Cipher.getInstance("AES/GCM/NoPadding");

        encryptCipher.init(
                Cipher.ENCRYPT_MODE,
                key,
                gcmParameterSpec
        );

        String message = "Hello StegoShield";

        byte[] encryptedData =
                encryptCipher.doFinal(message.getBytes());

        // Tampering experiment
        //encryptedData[0] = (byte) (encryptedData[0] ^ 1);

        // Decryption
        Cipher decryptCipher =
                Cipher.getInstance("AES/GCM/NoPadding");

        decryptCipher.init(
                Cipher.DECRYPT_MODE,
                key,
                gcmParameterSpec
        );

        byte[] decryptedData =
                decryptCipher.doFinal(encryptedData);

        String decryptedMessage =
                new String(decryptedData);

        System.out.println("Original: " + message);
        System.out.println("Decrypted: " + decryptedMessage);
    }
}