package ADS.Lab16;

import java.util.Stack;

public class NonRecursiveTraversal
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

    static class BinaryTree
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

        // region Нерекурсивный прямой обход со стеком

        public String preorderIterative()
        {
            if (root == null) return "";

            StringBuilder result = new StringBuilder();
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty())
            {
                TreeNode current = stack.pop();
                result.append(current.value).append(" ");

                if (current.right != null)
                {
                    stack.push(current.right);
                }

                if (current.left != null)
                {
                    stack.push(current.left);
                }
            }

            return result.toString().trim();
        }

        // endregion

        // region Визуализация работы стека

        public void preorderWithStackVisualization()
        {
            if (root == null)
            {
                System.out.println("Дерево пустое!");
                return;
            }

            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║  ПРОЦЕСС ОБХОДА С ИСПОЛЬЗОВАНИЕМ СТЕКА             ║");
            System.out.println("╚════════════════════════════════════════════════════╝\n");

            StringBuilder result = new StringBuilder();
            int step = 1;

            while (!stack.isEmpty())
            {
                System.out.printf("Шаг %2d: ", step++);
                System.out.print("Стек = [" + stackToString(stack) + "]");

                TreeNode current = stack.pop();
                result.append(current.value).append(" ");

                System.out.printf(" → Извлекли: %d%n", current.value);

                if (current.right != null || current.left != null)
                {
                    System.out.print("         Добавляем в стек: ");
                    if (current.right != null)
                    {
                        stack.push(current.right);
                        System.out.print("правый(" + current.right.value + ") ");
                    }
                    if (current.left != null)
                    {
                        stack.push(current.left);
                        System.out.print("левый(" + current.left.value + ")");
                    }
                    System.out.println();
                }
                System.out.println();
            }

            System.out.println("─".repeat(52));
            System.out.println("Итоговая последовательность: " + result.toString().trim());
        }

        private String stackToString(Stack<TreeNode> stack)
        {
            if (stack.isEmpty()) return "";

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < stack.size(); i++)
            {
                sb.append(stack.get(i).value);
                if (i < stack.size() - 1) sb.append(", ");
            }
            return sb.toString();
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
        BinaryTree tree = new BinaryTree();

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║  ЛАБА №16: НЕРЕКУРСИВНЫЙ ПРЯМОЙ ОБХОД             ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        String input = "8 (3 (1, 6 (4,7)), 10 (, 14(13,)))";
        System.out.println("Входная запись: " + input);

        tree.parseFromString(input);

        System.out.println("\nГрафический вид дерева:");
        tree.printTree();

        System.out.println("\n" + "═".repeat(52));
        System.out.println("РЕЗУЛЬТАТ ОБХОДА: " + tree.preorderIterative());
        System.out.println("═".repeat(52));

        tree.preorderWithStackVisualization();

        System.out.println("\n\n" + "═".repeat(52));
        System.out.println("ПРИМЕР 2");
        System.out.println("═".repeat(52) + "\n");

        String input2 = "1 (2 (0, 4 (7, 8)), 3 (5 (0, 9(11,)), 6 (10, 0)))";
        System.out.println("Входная запись: " + input2);

        tree.parseFromString(input2);

        System.out.println("\nГрафический вид дерева:");
        tree.printTree();

        System.out.println("\n" + "═".repeat(52));
        System.out.println("РЕЗУЛЬТАТ ОБХОДА: " + tree.preorderIterative());
        System.out.println("═".repeat(52));

        tree.preorderWithStackVisualization();
    }
}