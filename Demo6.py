# want 1000 -1000

# wiki random page https://en.wikipedia.org/wiki/Special:Random

import requests


def main():
    print("Beginning Demo 6")

    # link = "https://en.wikipedia.org/wiki/Special:Random"

    # r = requests.get(link)
    # counts = dict.fromkeys("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", 0)
    # for line in r.text:
    #     for char in line:
    #         if char in counts:
    #             counts[char] += 1
    testCounts = {'k' : 10, 'm' : 20, 'e' : 5, 'i' : 15}

   # testCounts2 = {'a' : 3374, 'b' : 689, 'c' : 1914}
    queue = PriorityQue(testCounts)
    print("Dequeue...")
    print(queue.dequeue())
    print(queue)
    print(queue.dequeue())
    print(queue)
    print(queue.dequeue())
    print(queue)
    print(queue.dequeue())
    print(queue)
    print("Ending Demo 6")

class Node:
    previous = None
    next = None
    value = None
    frequency = None

    def __str__(self) -> str:
        return str(self.value) + "(" + str(self.frequency) + ")"

    def __init__(self, previous, next, value, frequency) -> None:
        self.previous = previous
        self.next = next
        self.value = value
        self.frequency = frequency


class PriorityQue:
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
            else: # currentNode.frequency >= frequency:
                if currentNode is self.root:
                    newNode = Node(currentNode.previous, currentNode, value, frequency)
                    currentNode.previous = newNode
                    self.root = newNode
                    return
                else:
                    #print('Hit issue case')
                    #print(currentNode)
                    #print(currentNode.previous)
                    newNode = Node(currentNode.previous, currentNode, value, frequency)
                    previousNode.next = newNode
                    currentNode.previous = newNode
                    return
            currentNode = currentNode.next
        
    def dequeue(self) -> int:
        if self.root == None:
            return None
        
        value = self.root.value

        self.root = self.root.next

        if self.root == None:
            return value 
        
        self.root.previous = None

        return value


if __name__ == "__main__":
    main()
