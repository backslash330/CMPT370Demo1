# this is a rewrite of assignment 1 for Project 1
# this rewrite was done due to being unsure if I used online code for assignmet 1
# only quicksort, mergesort, and heapsort are being rewritten


# imports
import random
import time
import sys

# Runs the experiment, in groups of 3
def runExperiment(
    rawFile, AvgRandFile, AvgSortedFile, AvgRevSortedFile, arraySize, testType, debug
):
    # create the array
    if testType == 1:  # random array
        array = [random.randint(0, 2 * arraySize) for i in range(arraySize)]
    elif testType == 2:  # sorted array
        array = [random.randint(0, 2 * arraySize) for i in range(arraySize)]
        array.sort()
    elif testType == 3:  # reverse sorted array
        array = [random.randint(0, 2 * arraySize) for i in range(arraySize)]
        array.sort(reverse=True)
    else:
        print("invalid test type, cancelling experiment")
        return

    # Must declare due to scoping
    quickSortTime = 0
    mergeSortTime = 0
    heapSortTime = 0
    # run the tests 3 times and write all results to the four files
    for i in range(0, 3):
        # time needs to be in milliseconds
        arrayCopy = array.copy()
        startTime = round(time.time() * 1000)
        quickSortedArray = quickSort(arrayCopy, 0, arraySize - 1)
        endTime = round(time.time() * 1000)
        quickSortTime += endTime - startTime
        writeResults(rawFile, "Selection Sort", testType, arraySize, startTime, endTime)

        arrayCopy = array.copy()
        startTime = round(time.time() * 1000)
        mergeSortedArray = mergeSort(arrayCopy)
        endTime = round(time.time() * 1000)
        mergeSortTime += endTime - startTime
        writeResults(rawFile, "Merge Sort", testType, arraySize, startTime, endTime)

        arrayCopy = array.copy()
        startTime = round(time.time() * 1000)
        heapSortedArray = heapSort(arrayCopy)
        endTime = round(time.time() * 1000)
        heapSortTime += endTime - startTime
        writeResults(rawFile, "Heap Sort", testType, arraySize, startTime, endTime)

    # write avg results
    rawFile.write("\nAverage Times:\n")
    rawFile.write("Quick Sort: " + str(round(quickSortTime / 5)) + " milliseconds\n")
    rawFile.write("Merge Sort: " + str(round(mergeSortTime / 5)) + " milliseconds\n")
    rawFile.write("Heap Sort: " + str(round(heapSortTime / 5)) + " milliseconds\n\n")

    if testType == 1:
        AvgRandFile.write(
            str(round(quickSortTime / 5))
            + ","
            + str(round(mergeSortTime / 5))
            + ","
            + str(round(heapSortTime / 5))
            + ","
            + str(arraySize)
            + "\n"
        )
    elif testType == 2:
        AvgSortedFile.write(
            str(round(quickSortTime / 5))
            + ","
            + str(round(mergeSortTime / 5))
            + ","
            + str(round(heapSortTime / 5))
            + ","
            + str(arraySize)
            + "\n"
        )
    elif testType == 3:
        AvgRevSortedFile.write(
            str(round(quickSortTime / 5))
            + ","
            + str(round(mergeSortTime / 5))
            + ","
            + str(round(heapSortTime / 5))
            + ","
            + str(arraySize)
            + "\n"
        )
    else:
        print("invalid test type, cancelling experiment")
        return

# writes the results to the raw file.
def writeResults(rawFile, sortType, testType, arraySize, startTime, endTime):
    if testType == 1:
        type = "Random"
    elif testType == 2:
        type = "Sorted"
    elif testType == 3:
        type = "Reverse Sorted"
    else:
        print("invalid test type, cancelling writeResults")
        return
    rawFile.write(
        sortType
        + " Size "
        + str(arraySize)
        + " "
        + type
        + ": "
        + str(endTime - startTime)
        + " milliseconds\n"
    )


def quickSort(array, left, right):
    if left < right:
        pivot = partition(array, left, right)
        quickSort(array, left, pivot - 1)
        quickSort(array, pivot + 1, right)


# note: removed swap from java version because of python passes by assignment
# also makes the whole thing quicker due to fewer func calls
def partition(array, left, right):
    pivot = array[right]
    i = left - 1
    for j in range(left, right):
        if array[j] <= pivot:
            i = i + 1
            temp = array[i]
            array[i] = array[j]
            array[j] = temp
    temp = array[i + 1]
    array[i + 1] = array[right]
    array[right] = temp
    return i + 1

#  Merge sort function, uses merge as a subfunction
def mergeSort(array):
    if len(array) <= 1:
        return array

    # split the array
    left = [x for i, x in enumerate(array) if i < len(array) / 2]
    right = [x for i, x in enumerate(array) if i >= len(array) / 2]

    # recursive calls
    left = mergeSort(left)
    right = mergeSort(right)

    # merge the arrays
    return merge(left, right, array)

# merges the individual arrays
def merge(left, right, array):
    i = 0
    j = 0
    k = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            array[k] = left[i]
            i = i + 1
        else:
            array[k] = right[j]
            j = j + 1
        k = k + 1
    while i < len(left):
        array[k] = left[i]
        i = i + 1
        k = k + 1
    while j < len(right):
        array[k] = right[j]
        j = j + 1
        k = k + 1
    return array

# heap sort function, uses heapify as a subfunction
def heapSort(array):
    # build the heap
    for i in range(len(array) // 2 - 1, -1, -1):
        heapify(array, len(array), i)

    # sort the heap
    for i in range(len(array) - 1, 0, -1):
        temp = array[0]
        array[0] = array[i]
        array[i] = temp
        heapify(array, i, 0)
    return array

# turns an array into a max heap
def heapify(array, size, i):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2

    # check left child
    if left < size and array[left] > array[largest]:
        largest = left

    # check right child
    if right < size and array[right] > array[largest]:
        largest = right

    # change root if needed
    if largest != i:
        temp = array[i]
        array[i] = array[largest]
        array[largest] = temp
        heapify(array, size, largest)


def main():
    #Recursion limit must be overridden or program will fail on random sort of size 997 quicksort
    sys.setrecursionlimit(100000)
    # Create the files
    rawFile = open("rawresults.txt", "w")
    AvgRandFile = open("AvgRandomResults.csv", "w")
    AvgSortedFile = open("AvgSortedResults.csv", "w")
    AvgRevSortedFile = open("AvgReverseResults.csv", "w")

    # Write the headers
    rawFile.write("Assignment 1 Experiment Results\n")
    rawFile.write(
        "______________________________________________________________________________\n"
    )
    AvgRandFile.write("QuickSort,MergeSort,HeapSort,ArraySize(n)\n")
    AvgSortedFile.write("QuickSort,MergeSort,HeapSort,ArraySize(n)\n")
    AvgRevSortedFile.write("QuickSort,MergeSort,HeapSort,ArraySize(n)\n")

    # Now I run each test for each array size between 1 and 10000
    # Note: Python is not inclusive, therefore 1 - 10001
    # EAch test will be run 5 times and average the results.
    for arraySize in range(1, 10001):
        arraySizeString = str(arraySize)
        rawFile.write("Tests for array size " + arraySizeString + ":\n")
        rawFile.write(
            "______________________________________________________________________________\n"
        )

        # Run  tests
        print("running random test set for array size " + arraySizeString + "...")
        runExperiment(
            rawFile, AvgRandFile, AvgSortedFile, AvgRevSortedFile, arraySize, 1, False
        )
        print("random test set for array size " + arraySizeString + " complete")
        print("running sorted test set for array size " + arraySizeString + "...")
        runExperiment(
            rawFile, AvgRandFile, AvgSortedFile, AvgRevSortedFile, arraySize, 2, False
        )
        print("sorted test set for array size " + arraySizeString + " complete")
        print(
            "running reverse sorted test set for array size " + arraySizeString + "..."
        )
        runExperiment(
            rawFile, AvgRandFile, AvgSortedFile, AvgRevSortedFile, arraySize, 3, False
        )
        print("reverse sorted test set for array size " + arraySizeString + " complete")
        rawFile.write(
            "______________________________________________________________________________\n"
        )
    
    # close the files
    rawFile.close()
    AvgRandFile.close()
    AvgSortedFile.close()
    AvgRevSortedFile.close()


if __name__ == "__main__":
    main()
