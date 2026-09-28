import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESGCMEncryption {

    public static void main(String[] args) throws Exception {

        // 1. Generate a random 256-bit AES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        // 2. Generate a random 12-byte nonce (IV)
        byte[] nonce = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);

        // 3. Create AES-GCM Cipher
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // 4. Create GCM parameters using the nonce
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(128, nonce);

        // 5. Initialize cipher with AES key + GCM parameters
        cipher.init(
                Cipher.ENCRYPT_MODE,
                key,
                gcmParameterSpec
        );

        // 6. Plaintext
        String message = "Meet me at 5 PM";

        // 7. Encrypt
        byte[] encryptedData =
                cipher.doFinal(message.getBytes());

        // 8. Convert encrypted data to Base64 for easy display/storage
        String ciphertext =
                Base64.getEncoder().encodeToString(encryptedData);

        String encodedKey =
                Base64.getEncoder().encodeToString(key.getEncoded());

        String encodedNonce =
                Base64.getEncoder().encodeToString(nonce);

        // Display
        System.out.println("AES Key: " + encodedKey);
        System.out.println("Nonce: " + encodedNonce);
        System.out.println("Ciphertext + Authentication Tag: " + ciphertext);
    }
}