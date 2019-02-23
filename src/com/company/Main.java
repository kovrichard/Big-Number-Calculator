package com.company;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage calc.java <number1> <number2>");
            return;
        }

        if (!args[0].matches("^[0-9]+$") || !args[1].matches("^[0-9]+$")) {
            System.out.println("One or both of the inputs is not a number");
            return;
        }

        String[] aa = args[0].split("(?<!^)");
        String[] bb = args[1].split("(?<!^)");

        int sizeA = aa.length;
        int sizeB = bb.length;

        int[][] sumMatrix = new int[sizeB][sizeA + sizeB];

        initSumMatrix(sumMatrix, sizeB, sizeA + sizeB);

        int shift = 0;
        for (int i = 0; i < sizeB; i++) {
            Partial partial = new Partial(0, 0);
            for (int j = sizeA; j > 0; j--) {
                int num = (Integer.parseInt(bb[i]) * Integer.parseInt(aa[j - 1])) + partial.remainder;
                partial.number = (num % 10);
                partial.remainder = ((num - partial.number) / 10);

                sumMatrix[i][j + shift] = partial.number;
                if (partial.remainder != 0) sumMatrix[i][j + shift - 1] = partial.remainder;
            }
            shift++;
        }

        String[] number = new String[sizeA + sizeB + 1];
        int num = 0;

        for (int i = sizeA + sizeB - 1; i >= 0; i--) {
            Partial partial = new Partial(0, 0);
            for (int j = 0; j < sizeB; j++) {
                num += sumMatrix[j][i];
            }
            partial.number = (num % 10);
            partial.remainder = ((num - partial.number) / 10);
            num = partial.remainder;
            number[i + 1] = partial.number.toString();

            if (i == 0 && partial.remainder != 0) number[i] = partial.remainder.toString();
        }

        int i = 0;

        if (number[0] != null && number[0].equals("0")) i = 1;
        if (number[1] != null && number[1].equals("0")) i = 2;

        for (; i < number.length; i++) {
            if (number[i] != null)
                System.out.print(number[i]);
        }
        System.out.println("\n");
    }

    static class Partial {
        public Integer number;
        public Integer remainder;

        public Partial(Integer number, Integer remainder) {
            this.number = number;
            this.remainder = remainder;
        }
    }

    public static void initSumMatrix(int[][] matrix, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <  width; j++) {
                matrix[i][j] = 0;
            }
        }
    }
}