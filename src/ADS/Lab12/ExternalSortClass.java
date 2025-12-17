package ADS.Lab12;

import java.io.*;
import java.util.Random;

public class ExternalSortClass
{
    private static final int BLOCK_SIZE = 1000;

    static void main()
    {
        int numCount = 10000;
        String inputFile = "input.txt";
        String outputFile = "sorted_output.txt";

        GenerateTestFile(inputFile, numCount);

        try
        {
            ExternalSort(inputFile, outputFile);

            if (isSorted(outputFile))
            {
                System.out.println("Файл отсортирован");
            } else
            {
                System.out.println("Файл НЕ отсортирован");
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }


    private static void ExternalSort(String inputFile, String outputFile)
    {

    }

    private static boolean isSorted(String fileName) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName)))
        {
            String line = bufferedReader.readLine();
            if (line == null)
            {
                return true;
            }
            int prev = Integer.parseInt(line);
            while ((line = bufferedReader.readLine()) != null)
            {
                int current = Integer.parseInt(line);
                if (current < prev)
                {
                    return false;
                }
                prev = current;
            }
        }
        return true;
    }

    private static void GenerateTestFile(String fileName, int count)
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName)))
        {
            Random random = new Random();
            for (int i = 0; i < count; i++)
            {
                bufferedWriter.write(String.valueOf(random.nextInt(1000000)));
                bufferedWriter.newLine();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}