package ADS.Lab4_12;

import java.util.Arrays;

public class Main
{
    static void main()
    {
        int[] array = {9, 7, 5, 3, 1};
        Sorting.ShellSort(array);
        System.out.println(Arrays.toString(array));
    }
}
