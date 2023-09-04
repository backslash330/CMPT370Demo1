// Create a program to perform a selection sort on integer values. You may choose to use either 
// C/C++ or Java, but your code must run on a lab computer. (Keep in mind you may want to use 
// this code in assignment one). You may implement your selection sort using either an array or a 
// linked list. Use the initial data 2,4,6,0,1 to demonstrate your code.
// Also write a function / method that generates an array or linked list of random data. As 
// a parameter this method takes the size of the array/linked list to create. Generate random 
// values between zero and twice the length of the array/linked list. For example, an array of size 
// ten would be filled with values between 0 and 20. Use this method to create an array/linked 
// list of size ten and sort it. An example is shown below.
// Original Array: 2, 4, 6, 0, 1
//  After pass 1: 0, 4, 6, 2, 1
//  After pass 2: 0, 1, 6, 2, 4
//  After pass 3: 0, 1, 2, 6, 4
//  After pass 4: 0, 1, 2, 4, 6
// Sorted array: 0, 1, 2, 4, 6
// Original Array: 9, 6, 15, 16, 4, 16, 4, 11, 11, 5
//  After pass 1: 4, 6, 15, 16, 9, 16, 4, 11, 11, 5
//  After pass 2: 4, 4, 15, 16, 9, 16, 6, 11, 11, 5
//  After pass 3: 4, 4, 5, 16, 9, 16, 6, 11, 11, 15
//  After pass 4: 4, 4, 5, 6, 9, 16, 16, 11, 11, 15
//  After pass 5: 4, 4, 5, 6, 9, 16, 16, 11, 11, 15
//  After pass 6: 4, 4, 5, 6, 9, 11, 16, 16, 11, 15
//  After pass 7: 4, 4, 5, 6, 9, 11, 11, 16, 16, 15
//  After pass 8: 4, 4, 5, 6, 9, 11, 11, 15, 16, 16
//  After pass 9: 4, 4, 5, 6, 9, 11, 11, 15, 16, 16
// Sorted array: 4, 4, 5, 6, 9, 11, 11, 15, 16, 16
// Appropriately comment your code. When you have completed your program demonstrate it to 
// the instructor and upload your source code to Moodle

// Author: Nicholas Almeida

public class Demo1 {
    // Main method
    public static void main(String[] args) {
        // create basic request array
        int[] arr = {2, 4, 6, 0, 1};
        // print original array
        System.out.println("Original Array: " + printArray(arr));
        // call selection sort method
        selectionSort(arr);

        // set array to second request and retest
        arr = new int[]{9, 6, 15, 16, 4, 16, 4, 11, 11, 5};
        System.out.println("Original Array: " + printArray(arr));
        selectionSort(arr);

        //create a new array based on a given size
        int[] arr2 = generateArray(10);
        System.out.println("Original Array: " + printArray(arr2));

    }

    // method to print an array
    public static String printArray(int[] arr) {
        // create string to return
        String ret = "";
        // loop through array and add each element to string, except last
        for (int i = 0; i < arr.length - 1; i++) {
            // add element to string
            ret += arr[i] + ", ";
        }
        // add last element to string
        ret += arr[arr.length - 1];
        // return string
        return ret;
    }

    //select  sort method
    // This method does the sort and prints each step as it goes
    public static void selectionSort(int[] arr) {
        // confirm the array is not empty 
        if (arr.length == 0) {
            // print error message
            System.out.println("Array is empty");
            // exit method
            return;
        }

        // if there is only one element in the array, it is already sorted
        if (arr.length == 1) {
            // print array
            System.out.println("Sorted array: " + printArray(arr));
            // exit method
            return;
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
            System.out.println("After pass " + (i + 1) + ": " + printArray(arr));
        }


        // print sorted array
        System.out.println("Sorted array: " + printArray(arr));
    }
    
    //isSorted method
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

    // generateArray method
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
}

