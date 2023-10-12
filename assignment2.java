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
        Node root = createAVL(numbers);
 

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

    public static Node rotateRight(Node node){
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.parent = node.parent;
        // print out all details
        System.out.println("Rotating right on " + node.data);
        System.out.println("New parent: " + temp.data);
        System.out.println("New left: " + temp.left.data);
        System.out.println("New right: " + temp.right.data);
        return temp;
    }

    public static Node rotateLeft(Node node){
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.parent = node.parent;
        // print out all details
        System.out.println("Rotating left on " + node.data);
        System.out.println("New parent: " + temp.data);
        System.out.println("New left: " + temp.left.data);
        System.out.println("New right: " + temp.right.data);
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
    public static Node createAVL(int[] numbers) {
        Node root = null;
        for (int i = 0; i < numbers.length; i++) {
            if (root == null) {
                root = new Node(numbers[i]);
                root.height = 1;
            } else {
                Node current = root;
                while (current != null) {
                    if (numbers[i] < current.data) {
                        if (current.left == null) {
                            current.left = new Node(numbers[i]);
                            current.left.parent = current;
                            // calcualte hieght, remember to acount for null
                            if (current.right == null){
                                current.height = current.left.height + 1;
                            }
                            else{
                                current.height = Math.max(current.left.height, current.right.height) + 1;
                            }
                            calculateHeights(current.left);
                            // A new leaf node has been created
                            // So we need to check if the tree is balanced
                            Node balancedNode =  checkBalance(current);
                        } else {
                            current = current.left;
                        }
                    } else if (numbers[i] > current.data) {
                        if (current.right == null) {
                            current.right = new Node(numbers[i]);
                            current.right.parent = current;
                            if (current.left == null){
                                current.height = current.right.height + 1;
                            }
                            else{
                                current.height = Math.max(current.left.height, current.right.height) + 1;
                            }
                            calculateHeights(current.right);
                            // A new leaf node has been created
                            // So we need to check if the tree is balanced
                            Node balancedNode =  checkBalance(current);
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

    public static void calculateHeights(Node node){
        // This function should recalculate all heights in the path
        boolean rootFound = false;
        while(!rootFound){
            if (node.parent == null) {
                rootFound = true;
            }
            int left = 0;
            int right = 0;
            if(node.left != null){
                left = node.left.height;
            }
            if(node.right != null){
                right = node.right.height;
            }
            node.height = 1 + left - right;
            node = node.parent;
        }
    }

    public static Node checkBalance(Node node){
        int leftHeight = 0;
        int rightHeight = 0;
        if (node.left != null){
            // System.out.println("Left node found:  " + node.left.height);
            leftHeight = node.left.height;
        }
        if (node.right != null){
            System.out.println("Right node found: " + node.right.height);
            rightHeight = node.right.height;
        }
        // PRINT HEIGHTS
        // System.out.println("Left height: " + leftHeight);
        // System.out.println("Right height: " + rightHeight);
        // if height greater than 1, we have a left left or left right imbalance
        if (leftHeight - rightHeight > 1){
            if (node.data < node.left.data){
                // left left imbalance
                return rotateRight(node);
            }
            else{
                // left right imbalance
                return rotateLeftRight(node);
            }
        }
        // if height less than -1, we have a right right or right left imbalance
        else if (leftHeight - rightHeight < -1){
            if (node.data > node.right.data){
                // right right imbalance
                return rotateLeft(node);
            }
            else{
                // right left imbalance
                return rotateRightLeft(node);
            }
        }
        else{
            // no imbalance
            return node;
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
