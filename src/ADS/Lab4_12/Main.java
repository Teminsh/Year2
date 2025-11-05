package ADS.Lab4_12;

import java.util.Arrays;

public class Main
{
    static void main()
    {
        int[] array = {5, 17, 89, 150, 360, 450, 720, 1023, -10, -3, -1, 0, 1, 2};
        Sorting.ShellSort(array);
        System.out.println(Arrays.toString(array));
    }
}
