//c Compare binary search tree to AVL tree (insertions)


public class assignment2 {
    
    public static class Node {
        int data, height;
        Node left, right, parent;

        public Node(int data) {
            this.data = data;
            left = right = parent = null;
            height = 0;
        }
    }

    public static void main(String[] args) {
        // Generate numbers
        System.out.println("Generating 5 numbers...");
        int[] numbers = generateNumbers(5);
        // print numbers to confirm
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }

        // Create tree
        System.out.println("=== BSP Tree ===");
        Node root = createBST(numbers);

        // Create AVL tree
        System.out.println("=== AVL Tree ===");
        Node rootAVL = createAVL(numbers);

        // Print Tree
        inOrderTraversal(root, 1);
    }

    public static int[] generateNumbers(int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            // Note: Math.random() returns a double between 0.0 and 1.0.
            numbers[i] = (int) (Math.random() * size * 2);
        }
        return numbers;
    }

    // Create an AVL Tree
    public static Node createAVL(int [] numbers){

    }

    public static Node rotateRight(Node node){
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.parent = node.parent;
        return temp;
    }

    public static Node rotateLeft(Node node){
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.parent = node.parent;
        return temp;
    }

    public static Node rotateLeftRight(Node node){
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    public static Node rotateRightLeft(Node node){
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }


    // construct a binary search tree consisting of nodes that each stores
    // an integer
    public static Node createBST(int[] numbers) {
        Node root = null;
        for (int i = 0; i < numbers.length; i++) {
            if (root == null) {
                root = new Node(numbers[i]);
            } else {
                Node current = root;
                while (current != null) {
                    if (numbers[i] < current.data) {
                        if (current.left == null) {
                            current.left = new Node(numbers[i]);
                            current.left.parent = current;
                            break;
                        } else {
                            current = current.left;
                        }
                    } else if (numbers[i] > current.data) {
                        if (current.right == null) {
                            current.right = new Node(numbers[i]);
                            current.right.parent = current;
                            break;
                        } else {
                            current = current.right;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return root;
    }

    // When you print out your tree via an in-order traversal include the stored
    // integer, the depth of the
    // node, and an indication of the left and right subtrees (just the values in
    // the left and right nodes
    // is fine).
    public static void inOrderTraversal(Node node, int depth) {
        // We need to print one line per depth
        // Depth 1, value = 2, left = null, right = 3
        // So need to be careful with recursion
        String outputString = "";
        if (node != null) {
            outputString += "Depth " + depth + ", value = " + node.data;
            if (node.left != null) {
                outputString += ", left = " + node.left.data;
            } else {
                outputString += ", left = null";
            }
            if (node.right != null) {
                outputString += ", right = " + node.right.data;
            } else {
                outputString += ", right = null";
            }
            System.out.println(outputString);
            inOrderTraversal(node.left, depth + 1);
            inOrderTraversal(node.right, depth + 1);
        }
    }
}
