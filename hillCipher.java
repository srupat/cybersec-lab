import java.util.Scanner;

public class hillCipher {
    public static int[][] matrixMultiplication(int[][] key, int[][] textMatrix) {
        int[][] result = new int[2][1];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 1; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 2; k++) {
                    result[i][j] += key[i][k] * textMatrix[k][j];
                }
                result[i][j] = result[i][j] % 26;
            }
        }
        return result;
    }

    public static int[][] matrixMod26(int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 1; j++) {
                matrix[i][j] = matrix[i][j] % 26;
                if (matrix[i][j] < 0) {
                    matrix[i][j] += 26;
                }
            }
        }
        return matrix;
    }

    public static String matrixToString(int[][] matrix) {
        String result = "";
        for (int i = 0; i < 2; i++) {
            result += (char)(matrix[i][0] + 65);
        }
        return result;
    }

    public static int[][] inverseMatrix(int[][] key) {
        int[][] inverse = new int[2][2];
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        det = det % 26;
        if (det < 0) {
            det += 26;
        }
        int detInverse = 0;
        for (int i = 1; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInverse = i;
                break;
            }
        }
        inverse[0][0] = (detInverse * key[1][1]) % 26;
        inverse[0][1] = (detInverse * -key[0][1]) % 26;
        inverse[1][0] = (detInverse * -key[1][0]) % 26;
        inverse[1][1] = (detInverse * key[0][0]) % 26;

        return matrixMod26(inverse);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the key matrix of 2x2:");
        int[][] key = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the plain text of 2 characters:");
        String plainText = sc.next();
        int[][] plainTextMatrix = new int[2][1];
        for (int i = 0; i < 2; i++) {
            plainTextMatrix[i][0] = plainText.charAt(i) - 65;
        }

        int[][] cipherTextMatrix = matrixMultiplication(key, plainTextMatrix);
        cipherTextMatrix = matrixMod26(cipherTextMatrix);
        String cipherText = matrixToString(cipherTextMatrix);

        System.out.println("Cipher Text: " + cipherText);

        System.out.println("Decrypting...");
        int[][] inverseKey = inverseMatrix(key);
        int[][] decryptedMatrix = matrixMultiplication(inverseKey, cipherTextMatrix);
        decryptedMatrix = matrixMod26(decryptedMatrix);
        String decryptedText = matrixToString(decryptedMatrix);

        System.out.println("Decrypted Text: " + decryptedText);
        sc.close();
    }
}
