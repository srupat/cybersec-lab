public class SAES {
    static String[][] sbox = { { "1001", "0100", "1010", "1011" },
            { "1101", "0001", "1000", "0101" },
            { "0110", "0010", "0000", "0011" },
            { "1100", "1110", "1111", "0111" } };

    static String w0, w1, w2, w3, w4, w5;
    static String roundConstant1 = "10000000";
    static String roundConstant2 = "00110000";
    static String key1, key2, key3;

    static String xorStrings(String a, String b) {
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }
        }
        return result;
    }

    static String subNibble(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i += 4) {
            String nibble = s.substring(i, i + 4);
            int row = Integer.parseInt(nibble.substring(0, 2), 2);
            int col = Integer.parseInt(nibble.substring(2, 4), 2);
            result += sbox[row][col];
        }
        return result;        
    }

    static String rotateNibble(String nibble) {
        return nibble.substring(4, 8) + nibble.substring(0, 4);
    }

    static String shiftRow(String s) {
        String shifted = s.substring(0, 4) + s.substring(12, 16) + s.substring(8, 12) + s.substring(4, 8);
        return shifted;
    }

    static void generateSubKeys(String key) {
        w0 = key.substring(0, 8);
        w1 = key.substring(8, 16);

        w2 = xorStrings(w0, roundConstant1);
        w2 = xorStrings(w2, subNibble(rotateNibble(w1)));

        w3 = xorStrings(w2, w1);

        w4 = xorStrings(w2, roundConstant2);
        w4 = xorStrings(w4, subNibble(rotateNibble(w3)));

        w5 = xorStrings(w4, w3);

        key1 = w0 + w1;
        key2 = w2 + w3;
        key3 = w4 + w5;

        System.out.println("Key 1: " + key1);
        System.out.println("Key 2: " + key2);
        System.out.println("Key 3: " + key3);
    }

    static String mixColumns(String s) {
        int[][] mixMatrix = { { 1, 4 }, { 4, 1 } };
        int[][] stateMatrix = new int[2][2];

        // Populate the stateMatrix from the binary string 's'
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                String binaryString = s.substring(8 * i + 4 * j, 8 * i + 4 * j + 4);
                stateMatrix[i][j] = Integer.parseInt(binaryString, 2);
            }
        }

        int[][] mixedMatrix = new int[2][2];

        // Mix the columns
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                mixedMatrix[i][j] = 0;
                for (int k = 0; k < 2; k++) {
                    int mul = mixMatrix[i][k] * stateMatrix[k][j];
                    mixedMatrix[i][j] ^= (mul ^ ((mul & 0x10) >> 4) * 0x13) & 0xF; // keep the result within 4 bits
                }
            }
        }

        // Convert the mixedMatrix back to a binary string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result.append(String.format("%4s", Integer.toBinaryString(mixedMatrix[i][j]))
                        .replace(' ', '0')); // convert back to binary and pad with zeroes if necessary
            }
        }

        return result.toString();
    }

    static String encrypt(String input) {
        String cipher = input;
        System.out.println("Initial input: " + cipher);

        cipher = xorStrings(cipher, key1);
        System.out.println("After round key 1: " + cipher);

        // Round 1
        cipher = subNibble(cipher);
        System.out.println("After subNibble: " + cipher);

        cipher = shiftRow(cipher);
        System.out.println("After shiftRow: " + cipher);

        cipher = mixColumns(cipher);
        System.out.println("After mixColumns: " + cipher);

        cipher = xorStrings(cipher, key2);
        System.out.println("After round key 2: " + cipher);

        // Round 2
        cipher = subNibble(cipher);
        System.out.println("After subNibble: " + cipher);

        cipher = shiftRow(cipher);
        System.out.println("After shiftRow: " + cipher);

        cipher = xorStrings(cipher, key3);
        System.out.println("After round key 3: " + cipher);

        return cipher;
    }

    public static void main(String[] args) {
        String input = "1101011100101000";
        String key = "0100101011110101";

        generateSubKeys(key);
        String cipher = encrypt(input);

        System.out.println("Original input: " + input);
        System.out.println("Encrypted output: " + cipher);
    }
}