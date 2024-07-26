import java.util.Scanner;

public class SDES {

    public static int[] P10transform(int[] P10, int[] key){
        int[] newKey = new int[10];
        for (int i = 0; i < 10; i++) {
            newKey[i] = key[P10[i] - 1];
        }
        return newKey;
    }

    public static int[] leftShift(int[] halfKey){
        int firstBit = halfKey[0];
        for (int i = 0; i < halfKey.length; i++) {
            if(i>0){
                halfKey[i - 1] = halfKey[i];
            }
        }
        halfKey[halfKey.length - 1] = firstBit;
        return halfKey;
    }

    public static int[] rightHalf(int[] key){
        int[] leftHalf = new int[key.length/2];
        for (int i = 0; i < key.length/2; i++) {
            leftHalf[i] = key[i];
        }
        return leftHalf;
    }

    public static int[] leftHalf(int[] key){
        int[] rightHalf = new int[key.length/2];
        for (int i = 0; i < key.length/2; i++) {
            rightHalf[i] = key[i + key.length/2];
        }
        return rightHalf;
    }

    public static int[] combine(int[] left, int[] right){
        int[] combined = new int[left.length + right.length];
        for (int i = 0; i < left.length; i++) {
            combined[i] = left[i];
        }
        for (int i = 0; i < right.length; i++) {
            combined[i + left.length] = right[i];
        }
        return combined;
    }

    public static int[] P8transform(int[] P8, int[] key){
        int[] newKey = new int[8];
        for (int i = 0; i < 8; i++) {
            newKey[i] = key[P8[i] - 1];
        }
        return newKey;
    }
    public static void main(String[] args) {
        int[] input = new int[8];
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the input\n");
        for(int i=0;i<input.length;i++){
            input[i] = sc.nextInt();
        }
        int[] key = new int[10];
        System.out.println("Please enter the key\n");
        for (int i = 0; i < key.length; i++) {
            key[i] = sc.nextInt();
        }

        int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
        int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};
        int[] P4 = {2, 4, 3, 1};

        int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
        int[] IPinv = {4, 1, 3, 5, 7, 2, 8, 6};

        int[] K1 = new int[8];
        K1 = P8transform(P8, combine(leftShift(rightHalf(P10transform(P10, key))), leftShift(leftHalf(P10transform(P10, key)))));
        for (int i : K1) {
            System.out.print(i + " ");
        }

        int[] K2 = new int[8];
        K2 = P8transform(P8, combine(leftShift(leftShift(rightHalf(P10transform(P10, key)))), leftShift(leftShift(leftHalf(P10transform(P10, key))))));
        for (int i : K2) {
            System.out.print(i + " ");
        }
    }
}
