import java.util.Random;

public class RSA {
    private long p, q, n, phi, e, d;

    public RSA(int bitLength) {
        p = generatePrime(bitLength);
        q = generatePrime(bitLength);
        n = p * q;
        phi = (p - 1) * (q - 1);
        e = generateE(phi);
        d = modInverse(e, phi);
    }

    private long generatePrime(int bitLength) {
        Random random = new Random();
        while(true) {
            long candidate = (long) (random.nextInt((int) Math.pow(2, bitLength - 1)) + Math.pow(2, bitLength - 1));
            if(isPrime(candidate)) 
                return candidate;
        }
    }

    private boolean isPrime(long num) {
        if(num < 2) return false;
        for(int i=2;i<=Math.sqrt(num);i++) {
            if(num % i == 0) return false;
        }
        return true;
    }

    private long gcd(long a, long b) {
        while(b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long generateE(long phi) {
        Random random = new Random();
        long e;
        do {
            e = random.nextInt((int) (phi - 2)) + 2; // e < phi but should be > 2
        } while(gcd(e, phi) != 1);
        return e;
    }

    private long modInverse(long e, long phi) {
        long[] result = extendedGCD(e, phi);
        long d = result[1];
        if (d < 0) d += phi;
        return d;
    }

    private long[] extendedGCD(long a, long b) {
        if (b == 0) return new long[]{a, 1, 0};
        long[] result = extendedGCD(b, a % b);
        long x = result[2];
        long y = result[1] - (a / b) * result[2];
        return new long[]{result[0], x, y};
    }

    private long modPow(long base, long exponent, long mod) { // (base ^ exponent) % mod 
        long result = 1;
        base = base % mod;
        while(exponent > 0) {
            if(exponent % 2 == 1) {
                result = (result * base) % mod;
            }
            exponent = exponent >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    public long encrypt(long message) {
        return modPow(message, e, n);
    }

    public long decrypt(long ciphertext) {
        return modPow(ciphertext, d, n);
    }

    public static void main(String[] args) {
        int bitLength = 10;
        RSA rsa = new RSA(bitLength);

        long message = 123;
        System.out.println("Original message: " + message);

        long encryptedMessage = rsa.encrypt(message);
        System.out.println("Encrypted message: " + encryptedMessage);

        long decryptedMessage = rsa.decrypt(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
