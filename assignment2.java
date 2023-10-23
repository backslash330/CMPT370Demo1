// Analyze the resulting tree heights created by two algorithms: a standard binary search 
// tree insertion and an AVL tree insertion. You may select C/C++ or Java as the implementation 
// language, but your choice of language should be consistent for both algorithms. Regarding the 
// experimental method, write your own implementation of each algorithm and compare the
// heights of the resulting trees. If you use code not your own to implement either tree, clearly 
// state this in the methods section of your report. In such a case you will not receive credit for 
// the implementation portion of the assignment. Select appropriate input data sets for your 
// experiment. You may want to refer to the Assignment One instructions for some suggestions.
// Duplicate values should not be inserted into the trees, so either generate data sets guaranteed 
// to have no duplicates or have your insertion functions ignore values if they are already present 
// in the trees

// imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class assignment2 {

    // Main class
    // Runs the experiment
    public static void main(String[] args) {

        // Create the files
        File rawResults = new File("rawResults.txt");
        File averageHeights = new File("averageHeights.csv");

        System.out.println("Program starting");

        // Everything is in a try-catch because the file writer requires it
        try {

            FileWriter rawResultsWriter = new FileWriter(rawResults);
            FileWriter averageHeightsWriter = new FileWriter(averageHeights);
            averageHeightsWriter.write(
                    "Number of Nodes,AVL Height (Random), BST Height (Random), AVL Height (Sorted), BST Height (Sorted), AVL Height (Reverse Sorted), BST Height (Reverse Sorted)\n");
            int avgAVLHeight = 0;
            int avgBSTHeight = 0;

            // Main loop for experiment
            for (int i = 1; i <= 10000; i++) {
                // Random Numbers
                avgAVLHeight = 0;
                avgBSTHeight = 0;
                System.out.println("Testing " + i + "Random nodes");
                for (int j = 0; j < 3; j++) {
                    int[] randomNumbers = generateNumbers(i);
                    avl randomAVL = new avl(randomNumbers);
                    Node randomBST = createBST(randomNumbers);
                    rawResultsWriter.write(
                            "AVL Tree with " + i + " Random nodes has a height of " + randomAVL.root.height + "\n");
                    rawResultsWriter
                            .write("BST Tree with " + i + " Random nodes has a height of " + randomBST.height + "\n");
                    avgAVLHeight += randomAVL.root.height;
                    avgBSTHeight += randomBST.height;
                }
                avgAVLHeight = avgAVLHeight / 3;
                avgBSTHeight = avgBSTHeight / 3;
                averageHeightsWriter.write(i + "," + avgAVLHeight + "," + avgBSTHeight + ",");

                // Sorted Numbers
                avgAVLHeight = 0;
                avgBSTHeight = 0;
                System.out.println("Testing " + i + "Sorted nodes");

                for (int j = 0; j < 3; j++) {
                    int[] sortedNumbers = generateNumbers(i);
                    Arrays.sort(sortedNumbers);
                    avl sortedAVL = new avl(sortedNumbers);
                    Node sortedBST = createBST(sortedNumbers);
                    rawResultsWriter.write(
                            "AVL Tree with " + i + " Sorted nodes has a height of " + sortedAVL.root.height + "\n");
                    rawResultsWriter
                            .write("BST Tree with " + i + " Sorted nodes has a height of " + sortedBST.height + "\n");
                    avgAVLHeight += sortedAVL.root.height;
                    avgBSTHeight += sortedBST.height;
                }
                avgAVLHeight = avgAVLHeight / 3;
                avgBSTHeight = avgBSTHeight / 3;
                averageHeightsWriter.write(avgAVLHeight + "," + avgBSTHeight + ",");

                // Reverse Sorted
                avgAVLHeight = 0;
                avgBSTHeight = 0;
                System.out.println("Testing " + i + "Reverse Sorted nodes");
                for (int j = 0; j < 3; j++) {
                    int[] revSortedNumbers = generateNumbers(i);
                    Arrays.sort(revSortedNumbers);
                    int[] reverseNumbers = new int[revSortedNumbers.length];
                    for (int k = 0; k < revSortedNumbers.length; k++) {
                        reverseNumbers[k] = revSortedNumbers[revSortedNumbers.length - k - 1];
                    }
                    avl revSortedAVL = new avl(reverseNumbers);
                    Node revSortedBST = createBST(reverseNumbers);
                    rawResultsWriter.write("AVL Tree with " + i + " Reverse Sorted nodes has a height of "
                            + revSortedAVL.root.height + "\n");
                    rawResultsWriter.write("BST Tree with " + i + " Reverse Sorted nodes has a height of "
                            + revSortedBST.height + "\n");
                    avgAVLHeight += revSortedAVL.root.height;
                    avgBSTHeight += revSortedBST.height;
                }
                avgAVLHeight = avgAVLHeight / 3;
                avgBSTHeight = avgBSTHeight / 3;
                averageHeightsWriter.write(avgAVLHeight + "," + avgBSTHeight + "\n");

            }
            rawResultsWriter.close();
            averageHeightsWriter.close();
            System.out.println("Program Complete.");
        } catch (IOException e) {
            System.out.println("Then attempting to write the experiment, an error occurred. See stack below:");
            e.printStackTrace();
        }

    }

    // construct a binary search tree consisting of nodes that each stores
    // an integer
    public static Node createBST(int[] numbers) {
        Node root = null;
        for (int i = 0; i < numbers.length; i++) {
            if (root == null) {
                root = new Node(numbers[i]);
            } else {
                Node currentNode = root;
                while (currentNode != null) {
                    if (numbers[i] < currentNode.data) {
                        if (currentNode.left == null) {
                            currentNode.left = new Node(numbers[i]);
                            currentNode.left.parent = currentNode;
                            SetBSTHeights(currentNode.left);
                            // we have created a new leaf node
                            // so we print the path
                            break;
                        } else {
                            currentNode = currentNode.left;
                        }
                    } else if (numbers[i] > currentNode.data) {
                        if (currentNode.right == null) {
                            currentNode.right = new Node(numbers[i]);
                            currentNode.right.parent = currentNode;
                            SetBSTHeights(currentNode.right);
                            // we have created a new leaf node
                            // so we print the path
                            break;
                        } else {
                            currentNode = currentNode.right;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return root;

    }

    // This function recursively goes up the path start
    // at the node that was inserted
    public static void SetBSTHeights(Node node) {
        if (node == null) {
            return;
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        SetBSTHeights(node.parent);
    }

    // Gets the height of the node
    // This method is needed because a node could be null
    // Trying to retrieve the height of a null node will cause an error.
    public static int getHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    // AVL tree class
    // Class was needed to make metadata tracking easier between funcitons
    public static class avl {
        // Metadata
        Node root;
        Node childNode;
        Node grandChildNode;

        // Constructor
        public avl() {
            root = null;
        }

        // Constructor with numbers
        public avl(int[] numbers) {
            root = null;
            childNode = null;
            grandChildNode = null;
            for (int i = 0; i < numbers.length; i++) {
                // This is really the main function of the AVL
                // it is it's own funciton so that recursion vcan be done correctly.
                insert(root, numbers[i]);
            }
        }

        // Same get height method as BST, but within class
        public static int getHeight(Node node) {
            if (node == null) {
                return 0;
            } else {
                return node.height;
            }
        }

        // Sets the balanace value for a node
        // NOTE: This exists for the same issue as getHeight
        // Namely, we do not know if a node is null
        public static int getBalance(Node node) {
            if (node == null) {
                return 0;
            } else {
                return getHeight(node.left) - getHeight(node.right);
            }
        }

        // Sets the parents used in all rotations
        // Used because we do not know if a subtree is null
        public static void setParent(Node subTreeRoot, Node rotationNode) {
            if (subTreeRoot == null) {
                return;
            } else {
                subTreeRoot.parent = rotationNode;
            }
        }

        // public static void SetParentHeights(Node node) {
        // if (node == null) {
        // return;
        // }
        // node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        // SetParentHeights(node.parent);

        // }

        // for left left imbalance
        public void rotateRight(Node c) {
            // C -> B - > A all left children
            Node b = childNode;
            Node a = grandChildNode;
            Node connectingNode = c.parent;

            if (connectingNode != null) {
                if (connectingNode.left == c) {
                    connectingNode.left = b;
                } else {
                    connectingNode.right = b;
                }
            } else {
                root = b;
            }
            Node t1 = a.left;
            Node t2 = a.right;
            Node t3 = b.right;
            Node t4 = c.right;

            // disconnect nodes
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

            // Set parents of subtrees
            setParent(t1, a);
            setParent(t2, a);
            setParent(t3, c);
            setParent(t4, c);

            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            c.height = Math.max(getHeight(c.left), getHeight(c.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;

        }

        // for right right imbalance
        public void rotateLeft(Node a) {
            // A -> B -> C all right children
            Node b = childNode;
            Node c = grandChildNode;

            Node t1 = a.left;
            Node t2 = b.left;
            Node t3 = c.left;
            Node t4 = c.right;

            Node connectingNode = a.parent;
            if (connectingNode != null) {
                if (connectingNode.left == a) {
                    connectingNode.left = b;
                } else {
                    connectingNode.right = b;
                }
            } else {
                root = b;
            }

            // disconnect nodes
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

            // Set parents of subtrees
            setParent(t1, a);
            setParent(t2, a);
            setParent(t3, c);
            setParent(t4, c);

            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            c.height = Math.max(getHeight(c.left), getHeight(c.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;

        }

        // for left right imbalance
        public void rotateLeftRight(Node c) {

            // C A B
            Node a = childNode;
            Node b = grandChildNode;
            Node connectingNode = c.parent;
            Node t1 = a.left;
            Node t2 = b.left;
            Node t3 = b.right;
            Node t4 = c.right;

            if (connectingNode != null) {
                if (connectingNode.left == c) {
                    connectingNode.left = b;
                } else {
                    connectingNode.right = b;
                }
            } else {
                root = b;
            }

            // disconnect nodes
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

            // Set parents of subtrees
            setParent(t1, a);
            setParent(t2, a);
            setParent(t3, c);
            setParent(t4, c);

            // Set Heights
            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            c.height = Math.max(getHeight(c.left), getHeight(c.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;

        }

        // for right left imbalance
        public void rotateRightLeft(Node a) {
            if (a.data == 37) {
                int test = 1;
            }
            // A C B
            Node c = childNode;
            Node b = grandChildNode;
            Node connectingNode = a.parent;
            Node t1 = a.left;
            Node t2 = b.left;
            Node t3 = b.right;
            Node t4 = c.right;

            if (connectingNode != null) {
                if (connectingNode.left == a) {
                    connectingNode.left = b;
                } else {
                    connectingNode.right = b;
                }
            } else {
                root = b;
            }

            // disconnect nodes
            a.left = null;
            a.right = null;
            b.left = null;
            b.right = null;
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

            // Set parents of subtrees
            setParent(t1, a);
            setParent(t2, a);
            setParent(t3, c);
            setParent(t4, c);

            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            c.height = Math.max(getHeight(c.left), getHeight(c.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;
            // SetParentHeights(b.parent);

        }

        // sets the heights of the nodes
        public void setHeights(Node currentNode) {
            if (currentNode == null) {
                return;
            } else {
                // debug: stop on value of 15 or 12
                if (currentNode.data == 15 || currentNode.data == 12) {
                    int test = 1;
                }
                currentNode.height = Math.max(getHeight(currentNode.left), getHeight(currentNode.right)) + 1;
                // Check the balance here
                int balance = getBalance(currentNode);
                if (balance > 1 && grandChildNode.data < childNode.data) {
                    // System.out.println("Left Left Case, rotate right");
                    rotateRight(currentNode);
                }
                if (balance < -1 && grandChildNode.data > childNode.data) {
                    // System.out.println("Right Right Case, rotate left");
                    rotateLeft(currentNode);
                }
                if (balance > 1 && grandChildNode.data > childNode.data) {
                    // System.out.println("Left Right Case, rotate left then right");
                    rotateLeftRight(currentNode);
                }
                if (balance < -1 && grandChildNode.data < childNode.data) {
                    // System.out.println("Right Left Case, rotate right then left");
                    rotateRightLeft(currentNode);
                }

                setPreviousNodeMetadata(currentNode);
                setHeights(currentNode.parent);
            }
        }

        // Maintins the previous node metaData
        public void setPreviousNodeMetadata(Node currentNode) {
            grandChildNode = childNode;
            childNode = currentNode;

        }

        // Called from main function, inserts a number into the AVL tree.
        public void insert(Node currentNode, int number) {
            // Find the location to insert the node
            if (currentNode == null) {
                root = new Node(number);
                return;
            }
            if (number < currentNode.data) {
                if (currentNode.left == null) {
                    currentNode.left = new Node(number);
                    currentNode.left.parent = currentNode;
                    setHeights(currentNode.left);
                    return;
                } else {
                    insert(currentNode.left, number);
                }
            } else if (number > currentNode.data) {
                if (currentNode.right == null) {
                    currentNode.right = new Node(number);
                    currentNode.right.parent = currentNode;
                    setHeights(currentNode.right);
                    return;
                } else {
                    insert(currentNode.right, number);
                }
            }

        }

    }

    // Node structure
    // Note: this is not contained in the AVL so it can be used for BST and AVL
    public static class Node {
        int data, height;
        Node left, right, parent;

        public Node(int number) {
            this.data = number;
            height = 1;
            left = right = parent = null;
        }
    }

    // Generates random numbers from size to size * 2
    public static int[] generateNumbers(int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            // Note: Math.random() returns a double between 0.0 and 1.0.
            numbers[i] = (int) (Math.random() * size * 2);
        }
        return numbers;
    }

    // Print out the nodes using in Order Traversal
    // Was used for debugging
    public static void inOrderTraversal(Node currentNode, int nodeDEpth) {
        // We need to print one line per depth
        // Depth 1, value = 2, left = null, right = 3
        // So need to be careful with recursion
        String outputString = "";
        if (currentNode != null) {
            outputString += "Depth " + nodeDEpth + ", value = " + currentNode.data;
            if (currentNode.left != null) {
                outputString += ", left = " + currentNode.left.data;
            } else {
                outputString += ", left = null";
            }
            if (currentNode.right != null) {
                outputString += ", right = " + currentNode.right.data;
            } else {
                outputString += ", right = null";
            }
            System.out.println(outputString);
            inOrderTraversal(currentNode.left, nodeDEpth + 1);
            inOrderTraversal(currentNode.right, nodeDEpth + 1);
        }
    }

    // Prints the heights for inOrderTraversal
    // Was used for debugging
    public static void inOrderTraversalHeights(Node currentNode, int nodeDepth) {
        // We need to print one line per depth
        // Depth 1, value = 2, left = null, right = 3
        // So need to be careful with recursion
        String outputString = "";
        if (currentNode != null) {
            outputString += "Depth " + nodeDepth + ", value = " + currentNode.data;
            if (currentNode.left != null) {
                outputString += ", left = " + currentNode.left.data;
            } else {
                outputString += ", left = null";
            }
            if (currentNode.right != null) {
                outputString += ", right = " + currentNode.right.data;
            } else {
                outputString += ", right = null";
            }
            outputString += ", height = " + currentNode.height;
            System.out.println(outputString);
            inOrderTraversalHeights(currentNode.left, nodeDepth + 1);
            inOrderTraversalHeights(currentNode.right, nodeDepth + 1);
        }
    }
}
