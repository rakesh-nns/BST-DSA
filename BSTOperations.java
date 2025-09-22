import java.util.Scanner;

// Node class
class Node {
    int data;
    Node left, right;

    Node(int d) {
        data = d;
        left = right = null;
    }
}

// BST class with all operations
class BST {
    Node root;

    // Insert a node
    Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }
        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }
        return root;
    }

    // Search a node
    boolean search(Node root, int key) {
        if (root == null) return false;
        if (root.data == key) return true;
        if (key < root.data) return search(root.left, key);
        else return search(root.right, key);
    }

    // Delete a node
    Node delete(Node root, int key) {
        if (root == null) return null;

        if (key < root.data) {
            root.left = delete(root.left, key);
        } else if (key > root.data) {
            root.right = delete(root.right, key);
        } else { 
            // Node found
            if (root.left == null) return root.right; 
            else if (root.right == null) return root.left; 
            
            // Node with 2 children: Get inorder successor (min value in right subtree)
            root.data = findMin(root.right);
            root.right = delete(root.right, root.data);
        }
        return root;
    }

    // Inorder Traversal (Left, Root, Right)
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    // Preorder Traversal (Root, Left, Right)
    void preorder(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    // Postorder Traversal (Left, Right, Root)
    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }
}

// Main class
public class BSTOperations {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BST tree = new BST();
        int choice, data;

        do {
            System.out.println("\n--- BST Operations ---");
            System.out.println("1. Insert");
            System.out.println("2. Search");
            System.out.println("3. Delete");
            System.out.println("4. Inorder Traversal");
            System.out.println("5. Preorder Traversal");
            System.out.println("6. Postorder Traversal");
            System.out.println("7. Find Minimum");
            System.out.println("8. Find Maximum");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter data to insert: ");
                    data = sc.nextInt();
                    tree.root = tree.insert(tree.root, data);
                    break;
                case 2:
                    System.out.print("Enter value to search: ");
                    data = sc.nextInt();
                    System.out.println(tree.search(tree.root, data) ? "Found" : "Not Found");
                    break;
                case 3:
                    System.out.print("Enter value to delete: ");
                    data = sc.nextInt();
                    tree.root = tree.delete(tree.root, data);
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
                    System.out.println("Minimum value: " + tree.findMin(tree.root));
                    break;
                case 8:
                    System.out.println("Maximum value: " + tree.findMax(tree.root));
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 9);

        sc.close();
    }
}
