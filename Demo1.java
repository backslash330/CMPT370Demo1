// Author: Nicholas Almeida

public class demo1 {
    // Main method
    public static void main(String[] args) {
        // Test against first example array
        int[] arr = {2, 4, 6, 0, 1};
        System.out.println("Original Array: " + printArray(arr));
        selectionSort(arr);

        // Test against second example array
        arr = new int[]{9, 6, 15, 16, 4, 16, 4, 11, 11, 5};
        System.out.println("Original Array: " + printArray(arr));
        selectionSort(arr);

        //create a new array based on a given size
        int[] arr2 = generateArray(10);
        System.out.println("Original Array: " + printArray(arr2));
        selectionSort(arr2);

    }

    // method to print an array
    public static String printArray(int[] arr) {
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
            System.out.println("Array is empty");

            return;
        }

        // if there is only one element in the array, it is already sorted
        if (arr.length == 1) {
            System.out.println("Sorted array: " + printArray(arr));
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

