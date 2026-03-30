package ADS.Semester1.Lab17;

import java.util.Scanner;

public class BSTOperations
{
    // region TreeNode
    static class TreeNode
    {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value)
        {
            this.value = value;
        }
    }
    // endregion

    static class BinarySearchTree
    {
        TreeNode root;

        // region Парсинг линейно-скобочной записи

        public void parseFromString(String input)
        {
            root = parseNode(input.trim());
        }

        private TreeNode parseNode(String s)
        {
            s = s.trim();

            if (s.isEmpty())
            {
                return null;
            }

            int openParen = s.indexOf('(');

            if (openParen == -1)
            {
                try
                {
                    return new TreeNode(Integer.parseInt(s.trim()));
                }
                catch (NumberFormatException e)
                {
                    return null;
                }
            }

            String valueStr = s.substring(0, openParen).trim();

            if (valueStr.isEmpty())
            {
                return null;
            }

            TreeNode node = new TreeNode(Integer.parseInt(valueStr));

            String childrenStr = s.substring(openParen + 1, s.lastIndexOf(')')).trim();

            if (!childrenStr.isEmpty())
            {
                int commaPos = findTopLevelComma(childrenStr);
                if (commaPos != -1)
                {
                    String leftStr = childrenStr.substring(0, commaPos).trim();
                    String rightStr = childrenStr.substring(commaPos + 1).trim();

                    node.left = leftStr.isEmpty() ? null : parseNode(leftStr);
                    node.right = rightStr.isEmpty() ? null : parseNode(rightStr);
                }
                else
                {
                    node.left = parseNode(childrenStr);
                }
            }

            return node;
        }

        private int findTopLevelComma(String s)
        {
            int depth = 0;
            for (int i = 0; i < s.length(); i++)
            {
                if (s.charAt(i) == '(') depth++;
                else if (s.charAt(i) == ')') depth--;
                else if (s.charAt(i) == ',' && depth == 0) return i;
            }
            return -1;
        }

        // endregion

        // region Поиск элемента

        public boolean search(int value)
        {
            return searchHelper(root, value);
        }

        private boolean searchHelper(TreeNode node, int value)
        {
            if (node == null) return false;
            if (value == node.value) return true;
            if (value < node.value) return searchHelper(node.left, value);
            return searchHelper(node.right, value);
        }

        // endregion

        // region Добавление элемента

        public void insert(int value)
        {
            root = insertHelper(root, value);
        }

        private TreeNode insertHelper(TreeNode node, int value)
        {
            if (node == null) return new TreeNode(value);

            if (value < node.value)
            {
                node.left = insertHelper(node.left, value);
            }
            else if (value > node.value)
            {
                node.right = insertHelper(node.right, value);
            }

            return node;
        }

        // endregion

        // region Удаление элемента

        public void delete(int value)
        {
            root = deleteHelper(root, value);
        }

        private TreeNode deleteHelper(TreeNode node, int value)
        {
            if (node == null) return null;

            if (value < node.value)
            {
                node.left = deleteHelper(node.left, value);
            }
            else if (value > node.value)
            {
                node.right = deleteHelper(node.right, value);
            }
            else
            {
                if (node.left == null && node.right == null)
                {
                    return null;
                }
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;

                int minValue = findMin(node.right);
                node.value = minValue;
                node.right = deleteHelper(node.right, minValue);
            }

            return node;
        }

        private int findMin(TreeNode node)
        {
            while (node.left != null)
            {
                node = node.left;
            }
            return node.value;
        }

        // endregion

        // region Вывод в линейно-скобочной записи

        public String toParenthesisNotation()
        {
            return toParenthesisHelper(root);
        }

        private String toParenthesisHelper(TreeNode node)
        {
            if (node == null) return "";

            String result = String.valueOf(node.value);

            if (node.left != null || node.right != null)
            {
                result += " (" + toParenthesisHelper(node.left);
                result += ", " + toParenthesisHelper(node.right) + ")";
            }

            return result;
        }

        // endregion

        // region Графический вывод дерева

        public void printTree()
        {
            printHelper(root, "", true);
        }

        private void printHelper(TreeNode node, String prefix, boolean isTail)
        {
            if (node != null)
            {
                System.out.println(prefix + (isTail ? "└── " : "├── ") + node.value);
                if (node.left != null || node.right != null)
                {
                    if (node.right != null)
                    {
                        printHelper(node.right, prefix + (isTail ? "    " : "│   "), false);
                    }
                    if (node.left != null)
                    {
                        printHelper(node.left, prefix + (isTail ? "    " : "│   "), true);
                    }
                }
            }
        }

        // endregion
    }

    static void main()
    {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree tree = new BinarySearchTree();

        System.out.println("=== ЛАБА №17: ОПЕРАЦИИ НАД БДП ===\n");
        System.out.println("Введите дерево (пример: 8 (3 (1, 6), 10 (, 14))):");
        String input = scanner.nextLine();
        tree.parseFromString(input);

        System.out.println("\n✓ Дерево создано!");
        tree.printTree();

        boolean running = true;
        while (running)
        {
            System.out.println("\n╔══════════════════════════╗");
            System.out.println("║   МЕНЮ ОПЕРАЦИЙ          ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║ 1. Поиск                 ║");
            System.out.println("║ 2. Добавление            ║");
            System.out.println("║ 3. Удаление              ║");
            System.out.println("║ 4. Показать дерево       ║");
            System.out.println("║ 5. Выход                 ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Выбор: ");

            int choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    System.out.print("Значение: ");
                    int searchVal = scanner.nextInt();
                    System.out.println(tree.search(searchVal)
                            ? "✓ Найдено" : "✗ Не найдено");
                    break;

                case 2:
                    System.out.print("Значение: ");
                    int insertVal = scanner.nextInt();
                    tree.insert(insertVal);
                    System.out.println("✓ Добавлено!");
                    break;

                case 3:
                    System.out.print("Значение: ");
                    int deleteVal = scanner.nextInt();
                    tree.delete(deleteVal);
                    System.out.println("✓ Удалено!");
                    break;

                case 4:
                    System.out.println("\nГрафический вид:");
                    tree.printTree();
                    System.out.println("\nСкобочная запись:");
                    System.out.println(tree.toParenthesisNotation());
                    break;

                case 5:
                    running = false;
                    System.out.println("\nИтоговое дерево:");
                    System.out.println(tree.toParenthesisNotation());
                    break;

                default:
                    System.out.println("✗ Неверный выбор!");
            }
        }

        scanner.close();
    }
}
