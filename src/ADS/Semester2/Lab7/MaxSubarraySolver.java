package ADS.Semester2.Lab7;

public class MaxSubarraySolver {

    /**
     * Алгоритм Кадане — O(n) время, O(1) доп. память.
     * Возвращает SubarrayResult с максимальной суммой, индексами и самим подмассивом.
     */
    public SubarrayResult solve(int[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Массив не должен быть пустым.");

        int maxSum     = array[0];
        int currentSum = array[0];
        int start      = 0;
        int end        = 0;
        int tempStart  = 0;

        for (int i = 1; i < array.length; i++) {
            if (currentSum + array[i] < array[i]) {
                currentSum = array[i];
                tempStart  = i;
            } else {
                currentSum += array[i];
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start  = tempStart;
                end    = i;
            }
        }
        return new SubarrayResult(maxSum, start, end, array);
    }
}