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


    private static void ExternalSort(String inputFile, String outputFile) throws IOException
    {
        int numRuns = createInitialRuns(inputFile);

        if (numRuns == 0)
        {
            new File(outputFile).createNewFile();
            return;
        }

        if (numRuns == 1)
        {
            copyFile("temp_run_0.txt", outputFile);
            new File("temp_run_0.txt").delete();
            return;
        }

        mergeRuns(numRuns, outputFile);
    }

    private static int createInitialRuns(String inputFile) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int runIndex = 0;
        int[] buffer = new int[BLOCK_SIZE];

        while (true)
        {
            int count = 0;
            String line;

            while (count < BLOCK_SIZE && (line = reader.readLine()) != null)
            {
                buffer[count++] = Integer.parseInt(line);
            }

            if (count == 0)
            {
                break;
            }

            java.util.Arrays.sort(buffer, 0, count);

            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("temp_run_" + runIndex + ".txt"));
            for (int i = 0; i < count; i++)
            {
                writer.write(String.valueOf(buffer[i]));
                writer.newLine();
            }
            writer.close();
            runIndex++;
        }

        reader.close();
        return runIndex;
    }

    private static void mergeRuns(int numRuns, String outputFile) throws IOException
    {
        int currentPass = 0;
        int runsInCurrentPass = numRuns;

        while (runsInCurrentPass > 1)
        {
            int nextRunIndex = 0;

            for (int i = 0; i < runsInCurrentPass; i += 2)
            {
                String file1 = (currentPass == 0)
                        ? "temp_run_" + i + ".txt"
                        : "temp_pass_" + currentPass + "_run_" + i + ".txt";

                if (i + 1 < runsInCurrentPass)
                {
                    String file2 = (currentPass == 0)
                            ? "temp_run_" + (i + 1) + ".txt"
                            : "temp_pass_" + currentPass + "_run_" + (i + 1) + ".txt";
                    String outFile = "temp_pass_" + (currentPass + 1) + "_run_"
                            + nextRunIndex + ".txt";

                    mergeTwoFiles(file1, file2, outFile);

                    new File(file1).delete();
                    new File(file2).delete();
                }
                else
                {
                    String outFile = "temp_pass_" + (currentPass + 1) + "_run_"
                            + nextRunIndex + ".txt";
                    copyFile(file1, outFile);
                    new File(file1).delete();
                }
                nextRunIndex++;
            }

            runsInCurrentPass = nextRunIndex;
            currentPass++;
        }

        String lastFile = "temp_pass_" + currentPass + "_run_0.txt";
        copyFile(lastFile, outputFile);
        new File(lastFile).delete();
    }

    private static void mergeTwoFiles(String file1, String file2, String outputFile)
            throws IOException
    {
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        while (line1 != null && line2 != null)
        {
            int val1 = Integer.parseInt(line1);
            int val2 = Integer.parseInt(line2);

            if (val1 <= val2)
            {
                writer.write(String.valueOf(val1));
                writer.newLine();
                line1 = reader1.readLine();
            }
            else
            {
                writer.write(String.valueOf(val2));
                writer.newLine();
                line2 = reader2.readLine();
            }
        }

        while (line1 != null)
        {
            writer.write(line1);
            writer.newLine();
            line1 = reader1.readLine();
        }

        while (line2 != null)
        {
            writer.write(line2);
            writer.newLine();
            line2 = reader2.readLine();
        }

        reader1.close();
        reader2.close();
        writer.close();
    }

    private static void copyFile(String source, String dest) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(source));
        BufferedWriter writer = new BufferedWriter(new FileWriter(dest));

        String line;
        while ((line = reader.readLine()) != null)
        {
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();
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