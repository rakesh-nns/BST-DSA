class Node {
    int data;
    Node left, right, parent;
    boolean color; // true = Red, false = Black

    Node(int data) {
        this.data = data;
        color = true; // new node is red
    }
}

public class RBSpotQuestions {
    Node root;
    final boolean RED = true;
    final boolean BLACK = false;

    // ---------------- Basic Rotations ----------------
    void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null) root = x;
        else if (y == y.parent.left) y.parent.left = x;
        else y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    // Double rotation examples (for viva)
    void leftRightRotate(Node z) {
        leftRotate(z.left);
        rightRotate(z);
    }

    void rightLeftRotate(Node z) {
        rightRotate(z.right);
        leftRotate(z);
    }

    // ---------------- BST Insert ----------------
    Node bstInsert(Node root, Node node) {
        if (root == null) return node;
        if (node.data < root.data) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.data > root.data) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }
        return root;
    }

    // ---------------- Fix violations ----------------
    void fixInsert(Node k) {
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

    void insert(int data) {
        Node node = new Node(data);
        root = bstInsert(root, node);
        fixInsert(node);
    }

    // ---------------- Traversals ----------------
    void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.data + (node.color ? "(R) " : "(B) "));
        inorder(node.right);
    }

    // ---------------- Count Red and Black Nodes ----------------
    int countRed(Node node) {
        if (node == null) return 0;
        return (node.color == RED ? 1 : 0) + countRed(node.left) + countRed(node.right);
    }

    int countBlack(Node node) {
        if (node == null) return 0;
        return (node.color == BLACK ? 1 : 0) + countBlack(node.left) + countBlack(node.right);
    }

    // ---------------- Find Height and Black Height ----------------
    int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    int blackHeight(Node node) {
        if (node == null) return 0;
        int left = blackHeight(node.left);
        int right = blackHeight(node.right);
        return (node.color == BLACK ? 1 : 0) + Math.max(left, right);
    }

    // ---------------- Pretty Print ----------------
    void printTree(Node node, String prefix, boolean isTail) {
        if (node == null) return;

        if (node.right != null) printTree(node.right, prefix + (isTail ? "│   " : "    "), false);
        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.data + (node.color ? "(R)" : "(B)"));
        if (node.left != null) printTree(node.left, prefix + (isTail ? "    " : "│   "), true);
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        RBSpotQuestions rbt = new RBSpotQuestions();
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int choice, val;

        do {
            System.out.println("\n--- Red-Black Tree Spot Operations ---");
            System.out.println("1. Insert");
            System.out.println("2. Display Tree");
            System.out.println("3. Count Red Nodes");
            System.out.println("4. Count Black Nodes");
            System.out.println("5. Height of Tree");
            System.out.println("6. Black Height");
            System.out.println("7. Inorder Traversal");
            System.out.println("8. Double Rotation Demo");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value: ");
                    val = sc.nextInt();
                    rbt.insert(val);
                    break;
                case 2:
                    System.out.println("Tree:");
                    rbt.printTree(rbt.root, "", true);
                    break;
                case 3:
                    System.out.println("Red Nodes = " + rbt.countRed(rbt.root));
                    break;
                case 4:
                    System.out.println("Black Nodes = " + rbt.countBlack(rbt.root));
                    break;
                case 5:
                    System.out.println("Height = " + rbt.height(rbt.root));
                    break;
                case 6:
                    System.out.println("Black Height = " + rbt.blackHeight(rbt.root));
                    break;
                case 7:
                    System.out.print("Inorder: ");
                    rbt.inorder(rbt.root);
                    System.out.println();
                    break;
                case 8:
                    System.out.println("Double Rotations defined separately: leftRightRotate() & rightLeftRotate()");
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid!");
            }
        } while (choice != 9);
        sc.close();
    }
}
