import java.nio.charset.StandardCharsets;

public class SHA1 {

    private static final int[] H = {
            0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0
    };

    private static int leftRotate(int value, int bits) {
        return (value << bits) | (value >>> (32 - bits));
    }

    private static byte[] padMessage(byte[] message) {
        int originalLength = message.length;
        long bitLength = (long) originalLength * 8;

        int paddingLength = (56 - (originalLength + 1) % 64) % 64;
        byte[] paddedMessage = new byte[originalLength + paddingLength + 9];

        System.arraycopy(message, 0, paddedMessage, 0, originalLength);
        paddedMessage[originalLength] = (byte) 0x80;

        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedMessage.length - 1 - i] = (byte) (bitLength >>> (8 * i));
        }

        return paddedMessage;
    }

    private static int[] processChunk(byte[] chunk, int[] hash) {
        int[] w = new int[80];

        for (int i = 0; i < 16; i++) {
            w[i] = ((chunk[i * 4] & 0xFF) << 24) | ((chunk[i * 4 + 1] & 0xFF) << 16) |
                    ((chunk[i * 4 + 2] & 0xFF) << 8) | (chunk[i * 4 + 3] & 0xFF);
        }

        for (int i = 16; i < 80; i++) {
            w[i] = leftRotate(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);
        }

        int a = hash[0];
        int b = hash[1];
        int c = hash[2];
        int d = hash[3];
        int e = hash[4];

        for (int i = 0; i < 80; i++) {
            int f, k;
            if (i < 20) {
                f = (b & c) | (~b & d);
                k = 0x5A827999;
            } else if (i < 40) {
                f = b ^ c ^ d;
                k = 0x6ED9EBA1;
            } else if (i < 60) {
                f = (b & c) | (b & d) | (c & d);
                k = 0x8F1BBCDC;
            } else {
                f = b ^ c ^ d;
                k = 0xCA62C1D6;
            }

            int temp = leftRotate(a, 5) + f + e + k + w[i];
            e = d;
            d = c;
            c = leftRotate(b, 30);
            b = a;
            a = temp;
        }

        hash[0] += a;
        hash[1] += b;
        hash[2] += c;
        hash[3] += d;
        hash[4] += e;

        return hash;
    }

    public static String sha1(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] paddedMessage = padMessage(bytes);

        int[] hash = H.clone();

        for (int i = 0; i < paddedMessage.length / 64; i++) {
            byte[] chunk = new byte[64];
            System.arraycopy(paddedMessage, i * 64, chunk, 0, 64);
            hash = processChunk(chunk, hash);
        }

        StringBuilder result = new StringBuilder();
        for (int h : hash) {
            result.append(String.format("%08x", h));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String message = "My name is Srujan Patwardhan.";
        String hash = sha1(message);
        System.out.println("Message: " + message);
        System.out.println("SHA-1 Hash: " + hash);
    }
}
