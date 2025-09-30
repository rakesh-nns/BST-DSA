import java.util.*;

public class HeapMenu2 {
    static int[] heap = new int[100];
    static int size = 0;
    static boolean isMinHeap = true; // true = MinHeap, false = MaxHeap

    // Insert element
    static void insert(int val) {
        heap[++size] = val;
        int i = size;
        while (i > 1) {
            int p = i / 2;
            if ((isMinHeap && heap[i] < heap[p]) || (!isMinHeap && heap[i] > heap[p])) {
                int t = heap[i]; heap[i] = heap[p]; heap[p] = t;
                i = p;
            } else break;
        }
    }

    // Delete root
    static void deleteRoot() {
        if (size == 0) { System.out.println("Heap empty"); return; }
        System.out.println("Deleted root: " + heap[1]);
        heap[1] = heap[size--];
        heapify(1);
    }

    // Heapify at index
    static void heapify(int i) {
        int left = 2 * i, right = 2 * i + 1, t = i;
        if (left <= size && ((isMinHeap && heap[left] < heap[t]) || (!isMinHeap && heap[left] > heap[t]))) t = left;
        if (right <= size && ((isMinHeap && heap[right] < heap[t]) || (!isMinHeap && heap[right] > heap[t]))) t = right;
        if (t != i) {
            int temp = heap[i]; heap[i] = heap[t]; heap[t] = temp;
            heapify(t);
        }
    }

    // Build heap from unsorted array
    static void buildHeap(int[] arr, int n) {
        size = n;
        for (int i = 1; i <= n; i++) heap[i] = arr[i - 1];
        for (int i = size / 2; i >= 1; i--) heapify(i);
        System.out.println("Heap built successfully.");
    }

    // Perfect tree printer
    static void printTree() {
        if (size == 0) { System.out.println("Heap empty"); return; }

        int maxLevel = (int)(Math.log(size) / Math.log(2)) + 1;
        int index = 1;

        for (int level = 1; level <= maxLevel; level++) {
            int nodes = (int)Math.pow(2, level - 1);
            int spaceBefore = (int)Math.pow(2, maxLevel - level + 1);
            int spaceBetween = (int)Math.pow(2, maxLevel - level + 2);

            // print nodes with proper centering
            for (int i = 0; i < nodes && index <= size; i++, index++) {
                System.out.print(" ".repeat(spaceBefore) + heap[index] + " ".repeat(spaceBetween));
            }
            System.out.println();

            // print branches exactly under nodes
            if (level < maxLevel) {
                for (int i = 0; i < nodes && (index - nodes + i + 1) * 2 <= size; i++) {
                    System.out.print(" ".repeat(spaceBefore - 1) + "/ \\" + " ".repeat(spaceBetween - 2));
                }
                System.out.println();
            }
        }
    }

    // Main menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ch, val, n;

        do {
            System.out.println("\n--- Heap Menu ---");
            System.out.println("1. Switch to Min Heap");
            System.out.println("2. Switch to Max Heap");
            System.out.println("3. Insert");
            System.out.println("4. Delete Root");
            System.out.println("5. Build Heap from Array");
            System.out.println("6. Display Tree");
            System.out.println("7. Exit");
            System.out.print("Choice: ");
            ch = sc.nextInt();

            switch (ch) {
                case 1: isMinHeap = true; size = 0; System.out.println("Switched to Min Heap."); break;
                case 2: isMinHeap = false; size = 0; System.out.println("Switched to Max Heap."); break;
                case 3: System.out.print("Enter value: "); val = sc.nextInt(); insert(val); break;
                case 4: deleteRoot(); break;
                case 5:
                    System.out.print("Enter number of elements: "); n = sc.nextInt();
                    int[] arr = new int[n];
                    System.out.println("Enter elements:");
                    for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
                    buildHeap(arr, n);
                    break;
                case 6: printTree(); break;
                case 7: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid!");
            }
        } while (ch != 7);
        sc.close();
    }
}
