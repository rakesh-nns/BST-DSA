import java.util.*;

class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

public class AVLTreeAllOps2 {
    Node root;

    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

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

        // Left Left
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);
        // Right Right
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);
        // Left Right
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right Left
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
            if (root.left == null || root.right == null) {
                root = (root.left != null) ? root.left : root.right;
            } else {
                Node temp = root.right;
                while (temp.left != null)
                    temp = temp.left;
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getBalance(root);

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
        return (key < node.key) ? search(node.left, key) : search(node.right, key);
    }

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

    void displayTree() {
        if (root == null) {
            System.out.println("Tree is empty!");
            return;
        }
        System.out.println(root.key);
        printTree(root, "");
    }

    void printTree(Node node, String prefix) {
        if (node == null)
            return;

        if (node.left != null || node.right != null) {
            System.out.println(prefix + "│");
            
            if (node.left != null) {
                System.out.println(prefix + "├── " + node.left.key);
                printTree(node.left, prefix + "│   ");
            }
            
            if (node.right != null) {
                System.out.println(prefix + "│");
                System.out.println(prefix + "└── " + node.right.key);
                printTree(node.right, prefix + "    ");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTreeAllOps tree = new AVLTreeAllOps();

        while (true) {
            System.out.println("\n--- AVL TREE ---");
            System.out.println("1.Insert 2.Delete 3.Search 4.Inorder 5.Preorder 6.Postorder 7.Display 8.Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            if (choice == 8) {
                System.out.println("Exiting...");
                break;
            }

            int key;
            switch (choice) {
                case 1:
                    System.out.print("Enter key: ");
                    key = sc.nextInt();
                    tree.root = tree.insert(tree.root, key);
                    System.out.println("Inserted!");
                    break;
                case 2:
                    System.out.print("Enter key: ");
                    key = sc.nextInt();
                    tree.root = tree.delete(tree.root, key);
                    System.out.println("Deleted!");
                    break;
                case 3:
                    System.out.print("Enter key: ");
                    key = sc.nextInt();
                    System.out.println(tree.search(tree.root, key) ? "Found!" : "Not Found!");
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
                    tree.displayTree();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}