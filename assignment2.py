# REMEMBER THAT IS CHECKS MEMORY EQUALITY, == CHECKS VALUE EQUALITY!

# imports
import random
import time
import sys


def main():
    # Recursion limit must be overridden or program will fail on random sort of size 997 quicksort
    # sys.setrecursionlimit(1000000)
    rawFile = open("rawresultsAssignment2.txt", "w")
    avgFile = open("avgResultsAssignment2.csv", "w")

    # Write header
    avgFile.write(
        "Number of Nodes,AVL Height (Random), BST Height (Random), AVL Height (Sorted), BST Height (Sorted), AVL Height (Reverse Sorted), BST Height (Reverse Sorted)\n"
    )

    # Now I run each test for each array size between 1 and 10000
    # Note: Python is not inclusive, therefore 1 - 1000
    for arraySize in range(1, 10101, 100):
        experimentLoop(arraySize, 1, rawFile, avgFile)
        experimentLoop(arraySize, 2, rawFile, avgFile)
        experimentLoop(arraySize, 3, rawFile, avgFile)
    rawFile.close()
    avgFile.close()


# The main experiments are runa nd averaged here
def experimentLoop(arraySize, experimentType, rawFile, avgFile):
    typeString = ""
    if experimentType == 1:
        typeString = "Random"
    if experimentType == 2:
        typeString = "Sorted"
    if experimentType == 3:
        typeString = "Reverse Sorted"
    print("Testing insertion of " + str(arraySize) + " " + typeString + " nodes...")
    # Set variables
    avgAVLHeight = 0
    avgBSTHeight = 0

    # run three exp of each type to avg
    for experimentNumber in range(1, 4):
        numbers = [random.randint(0, 2 * arraySize) for i in range(arraySize)]
        if experimentType == 2:
            numbers.sort()
        if experimentType == 3:
            numbers.sort(reverse=True)
        myBST = BST(numbers)
        myAVL = AVL(numbers)
        rawFile.write(
            "AVL Tree with "
            + str(arraySize)
            + " Sorted nodes has a height of "
            + str(myAVL)
            + "\n"
        )
        rawFile.write(
            "BST Tree with "
            + str(arraySize)
            + " Sorted nodes has a height of "
            + str(myBST)
            + "\n"
        )
        avgAVLHeight += myAVL.root.height
        avgBSTHeight += myBST.root.height

    # exp done, write results
    avgAVLHeight = str(int(avgAVLHeight / 3))
    avgBSTHeight = str(int(avgBSTHeight / 3))
    if experimentType == 1:
        avgFile.write(str(arraySize) + "," + avgAVLHeight + "," + avgBSTHeight + ",")
    if experimentType == 2:
        avgFile.write(avgAVLHeight + "," + avgBSTHeight + ",")
    if experimentType == 3:
        avgFile.write(avgAVLHeight + "," + avgBSTHeight + "\n")


class Node:
    def __init__(self, number=None):
        self.data = number
        self.height = 1
        self.left = None
        self.right = None
        self.parent = None


class BST:
    def __str__(self) -> str:
        return str(self.root.height)

    def __init__(self, numbers):
        self.root = Node()
        for number in numbers:
            if not self.root.data:
                self.root = Node(number)
            currentNode = self.root
            while currentNode:
                if number < currentNode.data:
                    if not currentNode.left:
                        currentNode.left = Node(number)
                        currentNode.left.parent = currentNode
                        self.setNodeHeight(currentNode.left)
                        break
                    else:
                        currentNode = currentNode.left
                elif number > currentNode.data:
                    if not currentNode.right:
                        currentNode.right = Node(number)
                        currentNode.right.parent = currentNode
                        self.setNodeHeight(currentNode.right)
                        break
                    else:
                        currentNode = currentNode.right
                else:
                    break

    # This can not be a recusive loop due to how python does tail recursion!
    # Namely, it doesn't so it can have proper tracing
    # the solution is a while loop, which eliminates recursion issues.
    def setNodeHeight(self, currentNode: Node):
        while currentNode:
            currentNode.height = (
                max(
                    self.getNodeHeight(currentNode.left),
                    self.getNodeHeight(currentNode.right),
                )
                + 1
            )
            currentNode = currentNode.parent

    def getNodeHeight(self, currentNode: Node):
        if not currentNode:
            return 0
        else:
            return currentNode.height


class AVL:
    def __str__(self) -> str:
        return str(self.root.height)

    def __init__(self, numbers):
        self.root = None
        self.childNode = None
        self.grandChildNode = None
        for number in numbers:
            if not self.root:
                self.root = Node(number)
            currentNode = self.root
            while currentNode:
                if number < currentNode.data:
                    if not currentNode.left:
                        currentNode.left = Node(number)
                        currentNode.left.parent = currentNode
                        self.setNodeHeight(currentNode.left)
                        break
                    else:
                        currentNode = currentNode.left
                elif number > currentNode.data:
                    if not currentNode.right:
                        currentNode.right = Node(number)
                        currentNode.right.parent = currentNode
                        self.setNodeHeight(currentNode.right)
                        break
                    else:
                        currentNode = currentNode.right
                else:
                    break

    # This doesn't run into the same recusion issue due to the rotations fixing the height problem!
    def setNodeHeight(self, currentNode):
        if not currentNode:
            return
        currentNode.height = (
            max(
                self.getNodeHeight(currentNode.left),
                self.getNodeHeight(currentNode.right),
            )
            + 1
        )
        balance = self.getNodeBalance(currentNode)
        if self.grandChildNode and self.childNode:
            if balance > 1 and self.grandChildNode.data < self.childNode.data:
                self.rotateRight(currentNode)
            if balance < -1 and self.grandChildNode.data > self.childNode.data:
                self.rotateLeft(currentNode)
            if balance > 1 and self.grandChildNode.data > self.childNode.data:
                self.rotateLeftRight(currentNode)
            if balance < -1 and self.grandChildNode.data < self.childNode.data:
                self.rotateRightLeft(currentNode)
        self.grandChildNode = self.childNode
        self.childNode = currentNode
        self.setNodeHeight(currentNode.parent)

    def getNodeHeight(self, currentNode: Node):
        if not currentNode:
            return 0
        return currentNode.height

    def getNodeBalance(self, currentNode: Node):
        if not currentNode:
            return 0
        return self.getNodeHeight(currentNode.left) - self.getNodeHeight(
            currentNode.right
        )

    def rotateRight(self, nodeC: Node):
        nodeB = self.childNode
        nodeA = self.grandChildNode
        connectingNode = nodeC.parent

        if connectingNode:
            if connectingNode.left == nodeC:
                connectingNode.left = nodeB
            else:
                connectingNode.right = nodeB
        else:
            self.root = nodeB

        nodeT1 = nodeA.left
        nodeT2 = nodeA.right
        nodeT3 = nodeB.right
        nodet4 = nodeC.right

        self.performRotation(
            nodeA, nodeB, nodeC, nodeT1, nodeT2, nodeT3, nodet4, connectingNode
        )

    def rotateLeft(self, nodeA: Node):
        nodeB = self.childNode
        nodeC = self.grandChildNode
        connectingNode = nodeA.parent

        if connectingNode:
            if connectingNode.left == nodeA:
                connectingNode.left = nodeB
            else:
                connectingNode.right = nodeB
        else:
            self.root = nodeB

        nodeT1 = nodeA.left
        nodeT2 = nodeB.left
        nodeT3 = nodeC.left
        nodet4 = nodeC.right

        self.performRotation(
            nodeA, nodeB, nodeC, nodeT1, nodeT2, nodeT3, nodet4, connectingNode
        )

    def rotateLeftRight(self, nodeC: Node):
        nodeA = self.childNode
        nodeB = self.grandChildNode
        connectingNode = nodeC.parent

        if connectingNode:
            if connectingNode.left == nodeC:
                connectingNode.left = nodeB
            else:
                connectingNode.right = nodeB
        else:
            self.root = nodeB

        nodeT1 = nodeA.left
        nodeT2 = nodeB.left
        nodeT3 = nodeB.right
        nodet4 = nodeC.right

        self.performRotation(
            nodeA, nodeB, nodeC, nodeT1, nodeT2, nodeT3, nodet4, connectingNode
        )

    def rotateRightLeft(self, nodeA: Node):
        nodeC = self.childNode
        nodeB = self.grandChildNode
        connectingNode = nodeA.parent

        if connectingNode:
            if connectingNode.left == nodeA:
                connectingNode.left = nodeB
            else:
                connectingNode.right = nodeB
        else:
            self.root = nodeB

        nodeT1 = nodeA.left
        nodeT2 = nodeB.left
        nodeT3 = nodeB.right
        nodeT4 = nodeC.right

        self.performRotation(
            nodeA, nodeB, nodeC, nodeT1, nodeT2, nodeT3, nodeT4, connectingNode
        )

    def performRotation(
        self, nodeA, nodeB, nodeC, nodeT1, nodeT2, nodeT3, nodeT4, connectingNode
    ):
        nodeB.left = nodeA
        nodeB.right = nodeC
        nodeB.parent = connectingNode

        nodeA.left = nodeT1
        nodeA.right = nodeT2
        nodeA.parent = nodeB

        nodeC.left = nodeT3
        nodeC.right = nodeT4
        nodeC.parent = nodeB

        # Set parents of subtrees
        self.setParent(nodeT1, nodeA)
        self.setParent(nodeT2, nodeA)
        self.setParent(nodeT3, nodeC)
        self.setParent(nodeT4, nodeC)

        # Set hieghts
        nodeA.height = (
            max(self.getNodeHeight(nodeA.left), self.getNodeHeight(nodeA.right)) + 1
        )
        nodeC.height = (
            max(self.getNodeHeight(nodeC.left), self.getNodeHeight(nodeC.right)) + 1
        )
        nodeB.height = (
            max(self.getNodeHeight(nodeB.left), self.getNodeHeight(nodeB.right)) + 1
        )

    def setParent(self, subTreeRoot: Node, rotationNode: Node):
        if not subTreeRoot:
            return
        subTreeRoot.parent = rotationNode


if __name__ == "__main__":
    main()
