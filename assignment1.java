// Analyze the following five sorting algorithms using an experimental method: selection 
// sort, bubble sort, insertion sort, quick sort, and merge sort. You may select C/C++ or Java as 
// the implementation language, but your choice of language should be consistent for all 
// algorithms. Regarding the experimental method, you should write your own implementation of 
// each algorithm and record the time required by the algorithm to sort a set of integers.
// As input to each sorting algorithm, you should use sets of random integers, sorted integer sets, 
// and reverse sorted integer sets. Make copies of your data sets so that all algorithms get the 
// same data

//TO DO  java -Xss1000m assignment1.java


//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class assignment1 {

    // Test:
    // 10000 random numbers
    // 10000 sorted numbers
    // 10000 reverse sorted numbers
    // 100000 random numbers
    // 100000 sorted numbers
    // 100000 reverse sorted numbers
    // 1000000 random numbers
    // 1000000 sorted numbers
    // 1000000 reverse sorted numbers
    // 5 tests of each
    public static void main(String[] args) {
        
        // create a file
        File file = new File("results.txt");
        // create a print writer
        try{
            FileWriter writer = new FileWriter("results.txt");
            // Write a header
            writer.write("Assignment 1 Experiment Results\n");
            // create a line to break the data up
            writer.write("______________________________________________________________________________\n");
            // We are going to run the following set of tests 5 tims each to create multiple data points:
            for (int i = 0; i < 5; i++){
                // Print a header for the test set 
                writer.write("Test Set " + (i + 1) + ":\n");
                System.out.println("running test set " + (i + 1) + "...");
                // create a line to break the data up
                writer.write("______________________________________________________________________________\n");
                // run tests
                // 10000 random numbers
                System.out.println("running 10000 random numbers experiment...");
                writer.write("10000 random numbers experiment:\n");
                runExperiment(writer, 10000, 1, false);
                System.out.println("10000 random numbers experiment complete");
                // 10000 sorted numbers
                System.out.println("running 10000 sorted numbers experiment...");
                writer.write("10000 sorted numbers experiment:\n");
                runExperiment(writer, 10000, 2, false);
                System.out.println("10000 sorted numbers experiment complete");
                // 10000 reverse sorted numbers
                System.out.println("running 10000 reverse sorted numbers experiment...");
                writer.write("10000 reverse sorted numbers experiment:\n");
                runExperiment(writer, 10000, 3, false);
                System.out.println("10000 reverse sorted numbers experiment complete");
                // 100000 random numbers
                System.out.println("running 100000 random numbers experiment...");
                writer.write("100000 random numbers experiment:\n");
                runExperiment(writer, 100000, 1, false);
                System.out.println("100000 random numbers experiment complete");
                // 100000 sorted numbers
                System.out.println("running 100000 sorted numbers experiment...");
                writer.write("100000 sorted numbers experiment:\n");
                runExperiment(writer, 100000, 2, false);
                System.out.println("100000 sorted numbers experiment complete");
                // 100000 reverse sorted numbers
                System.out.println("running 100000 reverse sorted numbers experiment...");
                writer.write("100000 reverse sorted numbers experiment:\n");
                runExperiment(writer, 100000, 3, false);
                System.out.println("100000 reverse sorted numbers experiment complete");
                // Did not run the million tests because it takes too long and I could not get the stack size to increase
                // to allow it to run
                // // 1000000 (1 million) random numbers
                // System.out.println("running 1000000 random numbers experiment...");
                // writer.write("1000000 random numbers experiment:\n");
                // runExperiment(writer, 1000000, 1, false);
                // System.out.println("1000000 random numbers experiment complete");
                // // 1000000 sorted numbers
                // System.out.println("running 1000000 sorted numbers experiment...");
                // writer.write("1000000 sorted numbers experiment:\n");
                // runExperiment(writer, 1000000, 2, false);
                // System.out.println("1000000 sorted numbers experiment complete");
                // // 1000000 reverse sorted numbers
                // System.out.println("running 1000000 reverse sorted numbers experiment...");
                // writer.write("1000000 reverse sorted numbers experiment:\n");
                // runExperiment(writer, 1000000, 3, false);
                // System.out.println("1000000 reverse sorted numbers experiment complete");
                System.out.println("test set " + (i + 1) + " complete");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // runExperiment method
    // This method runs an experiment on a given array size and type
    public static void runExperiment(FileWriter writer, int size, int random, boolean debug){
        // create variables to store the start and end times and average time
        long startTime = 0;
        long endTime = 0;
        long selectSortAvg = 0;
        long bubbleSortAvg = 0;
        long insertionSortAvg = 0;
        long quickSortAvg = 0;
        long mergeSortAvg = 0;
        long heapSortAvg = 0;
        for (int i = 0; i < 5; i++){
            // create a random, sorted, or reverse sorted array
            // create a copy of the array for each sort
            int[] originalArr = new int[size];
            if(random == 1){
                // create random array
                originalArr = generateArray(size);
            }
            else if(random == 2){
                // create sorted array
                originalArr = generateArray(size);
                originalArr = selectionSort(originalArr);
            }
            else{
                // create reverse sorted array
                originalArr = generateArray(size);
                originalArr = selectionSort(originalArr);
                originalArr = reverseArray(originalArr);
            }
            int[] arr = originalArr.clone();
            //debug: print the array to console
            if(debug == true){
                System.out.println("Initial  Select Sory Array (Random " + size + "): " + printArray(arr));
            }
            // selection sort
            startTime = System.currentTimeMillis();
            selectionSort(arr);
            endTime = System.currentTimeMillis();
            writeResults(writer, "Selection Sort",random, size,  startTime, endTime);
            selectSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Select Sort Array (Random " + size + "): " + printArray(arr));
            }
            // bubble sort
            arr = originalArr.clone();
            if(debug == true){
                System.out.println("Initial Bubble Sort Array (Random " + size + "): " + printArray(arr));
            }
            startTime = System.currentTimeMillis();
            bubbleSort(arr);
            endTime = System.currentTimeMillis();
            writeResults(writer, "Bubble Sort",random, size,  startTime, endTime);
            bubbleSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Bubble Sort Array (Random " + size + "): " + printArray(arr));
            }
            // insertion sort
            arr = originalArr.clone();
            if(debug == true){
                System.out.println("Initial Insertion Sort Array (Random " + size + "): " + printArray(arr));
            }
            startTime = System.currentTimeMillis();;
            insertionSort(arr);
            endTime = System.currentTimeMillis();;
            writeResults(writer, "Insertion Sort",random, size,  startTime, endTime);
            insertionSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Insertion Sort Array (Random " + size + "): " + printArray(arr));
            }
            // quick sort
            arr = originalArr.clone();
            if(debug == true){
                System.out.println("Initial Quick Sort Array (Random " + size + "): " + printArray(arr));
            }
            startTime = System.currentTimeMillis();
            quickSort(arr, 0, arr.length - 1, 0);
            endTime = System.currentTimeMillis();
            writeResults(writer, "Quick Sort",random, size,  startTime, endTime);
            quickSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Quick Sort Array (Random " + size + "): " + printArray(arr));
            }
            // merge sort
            arr = originalArr.clone();
            if(debug == true){
                System.out.println("Initial Merge Sort Array (Random " + size + "): " + printArray(arr));
            }
            startTime = System.currentTimeMillis();
            mergeSort(arr);
            endTime = System.currentTimeMillis();
            writeResults(writer, "Merge Sort",random, size,  startTime, endTime);
            mergeSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Merge Sort Array (Random " + size + "): " + printArray(arr));
            }
            // heap sort
            arr = originalArr.clone();
            if(debug ==true){
                System.out.println("Initial Heap Sort Array (Random " + size + "): " + printArray(arr));
            }
            startTime = System.currentTimeMillis();
            heapSort(arr);
            endTime = System.currentTimeMillis();
            writeResults(writer, "Heap Sort",random, size,  startTime, endTime);
            heapSortAvg += (endTime - startTime);
            if(debug == true){
                System.out.println("Sorted Heap Sort Array (Random " + size + "): " + printArray(arr));
            }
        }
            //Report the results
            try{
            writer.write("Average Times:\n");
            writer.write("Selection Sort: " + selectSortAvg/5 + " milliseconds\n");
            writer.write("Bubble Sort: " + bubbleSortAvg/5 + " milliseconds\n");
            writer.write("Insertion Sort: " + insertionSortAvg/5 + " milliseconds\n");
            writer.write("Quick Sort: " + quickSortAvg/5 + " milliseconds\n");
            writer.write("Merge Sort: " + mergeSortAvg/5 + " milliseconds\n");
            writer.write("Heap Sort: " + heapSortAvg/5 + " milliseconds\n");
            // create a line to break the data up
            writer.write("______________________________________________________________________________\n");
            } 
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
    }
    
    // writeResults method
    // This method writes the results of a test to a file
    public static void writeResults(FileWriter writer, String sort, int random, int size, long startTime, long endTime){
        // set random string
        String rand = "";
        if(random == 1){
            rand = "random";
        }
        else if(random == 2){
            rand = "sorted";
        }
        else{
            rand = "reverse sorted";
        }
            // write the results to the file before the next sort
            try{
                writer.write(sort + " Size " + size + " " + rand + ": " + (endTime - startTime) + " milliseconds\n");
            } 
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
    }

    //heap sort method
    public static int[] heapSort(int[] arr){
        // build heap
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, arr.length, i);
        }
        // extract elements from heap
        for (int i = arr.length - 1; i >= 0; i--) {
            // move current root to end
            swap(arr, 0, i);
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
        return arr;
    }

    // heapify method (heap sort)
    // This method builds a max heap
    public static void heapify(int[] arr, int n, int i){
        // set largest to root
        int largest = i;
        // set left to left child
        int left = 2 * i + 1;
        // set right to right child
        int right = 2 * i + 2;
        // check if left child is larger than root
        if(left < n && arr[left] > arr[largest]){
            // set largest to left child
            largest = left;
        }
        // check if right child is larger than root
        if(right < n && arr[right] > arr[largest]){
            // set largest to right child
            largest = right;
        }
        // check if largest is not root
        if(largest != i){
            // swap root with largest
            swap(arr, i, largest);
            // recursively call heapify on the affected subtree
            heapify(arr, n, largest);
        }
    }

    //merge sort method
    // This method splits the array in half and sorts each half recursively
    public static int[] mergeSort(int[] arr){
        // check if array is empty or only has one element
        if(arr.length <= 1){
            return arr;
        }
        // split array in half
        int[] left = new int[arr.length/2];
        int[] right = new int[arr.length - left.length];
        // copy left half of array into left
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        // copy right half of array into right
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[left.length + i];
        }
        // recursively call mergeSort on left and right
        // We don't need to store the return value because the array is passed by reference
        mergeSort(left);
        mergeSort(right);
        // merge left and right
        merge(left, right, arr);
        return arr;
    }

    public static int[] reverseArray(int[] arr){
        int[] temp = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            temp[i] = arr[arr.length - 1 - i];
        }
        arr = temp;
        return arr;
    }

    
    // merge method (merge sort)    
    // This method merges two arrays into one sorted array
    public static void merge(int[] left, int[] right, int[] arr){
        // set i to index of left array
        int i = 0;
        // set j to index of right array
        int j = 0;
        // set k to index of merged array
        int k = 0;
        // loop through left and right arrays
        while(i < left.length && j < right.length){
            // check if left element is less than right element
            if(left[i] < right[j]){
                // add left element to merged array
                arr[k] = left[i];
                // increment i
                i++;
            }
            else{
                // add right element to merged array
                arr[k] = right[j];
                // increment j
                j++;
            }
            // increment k
            k++;
        }
        // loop through left array
        while(i < left.length){
            // add left element to merged array
            arr[k] = left[i];
            // increment i
            i++;
            // increment k
            k++;
        }
        // loop through right array
        while(j < right.length){
            // add right element to merged array
            arr[k] = right[j];
            // increment j
            j++;
            // increment k
            k++;
        }
    }

    //insertion sort method
    // This method swaps the current element with the previous element until the current element is in the correct position
    // think of it like a deck of cards
    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // set current element to temp
            int temp = arr[i];
            // set j to previous index
            int j = i - 1;
            // loop through array starting at previous index
            while (j >= 0 && arr[j] > temp) {
                // swap current element with previous element
                arr[j + 1] = arr[j];
                j--;
            }
            // set current element to temp
            arr[j + 1] = temp;
            
        }
        return arr;
    }

    //Bubble Sort method
    // this method bubbles the largest element to the end of the array with each pass
    public static int[] bubbleSort(int[] arr){

        // loop through array
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            // loop through array starting at next element
            for (int j = 0; j < arr.length - i - 1; j++) {
                // check if current element is greater than next element
                if (arr[j] > arr[j + 1]) {
                    // swap current element with next element
                    swap (arr, j, j + 1);
                    swapped = true;
                }
            }
            if (swapped == false){
                return arr;
            }
        }


        return arr;
    } 

    // quickSort method
    public static int[] quickSort(int[] arr, int left, int right, int level){
        if(left < right){
            int pivot = partition(arr, left, right);
            int[] leftArr = quickSort(arr, left, pivot-1, level+1);
            int[] rightArr = quickSort(arr, pivot+1, right, level+1);
        }
        return arr;
    }

    //partition function (quicksort)
    // This function takes last element as pivot, places
    // the pivot element at its correct position in sorted
    // array, and places all smaller (smaller than pivot)
    // to left of pivot and all greater elements to right
    // of pivot
    static int partition(int[] arr, int left, int right)
    {
        // Choosing the pivot
        int pivot = arr[right];
        // System.out.println("pivot: " + pivot);

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = (left - 1);

        for (int j = left; j <= right - 1; j++) {

            // If current element is smaller than the pivot
            if (arr[j] < pivot) {

                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return (i + 1);
    }

    // swap function (quicksort)
    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //select  sort method (selection sort)
    // This method does the sort and prints each step as it goes
    public static int[] selectionSort(int[] arr) {
        // confirm the array is not empty 
        if (arr.length == 0) {
            // System.out.println("Array is empty");

            return arr;
        }

        // if there is only one element in the array, it is already sorted
        if (arr.length == 1) {
            // System.out.println("Sorted array: " + printArray(arr));
            return arr;
        }

        // main body of the sort.
        // I need to figure out how to early exit like Quinn did. By IsSorted function didn't work
        // Solution assignment swapping done directly vs using a temp variable
        // loop through array
        for (int i = 0; i < arr.length - 1; i++) {
            // set min to current element
            int min = arr[i];
            // set min index to current index
            int minIndex = i;
            // loop through array starting at next element
            for (int j = i + 1; j < arr.length; j++) {
                // check if current element is less than min
                if (arr[j] < min) {
                    // set min to current element
                    min = arr[j];
                    // set min index to current index
                    minIndex = j;
                }
            }
            // swap current element with min
            int temp = arr[i];
            arr[i] = min;
            arr[minIndex] = temp;
            // print array
            // System.out.println("After pass " + (i + 1) + ": " + printArray(arr));

        }

        // print sorted array
        // System.out.println("Sorted array: " + printArray(arr));
        return arr;
    }
    
    //isSorted method (selection sort)
    // This method checks if the array is sorted
    public static boolean isSorted(int[] arr) {
        // loop through array
        for (int i = 0; i < arr.length - 1; i++) {
            // check if current element is greater than next element
            if (arr[i] > arr[i + 1]) {
                // return false
                return false;
            }
        }
        // return true
        return true;
    }

    // generateArray method (all)
    // This method generates an array of random numbers based on a given size
    public static int[] generateArray(int size) {
        // create array
        int[] arr = new int[size];
        // loop through array
        for (int i = 0; i < arr.length; i++) {
            // generate random number between 0 and 2 * size
            // math.random = 0.0-1.0
            int rand = (int) (Math.random() * (2 * size));
            // add random number to array
            arr[i] = rand;
        }
        // return array
        return arr;
    }

    // printArray method (all)
    // This method prints out the contents of an array
    public static String printArray(int[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str += arr[i] + " ";
        }
        return str;
    }

    // printArray method (all)
    // This method only prints out the contents of an array between two indices
    public static String printArray(int[] arr, int left, int right){
        String str = "";
        for(int i = left; i <= right; i++){
            str += arr[i] + " ";
        }
        return str;
    }



}
