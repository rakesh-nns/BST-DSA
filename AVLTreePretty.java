import java.util.*;

class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

public class AVLTreePretty {
    Node root;

    // ---------- Supporting ----------
    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // ---------- Rotations ----------
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // ---------- Main Operations ----------
    Node insert(Node node, int key) {
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Rotations
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    Node delete(Node root, int key) {
        if (root == null)
            return root;

        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;
                root = (temp == null) ? null : temp;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getBalance(root);

        // Balance checks
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    boolean search(Node node, int key) {
        if (node == null)
            return false;
        if (node.key == key)
            return true;
        if (key < node.key)
            return search(node.left, key);
        else
            return search(node.right, key);
    }

    // ---------- Traversals ----------
    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    void preorder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.key + " ");
        }
    }

    // ---------- Pretty Tree Print ----------
    void printTree(Node root) {
        printTree(root, "", true);
    }

    void printTree(Node node, String prefix, boolean isTail) {
        if (node == null)
            return;

        if (node.right != null)
            printTree(node.right, prefix + (isTail ? "│   " : "    "), false);

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.key);

        if (node.left != null)
            printTree(node.left, prefix + (isTail ? "    " : "│   "), true);
    }

    // ---------- Extra Spot Operations ----------
    int findMin(Node root) {
        if (root == null) return -1;
        while (root.left != null)
            root = root.left;
        return root.key;
    }

    int findMax(Node root) {
        if (root == null) return -1;
        while (root.right != null)
            root = root.right;
        return root.key;
    }

    int countNodes(Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    int findHeight(Node root) {
        if (root == null) return 0;
        return 1 + Math.max(findHeight(root.left), findHeight(root.right));
    }

    boolean isIdentical(Node a, Node b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return (a.key == b.key) &&
                isIdentical(a.left, b.left) &&
                isIdentical(a.right, b.right);
    }

    void printBalanceFactors(Node root) {
        if (root == null) return;
        System.out.println("Node " + root.key + " → Balance Factor: " + getBalance(root));
        printBalanceFactors(root.left);
        printBalanceFactors(root.right);
    }

    // ---------- Main Menu ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTreePretty tree = new AVLTreePretty();
        AVLTreePretty tree2 = new AVLTreePretty();
        int choice, key;

        do {
            System.out.println("\n--- AVL TREE OPERATIONS ---");
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("4. Inorder Traversal");
            System.out.println("5. Preorder Traversal");
            System.out.println("6. Postorder Traversal");
            System.out.println("7. Display Tree");
            System.out.println("8. Find Min & Max");
            System.out.println("9. Count Nodes");
            System.out.println("10. Find Height");
            System.out.println("11. Check Identical Trees");
            System.out.println("12. Print Balance Factors");
            System.out.println("13. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key to insert: ");
                    key = sc.nextInt();
                    tree.root = tree.insert(tree.root, key);
                    System.out.println("Inserted " + key);
                    break;
                case 2:
                    System.out.print("Enter key to delete: ");
                    key = sc.nextInt();
                    tree.root = tree.delete(tree.root, key);
                    System.out.println("Deleted " + key);
                    break;
                case 3:
                    System.out.print("Enter key to search: ");
                    key = sc.nextInt();
                    System.out.println(tree.search(tree.root, key) ? "Key found!" : "Key not found!");
                    break;
                case 4:
                    System.out.print("Inorder: ");
                    tree.inorder(tree.root);
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Preorder: ");
                    tree.preorder(tree.root);
                    System.out.println();
                    break;
                case 6:
                    System.out.print("Postorder: ");
                    tree.postorder(tree.root);
                    System.out.println();
                    break;
                case 7:
                    System.out.println("\nTree Structure:");
                    tree.printTree(tree.root);
                    break;
                case 8:
                    System.out.println("Minimum: " + tree.findMin(tree.root));
                    System.out.println("Maximum: " + tree.findMax(tree.root));
                    break;
                case 9:
                    System.out.println("Total nodes: " + tree.countNodes(tree.root));
                    break;
                case 10:
                    System.out.println("Height of tree: " + tree.findHeight(tree.root));
                    break;
                case 11:
                    System.out.println("Enter elements for second tree (type -1 to stop):");
                    while (true) {
                        int val = sc.nextInt();
                        if (val == -1) break;
                        tree2.root = tree2.insert(tree2.root, val);
                    }
                    System.out.println(tree.isIdentical(tree.root, tree2.root)
                            ? "Both trees are identical!" : "Trees are NOT identical!");
                    break;
                case 12:
                    tree.printBalanceFactors(tree.root);
                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 13);

        sc.close();
    }
}
