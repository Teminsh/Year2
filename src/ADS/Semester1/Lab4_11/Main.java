package ADS.Semester1.Lab4_11;

import java.util.Arrays;

public class Main
{
    static void main()
    {
        int[] array = {5, 17, 89, 150, 360, 450, 720, 1023, 0, 1, 2, 315};
        Sorting.QuickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
