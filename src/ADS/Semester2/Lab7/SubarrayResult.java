package ADS.Semester2.Lab7;

public class SubarrayResult {
    private final int maxSum;
    private final int startIndex;
    private final int endIndex;
    private final int[] subarray;

    public SubarrayResult(int maxSum, int startIndex, int endIndex, int[] originalArray) {
        this.maxSum     = maxSum;
        this.startIndex = startIndex;
        this.endIndex   = endIndex;
        this.subarray   = new int[endIndex - startIndex + 1];
        System.arraycopy(originalArray, startIndex, this.subarray, 0, this.subarray.length);
    }

    public int getMaxSum()    { return maxSum;     }
    public int getStartIndex(){ return startIndex; }
    public int getEndIndex()  { return endIndex;   }
    public int[] getSubarray(){ return subarray;   }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Наибольшая сумма  : ").append(maxSum).append("\n");
        sb.append("Индексы           : [").append(startIndex).append(" ... ").append(endIndex).append("]\n");
        sb.append("Подмассив         : [");
        for (int i = 0; i < subarray.length; i++) {
            sb.append(subarray[i]);
            if (i < subarray.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
