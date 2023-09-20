// Analyze the following five sorting algorithms using an experimental method: selection 
// sort, bubble sort, insertion sort, quick sort, and merge sort. You may select C/C++ or Java as 
// the implementation language, but your choice of language should be consistent for all 
// algorithms. Regarding the experimental method, you should write your own implementation of 
// each algorithm and record the time required by the algorithm to sort a set of integers.
// As input to each sorting algorithm, you should use sets of random integers, sorted integer sets, 
// and reverse sorted integer sets. Make copies of your data sets so that all algorithms get the 
// same data

//TO DO
// understand heap sort better
// make main method. It needs to right results to a file.
// if possible, make a graph of the results 


//Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class assignment1 {


    public static void main(String[] args) {
        //create a file to write to

        


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
