import java.util.Random;

class Point {
    long x, y;
    
    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class ECC {
    private long a, b, p; // y^2 = x^3 + ax + b mod p
    private Point G;      
    private long n;       
    private long k;       
    private Point Q;      
    
    public ECC(long a, long b, long p, Point G, long n) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.G = G;
        this.n = n;
        this.k = generatePrivateKey();
        this.Q = pointMultiply(G, k); // Q = k * G
    }

    private long generatePrivateKey() {
        Random rand = new Random();
        return rand.nextLong() % (n - 1) + 1; // Private key - range [1, n-1]
    }

    public Point getPublicKey() {
        return Q;
    }

    public Point encrypt(Point M, Point recipientPublicKey) {
        long k = generatePrivateKey(); 
        Point C1 = pointMultiply(G, k); // C1 = k * G
        Point C2 = pointAdd(M, pointMultiply(recipientPublicKey, k)); // C2 = M + k * Q
        return new Point(C1.x, C2.x); 
    }

    public Point decrypt(Point C1, Point C2) {
        Point M = pointSubtract(C2, pointMultiply(C1, k)); // M = C2 - k * C1
        return M;
    }

    
    private Point pointAdd(Point P, Point Q) {
        if (P.x == -1 && P.y == -1) return Q;
        if (Q.x == -1 && Q.y == -1) return P;
        
        long lambda;
        if (P.x == Q.x && P.y == Q.y) {
            lambda = (3 * P.x * P.x + a) * modInverse(2 * P.y, p) % p;
        } else {
            lambda = (Q.y - P.y) * modInverse(Q.x - P.x, p) % p;
        }

        long x3 = (lambda * lambda - P.x - Q.x) % p;
        long y3 = (lambda * (P.x - x3) - P.y) % p;

        return new Point((x3 + p) % p, (y3 + p) % p);
    }

    private Point pointSubtract(Point P, Point Q) {
        Point negQ = new Point(Q.x, -Q.y + p); // Negation of point Q
        return pointAdd(P, negQ);
    }

    // multiplication using double and add method
    private Point pointMultiply(Point P, long k) {
        Point result = new Point(-1, -1); 
        Point addend = P;

        while (k != 0) {
            if ((k & 1) == 1) {
                result = pointAdd(result, addend);
            }
            addend = pointAdd(addend, addend);
            k >>= 1;
        }
        return result;
    }

    private long modInverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1) return 0;

        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) x1 += m0;

        return x1;
    }

    public static void main(String[] args) {
        long a = 1, b = 1, p = 23;
        Point G = new Point(1, 3);
        long n = 19;

        ECC ecc = new ECC(a, b, p, G, n);

        Point M = new Point(10, 12);

        Point recipientPublicKey = ecc.getPublicKey();
        Point ciphertext = ecc.encrypt(M, recipientPublicKey);
        System.out.println("Ciphertext: (" + ciphertext.x + ", " + ciphertext.y + ")");

        Point decryptedMessage = ecc.decrypt(ciphertext, ciphertext);
        System.out.println("Decrypted Message: (" + decryptedMessage.x + ", " + decryptedMessage.y + ")");
    }
}
