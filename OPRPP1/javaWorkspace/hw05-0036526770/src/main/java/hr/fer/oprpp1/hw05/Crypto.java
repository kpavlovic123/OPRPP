package hr.fer.oprpp1.hw05;

import static hr.fer.oprpp1.hw05.Util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

    public static void checksha(String filename) {
        System.out.printf("Please provide expected sha-256 digest for %s:\n", filename);
        Scanner scan = new Scanner(System.in);
        String expectedSha = scan.nextLine();
        scan.close();
        InputStream inputFileStream = null;
        MessageDigest sha = null;

        try {
            sha = MessageDigest.getInstance("SHA-256");
            inputFileStream = new FileInputStream(filename);
            byte[] b = new byte[1024];
            int bytesRead = inputFileStream.read(b);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++)
                    sha.update(b[i]);
                bytesRead = inputFileStream.read(b);
            }
            inputFileStream.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        byte[] hash = sha.digest();
        String actualSha = Util.bytetohex(hash);
        if (actualSha.equals(expectedSha)) {
            System.out.printf("Digesting completed. Digest of %s matches expected digest.\n", filename);
        } else {
            System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s",
                    filename, actualSha);
        }

    };

    public static void cypher(String mode, String filename, String fileOutputName) {
        boolean encrypt = mode.equals("encrypt") ? true : false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
        String keyText = scan.nextLine();
        System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
        String ivText = scan.nextLine();
        scan.close();
        SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
            InputStream inputFileStream = new FileInputStream(filename);
            OutputStream fileOutput = new FileOutputStream(fileOutputName);
            byte[] outputBytes = null;
            int bytesRead;
            byte[] b = new byte[4096];
            bytesRead = inputFileStream.read(b);
            while (bytesRead != -1) {
                int n = bytesRead/16;
                for(int i = 0;i<n;i++){
                outputBytes = cipher.update(b,i*16,16);
                fileOutput.write(outputBytes);
                }
                if(bytesRead%16!=0){
                    fileOutput.write(cipher.update(b,n*16,bytesRead%16));
                }
                bytesRead = inputFileStream.read(b);
                
            }
            inputFileStream.close();
            outputBytes = cipher.doFinal();
            fileOutput.write(outputBytes);
            fileOutput.close();

            System.out.printf("%s completed. Generated file %s based on file %s.\n",
                encrypt ? "Encryption" : "Decryption", fileOutputName, filename);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    };

    public static void main(String[] args) {
        switch (args[0]) {
            case "checksha":
                checksha(args[1]);
                break;
            default:
                cypher(args[0], args[1], args[2]);
                break;
        }

    }
}
