package ADS.Semester1.Lab13_14;

import java.io.*;

public class HashTableMaker
{
    // region Лаба №13: Открытая адресация (Open Addressing)

    static class HashTableOpenAddressing
    {
        private static final int TABLE_SIZE = 101;
        private static final String DELETED = "__DELETED__";

        private String[] keys;
        private Integer[] values;
        private int size;

        // region Конструктор

        public HashTableOpenAddressing()
        {
            keys = new String[TABLE_SIZE];
            values = new Integer[TABLE_SIZE];
            size = 0;
        }

        // endregion

        // region Хеш-функция

        private int hash(String key)
        {
            int hashVal = 0;
            for (int i = 0; i < key.length(); i++)
            {
                hashVal = 37 * hashVal + key.charAt(i);
            }
            hashVal = hashVal % TABLE_SIZE;
            if (hashVal < 0)
            {
                hashVal += TABLE_SIZE;
            }
            return hashVal;
        }

        // endregion

        // region Вставка элемента

        public void put(String key, int value)
        {
            int index = hash(key);
            int originalIndex = index;

            while (keys[index] != null && !keys[index].equals(DELETED)
                    && !keys[index].equals(key))
            {
                index = (index + 1) % TABLE_SIZE;

                if (index == originalIndex)
                {
                    System.out.println("Таблица переполнена!");
                    return;
                }
            }

            if (keys[index] == null || keys[index].equals(DELETED))
            {
                keys[index] = key;
                values[index] = value;
                size++;
            }
            else if (keys[index].equals(key))
            {
                values[index] += value;
            }
        }

        // endregion

        // region Получение элемента

        public Integer get(String key)
        {
            int index = hash(key);
            int originalIndex = index;

            while (keys[index] != null)
            {
                if (!keys[index].equals(DELETED) && keys[index].equals(key))
                {
                    return values[index];
                }
                index = (index + 1) % TABLE_SIZE;

                if (index == originalIndex)
                {
                    break;
                }
            }
            return null;
        }

        // endregion

        // region Сохранение в файл

        public void saveToFile(String fileName) throws IOException
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
            {
                writer.write("=== ЛАБА №13: ХЕШ-ТАБЛИЦА С ОТКРЫТОЙ АДРЕСАЦИЕЙ ===\n\n");
                writer.write(String.format("Размер таблицы: %d\n", TABLE_SIZE));
                writer.write(String.format("Элементов: %d\n", size));
                writer.write(String.format("Коэффициент заполнения: %.2f%%\n\n",
                        (size * 100.0) / TABLE_SIZE));

                writer.write("╔════════╦══════════════════╦═══════════╗\n");
                writer.write("║ Индекс ║ Ключ (слово)     ║ Частота   ║\n");
                writer.write("╠════════╬══════════════════╬═══════════╣\n");

                for (int i = 0; i < TABLE_SIZE; i++)
                {
                    if (keys[i] != null && !keys[i].equals(DELETED))
                    {
                        writer.write(String.format("║ %6d ║ %-16s ║ %9d ║\n",
                                i, keys[i], values[i]));
                    }
                }
                writer.write("╚════════╩══════════════════╩═══════════╝\n");
            }
        }

        // endregion
    }

    // endregion

    // region Лаба №14: Метод цепочек (Chaining Method)

    static class HashTableChaining
    {
        private static final int TABLE_SIZE = 101;

        // region Node класс

        static class Node
        {
            String key;
            int value;
            Node next;

            Node(String key, int value)
            {
                this.key = key;
                this.value = value;
                this.next = null;
            }
        }

        // endregion

        private Node[] table;
        private int size;

        // region Конструктор

        public HashTableChaining()
        {
            table = new Node[TABLE_SIZE];
            size = 0;
        }

        // endregion

        // region Хеш-функция

        private int hash(String key)
        {
            int hashVal = 0;
            for (int i = 0; i < key.length(); i++)
            {
                hashVal = 37 * hashVal + key.charAt(i);
            }
            hashVal = hashVal % TABLE_SIZE;
            if (hashVal < 0)
            {
                hashVal += TABLE_SIZE;
            }
            return hashVal;
        }

        // endregion

        // region Вставка элемента

        public void put(String key, int value)
        {
            int index = hash(key);
            Node current = table[index];

            while (current != null)
            {
                if (current.key.equals(key))
                {
                    current.value += value;
                    return;
                }
                current = current.next;
            }

            Node newNode = new Node(key, value);
            newNode.next = table[index];
            table[index] = newNode;
            size++;
        }

        // endregion

        // region Получение элемента

        public Integer get(String key)
        {
            int index = hash(key);
            Node current = table[index];

            while (current != null)
            {
                if (current.key.equals(key))
                {
                    return current.value;
                }
                current = current.next;
            }
            return null;
        }

        // endregion

        // region Сохранение в файл

        public void saveToFile(String fileName) throws IOException
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
            {
                writer.write("=== ЛАБА №14: ХЕШ-ТАБЛИЦА С МЕТОДОМ ЦЕПОЧЕК ===\n\n");
                writer.write(String.format("Размер таблицы: %d\n", TABLE_SIZE));
                writer.write(String.format("Элементов: %d\n", size));
                writer.write(String.format("Средняя длина цепочки: %.2f\n\n",
                        (double) size / TABLE_SIZE));

                writer.write("╔════════╦═══════════════════════════════════════════════════╗\n");
                writer.write("║ Индекс ║ Цепочка (ключ: частота)                           ║\n");
                writer.write("╠════════╬═══════════════════════════════════════════════════╣\n");

                int collisions = 0;
                int maxChainLength = 0;

                for (int i = 0; i < TABLE_SIZE; i++)
                {
                    if (table[i] != null)
                    {
                        Node current = table[i];
                        int chainLength = 0;
                        StringBuilder chain = new StringBuilder();

                        while (current != null)
                        {
                            chain.append(current.key)
                                    .append(":")
                                    .append(current.value);

                            if (current.next != null)
                            {
                                chain.append(" → ");
                            }
                            current = current.next;
                            chainLength++;
                        }

                        writer.write(String.format("║ %6d ║ %-57s ║\n",
                                i, chain.toString()));

                        if (chainLength > 1)
                        {
                            collisions++;
                        }
                        maxChainLength = Math.max(maxChainLength, chainLength);
                    }
                }

                writer.write("╚════════╩═══════════════════════════════════════════════════╝\n\n");
                writer.write("--- Статистика коллизий ---\n");
                writer.write(String.format("Количество коллизий: %d\n", collisions));
                writer.write(String.format("Максимальная длина цепочки: %d\n", maxChainLength));
            }
        }

        // endregion
    }

    // endregion

    // region Вспомогательные методы

    // region Обработка файла

    private static void processFile(String inputFile, Object hashTable) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;

        while ((line = reader.readLine()) != null)
        {
            String[] words = line.toLowerCase()
                    .replaceAll("[^а-яёa-z\\s]", " ")
                    .split("\\s+");

            for (String word : words)
            {
                if (!word.isEmpty())
                {
                    if (hashTable instanceof HashTableOpenAddressing)
                    {
                        ((HashTableOpenAddressing) hashTable).put(word, 1);
                    }
                    else if (hashTable instanceof HashTableChaining)
                    {
                        ((HashTableChaining) hashTable).put(word, 1);
                    }
                }
            }
        }
        reader.close();
    }

    // endregion

    // region Создание тестового файла

    private static void createTestFile(String fileName)
    {
        File file = new File(fileName);
        if (file.exists())
        {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            writer.write("Хеш-таблица это структура данных для хранения пар ключ-значение.\n");
            writer.write("Хеш-функция преобразует ключ в индекс массива.\n");
            writer.write("Коллизии возникают когда разные ключи имеют одинаковый хеш.\n");
            writer.write("Существует два основных метода разрешения коллизий.\n");
            writer.write("Первый метод это открытая адресация с линейным пробированием.\n");
            writer.write("Второй метод это использование связных списков или цепочек.\n");
            writer.write("Hash table is a data structure for storing key-value pairs.\n");
            writer.write("Hash function converts a key into an array index.\n");
            writer.write("Collisions occur when different keys have the same hash.\n");

            System.out.println("✓ Создан тестовый файл: " + fileName);
        }
        catch (IOException e)
        {
            System.err.println("Не удалось создать тестовый файл");
        }
    }

    // endregion

    // endregion

    static void main()
    {
        String inputFile = "input_text.txt";
        String outputFile13 = "lab13_open_addressing.txt";
        String outputFile14 = "lab14_chaining.txt";

        createTestFile(inputFile);

        try
        {
            System.out.println("=== Запуск Лабы №13 (Открытая адресация) ===");
            HashTableOpenAddressing hashTable13 = new HashTableOpenAddressing();
            processFile(inputFile, hashTable13);
            hashTable13.saveToFile(outputFile13);
            System.out.println("✓ Результат сохранен в " + outputFile13 + "\n");

            System.out.println("=== Запуск Лабы №14 (Метод цепочек) ===");
            HashTableChaining hashTable14 = new HashTableChaining();
            processFile(inputFile, hashTable14);
            hashTable14.saveToFile(outputFile14);
            System.out.println("✓ Результат сохранен в " + outputFile14 + "\n");
        }
        catch (IOException e)
        {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
