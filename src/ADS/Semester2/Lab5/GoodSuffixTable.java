package ADS.Semester2.Lab5;

public class GoodSuffixTable {

    public static int[] build(String pattern) {
        int m = pattern.length();
        int[] shift = new int[m + 1];
        int[] borderPos = new int[m + 1];

        preprocessStrongSuffix(shift, borderPos, pattern, m);
        preprocessCase2(shift, borderPos, m);

        return shift;
    }

    private static void preprocessStrongSuffix(int[] shift, int[] borderPos, String pattern, int m) {
        int i = m;
        int j = m + 1;
        borderPos[i] = j;

        while (i > 0) {
            while (j <= m && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {
                if (shift[j] == 0) {
                    shift[j] = j - i;
                }
                j = borderPos[j];
            }
            i--;
            j--;
            borderPos[i] = j;
        }
    }

    private static void preprocessCase2(int[] shift, int[] borderPos, int m) {
        int j = borderPos[0];

        for (int i = 0; i <= m; i++) {
            if (shift[i] == 0) {
                shift[i] = j;
            }
            if (i == j) {
                j = borderPos[j];
            }
        }
    }
}
