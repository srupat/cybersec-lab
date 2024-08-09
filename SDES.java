import java.util.Scanner;

public class SDES {

    public static int[] P10transform(int[] P10, int[] key) {
        int[] newKey = new int[10];
        for (int i = 0; i < 10; i++) {
            newKey[i] = key[P10[i] - 1];
        }
        return newKey;
    }

    public static int[] leftShift(int[] halfKey) {
        int firstBit = halfKey[0];
        for (int i = 0; i < halfKey.length; i++) {
            if (i > 0) {
                halfKey[i - 1] = halfKey[i];
            }
        }
        halfKey[halfKey.length - 1] = firstBit;
        return halfKey;
    }

    public static int[] leftHalf(int[] key) {
        int[] leftHalf = new int[key.length / 2];
        for (int i = 0; i < key.length / 2; i++) {
            leftHalf[i] = key[i];
        }
        return leftHalf;
    }

    public static int[] rightHalf(int[] key) {
        int[] rightHalf = new int[key.length / 2];
        for (int i = 0; i < key.length / 2; i++) {
            rightHalf[i] = key[i + key.length / 2];
        }
        return rightHalf;
    }

    public static int[] combine(int[] left, int[] right) {
        int[] combined = new int[left.length + right.length];
        for (int i = 0; i < left.length; i++) {
            combined[i] = left[i];
        }
        for (int i = 0; i < right.length; i++) {
            combined[i + left.length] = right[i];
        }
        return combined;
    }

    public static int[] IPtransform(int[] IP, int[] input) {
        int[] newInput = new int[8];
        for (int i = 0; i < 8; i++) {
            newInput[i] = input[IP[i] - 1];
        }
        return newInput;
    }

    public static int[] P8transform(int[] P8, int[] key) {
        int[] newKey = new int[8];
        for (int i = 0; i < 8; i++) {
            newKey[i] = key[P8[i] - 1];
        }
        return newKey;
    }

    public static int[] EIPtransform(int[] EIP, int[] input) {
        int[] newInput = new int[8];
        for (int i = 0; i < 8; i++) {
            newInput[i] = input[EIP[i] - 1];
        }
        return newInput;
    }

    public static int[] XOR(int[] input, int[] key) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i] == key[i]) {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }
        return result;
    }

    public static int[] SBox(int[] input, int[][] S) {
        int row = 2 * input[0] + input[3];
        int col = 2 * input[1] + input[2];
        // decimal to binary (2 digits)

        int[] output = new int[2];
        int value = S[row][col];
        output[0] = value / 2;
        output[1] = value % 2;
        // binary to decimal (2 digits)

        return output;
    }

    public static int[] combineAfterSBox(int[] S0, int[] S1) {
        int[] combined = new int[4];
        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                combined[i] = S0[i];
            } else {
                combined[i] = S1[i - 2];
            }
        }
        return combined;
    }

    public static int[] P4transform(int[] P4, int[] input) {
        int[] newInput = new int[4];
        for (int i = 0; i < 4; i++) {
            newInput[i] = input[P4[i] - 1];
        }
        return newInput;
    }

    public static int[] IPinvtransform(int[] IPinv, int[] input) {
        int[] newInput = new int[8];
        for (int i = 0; i < 8; i++) {
            newInput[i] = input[IPinv[i] - 1];
        }
        return newInput;
    }

    public static void main(String[] args) {
        int[] input = new int[8];
        int[] result = new int[8];
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the input\n");
        for (int i = 0; i < input.length; i++) {
            input[i] = sc.nextInt();
        }
        int[] key = new int[10];
        System.out.println("Please enter the key\n");
        for (int i = 0; i < key.length; i++) {
            key[i] = sc.nextInt();
        }

        int[] P10 = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
        int[] P8 = { 6, 3, 7, 4, 8, 5, 10, 9 };
        int[] P4 = { 2, 4, 3, 1 };

        int[] IP = { 2, 6, 3, 1, 4, 8, 5, 7 };
        int[] IPinv = { 4, 1, 3, 5, 7, 2, 8, 6 };

        int[] EIP = { 4, 1, 2, 3, 2, 3, 4, 1 };

        int[][] S0 = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3, 1, 3, 2 } };
        int[][] S1 = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2, 1, 0, 3 } };

        int[] K1 = new int[8];
        K1 = P8transform(P8,
                combine(leftShift(rightHalf(P10transform(P10, key))), leftShift(leftHalf(P10transform(P10, key)))));
        System.out.println("Key1: ");
        for (int i : K1) {
            System.out.print(i + " ");
        }

        int[] K2 = new int[8];
        K2 = P8transform(P8, combine(leftShift(leftShift(rightHalf(P10transform(P10, key)))),
                leftShift(leftShift(leftHalf(P10transform(P10, key))))));
        System.out.println("\nKey2: ");
        for (int i : K2) {
            System.out.print(i + " ");
        }

        result = IPtransform(IP, input);
        int[] L0 = new int[4];
        int[] R0 = new int[4];
        int[] temp = new int[4];
        L0 = leftHalf(result);
        R0 = rightHalf(result);
        temp = rightHalf(result);
        temp = XOR(EIPtransform(EIP, temp), K1);
        int[] S0output = new int[2];
        int[] S1output = new int[2];
        S0output = SBox(temp, S0);
        S1output = SBox(temp, S1);
        temp = P4transform(P4, combineAfterSBox(S0output, S1output));
        temp = XOR(L0, temp);
        L0 = R0;
        R0 = temp;

        temp = new int[4];
        temp = rightHalf(combine(L0, R0));
        temp = XOR(EIPtransform(EIP, temp), K2);
        S0output = new int[2];
        S1output = new int[2];
        S0output = SBox(temp, S0);
        S1output = SBox(temp, S1);
        temp = P4transform(P4, combineAfterSBox(S0output, S1output));
        temp = XOR(L0, temp);
        result = combine(temp, R0);

        result = IPinvtransform(IPinv, result);

        System.out.println("\nResult: ");
        for (int i : result) {
            System.out.print(i + " ");
        }

    }
}
