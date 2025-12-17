package ADS.Lab4_11;

import java.util.Arrays;

public class Sorting
{
    // region 4 Comb Sort

    public static void CombSort(int[] arr)
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

    //region 5 Insertion Sort

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

    //region 6 Selection Sort

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

    //region 7 Shell Sort

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

    //region 8 Radix Sort

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


        for (int i : arr)
        {
            int digit = (i / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++)
        {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--)
        {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    //endregion

    // region 9 Heap Sort

    public static void HeapSort(int[] arr)
    {
        int n = arr.length;

        for (int i = n / 2; i >= 0; i--)
        {
            Heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--)
        {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            Heapify(arr, i ,0);
        }
    }

    private static void Heapify(int[] arr, int n, int i)
    {
        int largest = i;
        int left = i * 2 + 1;
        int right = i * 2 + 2;

        if (left < n && arr[left] > arr[largest])
        {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest])
        {
            largest = right;
        }

        if (largest != i)
        {
            int tmp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = tmp;

            Heapify(arr, n, largest);
        }
    }


    // endregion

    // region 10 Merge Sort

    public static void MergeSort(int[] arr)
    {
        if (arr.length < 2)
        {
            return;
        }
        MergeSortHelper(arr, 0, arr.length - 1);
    }

    private static void MergeSortHelper(int[] arr, int left, int right)
    {
        if (left < right)
        {
            int mid = (left + right) / 2;

            MergeSortHelper(arr, left, mid);
            MergeSortHelper(arr, mid + 1, right);

            Merge(arr, left, mid, right);
        }
    }

    private static void Merge(int[] arr, int left, int mid, int right)
    {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(arr, left, leftArray, 0, n1);

        for (int j = 0; j < n2; j++)
        {
            rightArray[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2)
        {
            if (leftArray[i] <= rightArray[j])
            {
                arr[k] = leftArray[i];
                i++;
            }
            else
            {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1)
        {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2)
        {
            arr[k] = rightArray[j];
            j++;
            k++;
        }

    }

    // endregion

    // region 11 Quick Sort

    public static void QuickSort(int[] arr)
    {
        if (arr.length < 2)
        {
            return;
        }
        QuickSortHelper(arr, 0, arr.length - 1);
    }

    private static void QuickSortHelper(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int partitionIndex = Partition(arr, low, high);

            QuickSortHelper(arr, low, partitionIndex - 1);
            QuickSortHelper(arr, partitionIndex + 1, high);
        }
    }

    private static int Partition(int[] arr, int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j ++)
        {
            if (arr[j] <= pivot)
            {
                i++;

                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        int tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;

        return i + 1;
    }

    // endregion

}