package ADS.Lab4_12;

import java.util.Arrays;

public class Sorting
{

    // region Comb Sort

    public static void combSort(int[] arr)
    {
        int gap = arr.length;
        boolean swapped = true;
        double shrink = 1.247;

        while (gap > 1 || swapped)
        {
            gap = (int) (gap / shrink);

            if (gap < 1) { gap = 1; }

            swapped = false;

            for (int i = 0;  i + gap < arr.length; i++)
            {
                if (arr[i] > arr[i + gap])
                {
                    int tmp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = tmp;
                    swapped = true;
                }
            }
        }
    }

    //endregion

    //region Insertion Sort

    public static void InsertionSort(int[] arr)
    {
        for (int i = 1; i < arr.length; i++)
        {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    //endregion

    //region Selection Sort

    public static void SelectionSort(int[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++)
            {
                if (arr[j] < arr[minIndex])
                {
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }

    //endregion

    //region Shell Sort

    public static void ShellSort(int[] arr)
    {
        int n = arr.length;

        for (int gap = n / 2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < n; i++)
            {
                int tmp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > tmp)
                {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = tmp;
            }
        }
    }

    //endregion

    //region Radix Sort

    public static void RadixSort(int[] arr)
    {
        int max = Arrays.stream(arr).max().orElse(0);

        for (int exp = 1; max / exp > 0; exp *= 10)
        {
            CountingSortByDigit(arr, exp);
        }
    }

    private static void CountingSortByDigit(int[] arr, int exp)
    {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];


    }

    //endregion
}