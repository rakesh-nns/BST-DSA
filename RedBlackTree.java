import java.util.Scanner;

class Node {
    int data;
    Node left, right, parent;
    boolean color; // true = Red, false = Black

    Node(int data) {
        this.data = data;
        left = right = parent = null;
        color = true; // new node is always red
    }
}

public class RedBlackTree {
    private Node root;
    private final boolean RED = true;
    private final boolean BLACK = false;

    // ---------- Left Rotation ----------
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    // ---------- Right Rotation ----------
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null)
            root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    // ---------- Insert ----------
    public void insert(int data) {
        Node node = new Node(data);
        root = bstInsert(root, node);
        fixInsert(node);
    }

    // Normal BST Insert
    private Node bstInsert(Node root, Node node) {
        if (root == null)
            return node;
        if (node.data < root.data) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.data > root.data) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }
        return root;
    }

    // Fix Red-Black violations
    private void fixInsert(Node k) {
        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                Node uncle = k.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    k.parent.color = BLACK;
                    uncle.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                Node uncle = k.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    k.parent.color = BLACK;
                    uncle.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    // ---------- Search ----------
    public boolean search(int key) {
        Node temp = root;
        while (temp != null) {
            if (key == temp.data)
                return true;
            else if (key < temp.data)
                temp = temp.left;
            else
                temp = temp.right;
        }
        return false;
    }

    // ---------- Traversals ----------
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + "(" + (node.color ? "R" : "B") + ") ");
            inorder(node.right);
        }
    }

    public void preorder(Node node) {
        if (node != null) {
            System.out.print(node.data + "(" + (node.color ? "R" : "B") + ") ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.data + "(" + (node.color ? "R" : "B") + ") ");
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

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.data + (node.color ? "(R)" : "(B)"));

        if (node.left != null)
            printTree(node.left, prefix + (isTail ? "    " : "│   "), true);
    }

    // ---------- Main Menu ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        int choice, key;

        do {
            System.out.println("\n--- RED-BLACK TREE OPERATIONS ---");
            System.out.println("1. Insert");
            System.out.println("2. Search");
            System.out.println("3. Inorder Traversal");
            System.out.println("4. Preorder Traversal");
            System.out.println("5. Postorder Traversal");
            System.out.println("6. Display Tree");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value to insert: ");
                    key = sc.nextInt();
                    tree.insert(key);
                    System.out.println("Inserted " + key);
                    break;
                case 2:
                    System.out.print("Enter value to search: ");
                    key = sc.nextInt();
                    System.out.println(tree.search(key) ? "Found!" : "Not Found!");
                    break;
                case 3:
                    System.out.print("Inorder: ");
                    tree.inorder(tree.root);
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Preorder: ");
                    tree.preorder(tree.root);
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Postorder: ");
                    tree.postorder(tree.root);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Tree Structure:");
                    tree.printTree(tree.root);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 7);
        sc.close();
    }
}
