import java.util.Scanner;

class Node {
    int data;
    Node left, right, parent;
    boolean color; // true = Red, false = Black

    Node(int data) {
        this.data = data;
        color = true; // New nodes are red by default
    }
}

public class RedBlackTree4 {
    public Node root;
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

    // ---------- BST Insert ----------
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

    // ---------- Insert with Fix ----------
    public void insert(int data) {
        Node node = new Node(data);
        root = bstInsert(root, node);
        fixInsert(node);
    }

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
    public Node searchNode(Node root, int key) {
        if (root == null || root.data == key)
            return root;
        if (key < root.data)
            return searchNode(root.left, key);
        return searchNode(root.right, key);
    }

    // ---------- Delete ----------
    public void delete(int data) {
        Node node = searchNode(root, data);
        if (node == null) {
            System.out.println("Node not found!");
            return;
        }
        deleteNode(node);
        System.out.println("Deleted " + data);
    }

    private void deleteNode(Node z) {
        Node y = z;
        Node x;
        boolean yOriginalColor = y.color;

        if (z.left == null) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z)
                if (x != null)
                    x.parent = y;
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == BLACK && x != null)
            fixDelete(x);
    }

    private void transplant(Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        if (v != null)
            v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private void fixDelete(Node x) {
        while (x != root && (x == null || x.color == BLACK)) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w != null && w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if ((w.left == null || w.left.color == BLACK) &&
                    (w.right == null || w.right.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right == null || w.right.color == BLACK) {
                        if (w.left != null)
                            w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.right != null)
                        w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w != null && w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if ((w.right == null || w.right.color == BLACK) &&
                    (w.left == null || w.left.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left == null || w.left.color == BLACK) {
                        if (w.right != null)
                            w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    if (w.left != null)
                        w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        if (x != null)
            x.color = BLACK;
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
        RedBlackTree4 tree = new RedBlackTree4();
        int choice, key;

        do {
            System.out.println("\n--- RED-BLACK TREE OPERATIONS ---");
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("4. Display Tree");
            System.out.println("5. Exit");
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
                    System.out.print("Enter value to delete: ");
                    key = sc.nextInt();
                    tree.delete(key);
                    break;
                case 3:
                    System.out.print("Enter value to search: ");
                    key = sc.nextInt();
                    System.out.println(tree.searchNode(tree.root, key) != null ? "Found!" : "Not Found!");
                    break;
                case 4:
                    System.out.println("\nTree Structure:");
                    tree.printTree(tree.root);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
        sc.close();
    }
}
