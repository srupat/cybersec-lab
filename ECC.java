// Point class to represent a point on the elliptic curve
class Point {
    long x;
    long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class ECC {
    private long a; // Coefficient a in the elliptic curve equation
    private long p; // Prime number defining the field

    public ECC(long a, long p) {
        this.a = a;
        this.p = p;
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
        Point negQ = new Point(Q.x, -Q.y + p);
        return pointAdd(P, negQ);
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

    private Point pointMultiply(Point P, long k) {
        Point result = new Point(-1, -1); // Identity element (point at infinity)

        for (long i = 0; i < k; i++) {
            result = pointAdd(result, P);
        }
        
        return result;
    }

    public static void main(String[] args) {
        long a = 2; // Example parameter for the elliptic curve
        long p = 17; // Example prime for the field
        ECC ecc = new ECC(a, p);

        Point P = new Point(5, 1); // Example point on the curve
        long k = 3; // Scalar to multiply

        Point result = ecc.pointMultiply(P, k);
        System.out.println("Result of point multiplication: (" + result.x + ", " + result.y + ")");
    }
}
