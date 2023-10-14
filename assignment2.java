//c Compare binary search tree to AVL tree (insertions)


// Issue with this implementation: when the root is updated, the root is not updated in the main function
// Potentail solution: rewrite AVL as it's own class. This will allow the AVL to have it's own root

public class assignment2 {
    
    public static class Node {
        int data, height, balanceFator;
        Node left, right, parent;

        public Node(int data) {
            this.data = data;
            left = right = parent = null;
            height = 1;
        }
    }

    public static void main(String[] args) {
        // Generate numbers
        // System.out.println("Generating 5 numbers...");
        // int[] numbers = generateNumbers(5);
        // // print numbers to confirm
        // for (int i = 0; i < numbers.length; i++) {
        //     System.out.println(numbers[i]);
        // }

        // Try specific array as test
        //int[] numbers = { 17, 7, 42, 4, 8, 29, 88, 30,}; // no imbalance
      //   int[] numbers = { 17, 7, 42, 4, 8, 29, 88, 30, 33}; //right right imbalance
       //  int[] numbers = { 17, 7, 42, 4, 8, 29, 88, 30, 3, 2}; //left left imbalance
      //  int[] numbers = { 17, 7, 42, 4, 8, 29, 88, 27, 28}; //left right imbalance

        //issue array
        int[] numbers = {7,8,7,9,0};
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
                    System.out.print("inserting " + numbers[i] + " at " + current.data + " \n");
                    if (numbers[i] < current.data) {
                        if (current.left == null) {
                            current.left = new Node(numbers[i]);
                            current.left.parent = current;
                            calculateHeights(current.left);
                            break;
                            // A new leaf node has been created
                            // So we need to check if the tree is balanced
                         //   Node balancedNode =  checkBalance(current);
                        } else {
                            current = current.left;
                        }
                    } else if (numbers[i] > current.data) {
                        if (current.right == null) {
                            current.right = new Node(numbers[i]);
                            current.right.parent = current;
                            calculateHeights(current.right);
                            // A new leaf node has been created
                            // So we need to check if the tree is balanced
                         //   Node balancedNode =  checkBalance(current);
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
       // System.out.println("Calculating heights...");
        // This function should recalculate all heights in the path
        // We also need to keep track of the previous 3 nodes in the path
        // If the height diffence is greater than 1, we need to rotate
        Node prev1 = null;
        Node prev2 = null;
        Node prev3 = null;
        boolean rootFound = false;
        Node current = node;
        while (!rootFound){
            Node parent = current.parent;
            if (current.parent == null){
                rootFound = true;
            }
            if (current.left == null && current.right != null){
                current.height = current.right.height + 1;
            }
            if (current.right == null && current.left != null){
                current.height = current.left.height + 1;
            }
            if (current.left != null && current.right != null){
                current.height = Math.max(current.left.height, current.right.height) + 1;
            }
            if (current.left == null && current.right == null){
                current.height = 1;
            }

            // Set previous nodes
            prev3 = prev2;
            prev2 = prev1;
            prev1 = current;

            // if (prev3 != null){
            //     System.out.println("Prev 3: " + prev3.data);
            //     System.out.println("Prev 3 height: " + prev3.height);
            //     System.out.println("Prev 2: " + prev2.data);
            //     System.out.println("Prev 2 height: " + prev2.height);
            //     System.out.println("Prev 1: " + prev1.data);
            //     System.out.println("Prev 1 height: " + prev1.height);
            // }

            // check balance for each prev node
            // height of the left subtree minus the height of the right subtree
            int balanceFactor = 0;
            balanceFactor = checkBalance(prev3, prev2, prev1);
            //System.out.println("Balance factor 3: " + balanceFactor);

            // if we have a balance factor, we have rotated we can break as we never rotate twice in a row
            if (balanceFactor == 1){
                break;
            }


            if (current.parent == null){
                rootFound = true;
            }
            else {
                current = current.parent;
            }
        }

    }

    public static int checkBalance(Node grandChild, Node child, Node node){
        
        if (grandChild == null){
            return 0;
        }
        int leftHeight = 0;
        int rightHeight = 0;
        if (node.left != null){
            leftHeight = node.left.height;
        }
        if (node.right != null){
            rightHeight = node.right.height;
        }
        // System.out.println("Node: " + node.data);
        // System.out.println("Left height: " + leftHeight);
        // System.out.println("Right height: " + rightHeight);
        // System.out.println("balance factor is " + (leftHeight - rightHeight));

        if (leftHeight - rightHeight > 1){
            // the if statements should not use node.data compared to node.left.data/node.right.data
            // we should be using grandChild.data compared to child.data compared to node.data
            if (grandChild.data < child.data){
                // left left imbalance
                // C B A
                System.out.println("Left left imbalance");
                        System.out.println("Node: " + grandChild.data);
        System.out.println("Path parent: " + child.data);
        System.out.println("Path grandparent: " + node.data);
                // rotate right
                rotateRight(grandChild, child, node);

            }
            else{
                // left right imbalance
                // C A B
                System.out.println("Left right imbalance");
                // rotate left
                rotateLeftRight(grandChild, child, node);

            }
            return 1;
        }
        // if height less than -1, we have a right right or right left imbalance
        else if (leftHeight - rightHeight < -1){
            if (grandChild.data > child.data){
                // right right imbalance
                // A B C
                System.out.println("Right right imbalance");
                // rotate left
                rotateLeft(grandChild, child, node);
                
            
            }
            else{
                // right left imbalance
                // A C B
                System.out.println("Right left imbalance");
                // rotate right
              //  rotateRightLeft(node, pathParent, pathGrandparent);
                
            }
            return 1;
        }
        else{
            // no imbalance
            return 0;
        }


    }

    // for left left imbalance
    public static void rotateRight(Node a, Node b, Node c){
        // C -> B - > A all left children
        // print nodes for confirmation
        System.out.println("Rotating right...");
        System.out.println("A: " + a.data + " height: " + a.height + " left: " + a.left + " right: " + a.right);
        System.out.println("B: " + b.data + " height: " + b.height + " left: " + b.left + " right: " + b.right);
        System.out.println("C: " + c.data + " height: " + c.height + " left: " + c.left + " right: " + c.right);
        Node connectingNode = c.parent;
        // determine if c is the left or right child of connecting node

        if (connectingNode != null){
            if (connectingNode.left == c){
                connectingNode.left = b;
            }
            else{
                connectingNode.right = b;
            }
        }
        Node t1 = a.left;
        Node t2 = a.right;
        Node t3 = b.right;
        Node t4 = c.right;

        //disconnect nodes
        a.left = null;
        a.right = null;
        b.right = null;
        b.left = null;
        c.right = null;
        c.parent = null;

        // rotate b to top
        b.left = a;
        b.right = c;
        b.parent = connectingNode;
        

        a.left = t1;
        a.right = t2;
        a.parent = b;

        c.left = t3;
        c.right = t4;
        c.parent = b;
        c.height = c.height - 1;

        // print out a b c to confirm
        // System.out.println("A: " + a.data + " height: " + a.height + " left: " + a.left + " right: " + a.right);
        // System.out.println("B: " + b.data + " height: " + b.height + " left: " + b.left.data + " right: " + b.right.data);
        // System.out.println("C: " + c.data + " height: " + c.height + " left: " + c.left + " right: " + c.right);



    }

    // for right right imbalance
    public static void rotateLeft(Node c, Node b, Node a){
        // print nodes for confirmation
        System.out.println("Rotating left...");
        System.out.println("A: " + a.data + " height: " + a.height + " left: " + a.left + " right: " + a.right);
        System.out.println("B: " + b.data + " height: " + b.height + " left: " + b.left + " right: " + b.right);
        System.out.println("C: " + c.data + " height: " + c.height + " left: " + c.left + " right: " + c.right);


        // A B C
        Node t1 = a.left;
        Node t2 = b.left;
        Node t3 = c.left;
        Node t4 = c.right;

        // Find out if a was the left or right child of connecting node
        //if there is a parent
        Node connectingNode = a.parent;
        if (connectingNode != null){
            if (connectingNode.left == a){
                connectingNode.left = b;
            }
            else{
                connectingNode.right = b;
            }
        }
        

        //disconnect nodes
        a.left = null;
        b.left = null;
        c.right = null;
        c.left = null;
        c.parent = null;

        // rotate b to top
        b.left = a;
        b.right = c;
        b.parent = connectingNode;
        

        a.left = t1;
        a.right = t2;
        a.parent = b;

        c.left = t3;
        c.right = t4;
        c.parent = b;
        a.height = a.height - 1;

        System.out.println("Rotation complete...");
        System.out.println("A: " + a.data + " height: " + a.height + " left: " + a.left + " right: " + a.right);
        System.out.println("B: " + b.data + " height: " + b.height + " left: " + b.left.data + " right: " + b.right.data);
        System.out.println("C: " + c.data + " height: " + c.height + " left: " + c.left + " right: " + c.right);

    }

    // for left right imbalance
    public static void rotateLeftRight(Node b, Node a, Node c){

        // C A B
        Node connectingNode = c.parent;
        Node t1 = a.left;
        Node t2 = b.left;
        Node t3 = b.right;
        Node t4 = c.right;

        // Find out if a was the left or right child of connecting node
        if (connectingNode != null){
            if (connectingNode.left == c){
                connectingNode.left = b;
            }
            else{
                connectingNode.right = b;
            }
     }

        //disconnect nodes
        a.left = null;
        b.left = null;
        b.right = null;
        c.right = null;
        c.parent = null;

        // rotate b to top
        b.left = a;
        b.right = c;
        b.parent = connectingNode;
        

        a.left = t1;
        a.right = t2;
        a.parent = b;

        c.left = t3;
        c.right = t4;
        c.parent = b;
        c.height = a.height;
        b.height = c.height + 1;

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
            outputString += "Depth " + depth + ", value = " + node.data + " Height: " + node.height;
            if (node.left != null) {
                outputString += ", left = " + node.left.data  + " left height: " + node.left.height;
            } else {
                outputString += ", left = null"  ;
            }
            if (node.right != null) {
                outputString += ", right = " + node.right.data + " right height: " + node.right.height;
            } else {
                outputString += ", right = null" ;
            }
            System.out.println(outputString);
            inOrderTraversal(node.left, depth + 1);
            inOrderTraversal(node.right, depth + 1);
        }

        // The depth should be no more than log2(n) + 1
        // our n for testing is 100
        // log2(100) + 1 = 7.64385618977
        if (depth > 8){
            System.out.println("Depth is greater than log2(n) + 1");
        }
    }
}
