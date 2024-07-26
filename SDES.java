import java.util.Scanner;

public class SDES {

    public static int[] P10transform(int[] P10, int[] key){
        int[] newKey = new int[10];
        for (int i = 0; i < 10; i++) {
            newKey[i + 1] = key[P10[i + 1]];
        }
        return newKey;
    }
    public static int[] leftShift(int[] halfKey){
//        int[] half = new int[5];
        for (int i = 0; i < halfKey.length; i++) {
            if(i>0){
                halfKey[i - 1] = halfKey[i];
            }
        }
        halfKey[halfKey.length - 1] = halfKey[0];
        return halfKey;
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




    }
}
