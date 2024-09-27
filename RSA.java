public class RSA {
    public static void main(String[] args) {
        int p = 3;
        int q = 11;
        int n = p * q;
        int phi = (p - 1) * (q - 1);
        int e = 3;
        int d = 7;
        int plainText = 4;
        int cipherText = (int) (Math.pow(plainText, e) % n);
        int decryptedText = (int) (Math.pow(cipherText, d) % n);
        System.out.println("Cipher Text: " + cipherText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
