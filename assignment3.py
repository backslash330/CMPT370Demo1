# want 1000 -1000

# wiki random page https://en.wikipedia.org/wiki/Special:Random

import requests
import sys


def main():
    print("beginning test")

    link = "https://en.wikipedia.org/wiki/Special:Random"

    r = requests.get(link)
    totalchars = 0
 #   counts = dict.fromkeys("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 0)
    counts = {chr (i): i for i in range (128)}
    # set all values to 0
    for key in counts:
        counts[key] = 0
    # create dictionary that holds all ascii characters and their frequencies
    asciiDictionary = dict.fromkeys(
        "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~",
        0,
    )
    for line in r.text:
        for char in line:
            totalchars += 1
            if char in counts:
                counts[char] += 1
    queue = PriorityQueue(counts)
    bits = totalchars * 7
    print("Total bits before compression: " + str(bits))
    huffman = HuffmanTree(queue)
    # print("beginning in order traversal")
    # inOrderTraversal(huffman.root)
    # print("end in order traversal")
    # codeDictionary = dict.fromkeys(
    #     "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~",
    #     0,
    # )
    codeDictionary =  {chr (i): i for i in range (128)}
    for key in codeDictionary:
        codeDictionary[key] = 0
    codeDictionary = getHuffmanCodes(huffman.root, "", codeDictionary)
    print(codeDictionary)

    # loop through the dictionary and create a string that encodes the text using the huffman codes
    # then count the length of the string to get the compressed size
    compressedSize = 0
    for line in r.text:
        for char in line:
            if char in codeDictionary:
                if codeDictionary[char] != 0:
                    compressedSize += len(codeDictionary[char])
    print("Total bits after compression: " + str(compressedSize))
    print("Compression text precentage: " + str(compressedSize / bits * 100) + "%")


# in order traversal for debugging
def inOrderTraversal(node):
    if node == None:
        # print("Node is null")
        return
    inOrderTraversal(node.left)
    print(str(node.value) + "(" + str(node.frequency) + ")")
    inOrderTraversal(node.right)


def getHuffmanCodes(node, code, codeDictionary):
    # use in order traversal to get the huffman codes
    # left is 0, right is 1
    if node == None:
        return
    if node.isHumffmanNode:
        getHuffmanCodes(node.left, code + "0", codeDictionary)
        getHuffmanCodes(node.right, code + "1", codeDictionary)
    else:
        if node.value != None and node.value in codeDictionary:
            codeDictionary[node.value] = code
    return codeDictionary


class Node:
    previous = None
    next = None
    value = None
    frequency = None
    left = None
    right = None
    isHumffmanNode = False

    def __str__(self) -> str:
        return str(self.value) + "(" + str(self.frequency) + ")"

    def __init__(
        self,
        previous,
        next,
        value,
        frequency,
        left=None,
        right=None,
        isHuffmanNode=False,
    ) -> None:
        self.previous = previous
        self.next = next
        self.value = value
        self.frequency = frequency
        self.left = left
        self.right = right
        self.isHumffmanNode = isHuffmanNode


class PriorityQueue:
    root = None
    nodeCount = 0

    def __str__(self) -> str:
        # print("Printing string here")
        string = ""
        currentNode = self.root
        while currentNode != None:
            string += str(currentNode.value) + str(currentNode.frequency)
            string += " -> "
            currentNode = currentNode.next
        string += "null"
        return string

    def __init__(self, values) -> None:
        print("The queue is empty")
        print("There are" + str(self.nodeCount) + "entries in the queue")
        for enumeration, key in enumerate(values):
            print("- Inserting " + str(key) + "(" + str(values[key]) + ")" + "-")
            self.enqueue(key, values[key])
            self.nodeCount += 1
            print(self)
            print("There are " + str(self.nodeCount) + " entries in the queue")

    def enqueue(self, value, frequency):
        # print("Root is " + str(self.root))
        if self.root == None:
            self.root = Node(None, None, value, frequency)
            # print("Root is " + str(self.root))
            return
        currentNode = self.root
        while currentNode != None:
            previousNode = currentNode.previous
            if currentNode.frequency < frequency:
                if currentNode.next == None:
                    currentNode.next = Node(currentNode, None, value, frequency)
                    return
            else:  # currentNode.frequency >= frequency:
                if currentNode is self.root:
                    newNode = Node(currentNode.previous, currentNode, value, frequency)
                    currentNode.previous = newNode
                    self.root = newNode
                    return
                else:
                    newNode = Node(currentNode.previous, currentNode, value, frequency)
                    currentNode.previous.next = newNode
                    currentNode.previous = newNode
                    return
            currentNode = currentNode.next

    def dequeue(self) -> Node:
        if self.root == None:
            return None

        node = self.root
        # if the value of the node is itself the node, we return that instead
        if isinstance(node.value, Node):
            node = node.value

        self.root = self.root.next

        if self.root == None:
            return node

        self.root.previous = None

        return node


class HuffmanTree:
    root = None

    def __init__(self, queue: PriorityQueue) -> None:
        print("starting huffman...")
        while queue.root != None:
            left = queue.dequeue()
            right = queue.dequeue()
            print
            if right == None:
                self.root = left
                break
            newFrequency = left.frequency + right.frequency
            newNode = Node(None, None, newFrequency, newFrequency, left, right, True)
            queue.enqueue(newNode, newNode.frequency)
        return


if __name__ == "__main__":
    main()
