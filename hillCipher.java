import java.util.Scanner;

public class hillCipher {
    public static int[][] matrixMultiplication(int[][] key, int[][] plainTextMatrix){
        int[][] result = new int[3][1];
        for(int i=0;i<3;i++){
            for(int j=0;j<1;j++){
                result[i][j] = 0;
                for(int k=0;k<3;k++){
                    result[i][j] += key[i][k] * plainTextMatrix[k][j];
                }
                result[i][j] = result[i][j] % 26;
            }
        }
        return result;
    }

    public static int[][] matrixMod26(int[][] matrix){
        for(int i=0;i<3;i++){
            for(int j=0;j<1;j++){
                matrix[i][j] = matrix[i][j] % 26;
            }
        }
        return matrix;
    }

    public static String matrixToString(int[][] matrix){
        String result = "";
        for(int i=0;i<3;i++){
            result += (char)(matrix[i][0] + 65);
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the key matrix of 3x3");
        int[][] key = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                key[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the plain text of 3 characters");
        String plainText = sc.next();
        int[][] plainTextMatrix = new int[3][1];
        for(int i=0;i<3;i++){
            plainTextMatrix[i][0] = plainText.charAt(i) - 65;
        }

        int[][] cipherTextMatrix = matrixMultiplication(key, plainTextMatrix);
        cipherTextMatrix = matrixMod26(cipherTextMatrix);
        String cipherText = matrixToString(cipherTextMatrix);

        System.out.println("Cipher Text: " + cipherText);

        
        sc.close();
    }
}