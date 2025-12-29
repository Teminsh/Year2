package ADS.Lab15;

public class RecursiveTraversals
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

        // region Прямой обход (Pre-order)

        public String preorder()
        {
            StringBuilder result = new StringBuilder();
            preorderHelper(root, result);
            return result.toString().trim();
        }

        private void preorderHelper(TreeNode node, StringBuilder result)
        {
            if (node != null)
            {
                result.append(node.value).append(" ");
                preorderHelper(node.left, result);
                preorderHelper(node.right, result);
            }
        }

        // endregion

        // region Центральный обход (In-order)

        public String inorder()
        {
            StringBuilder result = new StringBuilder();
            inorderHelper(root, result);
            return result.toString().trim();
        }

        private void inorderHelper(TreeNode node, StringBuilder result)
        {
            if (node != null)
            {
                inorderHelper(node.left, result);
                result.append(node.value).append(" ");
                inorderHelper(node.right, result);
            }
        }

        // endregion

        // region Концевой обход (Post-order)

        public String postorder()
        {
            StringBuilder result = new StringBuilder();
            postorderHelper(root, result);
            return result.toString().trim();
        }

        private void postorderHelper(TreeNode node, StringBuilder result)
        {
            if (node != null)
            {
                postorderHelper(node.left, result);
                postorderHelper(node.right, result);
                result.append(node.value).append(" ");
            }
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

        System.out.println("=== ЛАБА №15: РЕКУРСИВНЫЕ ОБХОДЫ ===\n");

        String input1 = "8 (3 (1, 6 (4,7)), 10 (, 14(13,)))";
        System.out.println("Пример 1: " + input1);
        tree.parseFromString(input1);

        System.out.println("\nГрафический вид:");
        tree.printTree();

        System.out.println("\nПрямой обход (Pre-order):    " + tree.preorder());
        System.out.println("Центральный обход (In-order): " + tree.inorder());
        System.out.println("Концевой обход (Post-order):  " + tree.postorder());

        System.out.println("\n\n" + "=".repeat(50));
        String input2 = "1 (2 (0, 4 (7, 8)), 3 (5 (0, 9(11,)), 6 (10, 0)))";
        System.out.println("Пример 2: " + input2);
        tree.parseFromString(input2);

        System.out.println("\nГрафический вид:");
        tree.printTree();

        System.out.println("\nПрямой обход (Pre-order):    " + tree.preorder());
        System.out.println("Центральный обход (In-order): " + tree.inorder());
        System.out.println("Концевой обход (Post-order):  " + tree.postorder());

        System.out.println("\nОжидаемая последовательность: 1 2 4 7 8 3 5 9 11 6 10");
    }
}
