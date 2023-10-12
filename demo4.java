// When a new leaf node is created list all 
// the nodes in the path from the newly added leaf node to the root of the tree. It is sufficient to 
// list only the value the node holds since duplication is not permitted. When listing each node 
// also print the next two children in the path from the root to the newly added leaf node.
public class demo4 {
    
    public static class Node {
        int data;
        int height;
        Node left, right, parent;

        public Node(int data) {
            this.data = data;
            left = right = parent = null;
        }
    }

    public static void main(String[] args) {
        // Generate numbers
        // System.out.println("Generating 5 numbers...");
        // int[] numbers = generateNumbers(5);

        // Use specific array
        //14, 35, 2, 3, 39,  27, 37
        int[] numbers = {14, 35, 2, 3, 39, 27, 37};

        // print numbers to confirm
        // for (int i = 0; i < numbers.length; i++) {
        //     System.out.println(numbers[i]);
        // }

        // Create tree
        System.out.println("=== BSP Tree ===");
        Node root = createBST(numbers);

        //TEST: Find tail of tree
        Node tail = root;

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
                            // we have created a new leaf node
                            // so we print the path
                            printPath(current.left);
                            break;
                        } else {
                            current = current.left;
                        }
                    } else if (numbers[i] > current.data) {
                        if (current.right == null) {
                            current.right = new Node(numbers[i]);
                            current.right.parent = current;
                            // we have created a new leaf node
                            // so we print the path
                            printPath(current.right);
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

    // When a new leaf node is created list all 
    // the nodes in the path from the newly added leaf node to the root of the tree. It is sufficient to 
    // list only the value the node holds since duplication is not permitted. When listing each node 
    // also print the next two children in the path from the root to the newly added leaf node.
    public static void printPath(Node node) {
        // print the nodes until the parent is null
        System.out.println("Path: ");
        boolean rootFound = false;
        while (!rootFound) {
            if (node.parent == null) {
                rootFound = true;
            }
            String left = "";
            String right = "";
            if (node.left != null) {
                left = String.valueOf(node.left.data);
            }
            else {
                left = "null";
            }
            if (node.right != null) {
                right = String.valueOf(node.right.data);
            }
            else {
                right = "null";
            }
            System.out.println(node.data + "->" + left + "->" + right);
            node = node.parent;
        }
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
