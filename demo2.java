// Author: Nicholas Almeida
// Note: Including the print statements vastly increases the time it takes to sort the array


public class demo2{
    public static void main(String[] args){
        // Test against first example array.
        int[] arr = {5, 9, 2, 9, 7};
        System.out.println("Initial Array: " + printArray(arr));
        quickSort(arr, 0, arr.length-1, 0);
        System.out.println("Sorted Array: " + printArray(arr));

        // Test against generated array with timestamp in Milliseconds
        int[] arr2 = generateArray(5);
        System.out.println("Initial Array (Random 5): " + printArray(arr2));
        long startTime = System.currentTimeMillis();
        quickSort(arr2, 0, arr2.length-1, 0);
        System.out.println("Sorted Array (Random 5): " + printArray(arr2));
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        // Test against generated array of 100000 elements with timestamp in Milliseconds
        int[] arr3 = generateArray(100000);
        System.out.println("Initial Array (Random 100000): " + printArray(arr3));
        startTime = System.currentTimeMillis();
        quickSort(arr3, 0, arr3.length-1, 0);
        System.out.println("Sorted Array (Random 100000): " + printArray(arr3));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        // Test against generated array of 500000 elements with timestamp in Milliseconds
        int[] arr4 = generateArray(500000);
        System.out.println("Initial Array (Random 500000): " + printArray(arr4));
        startTime = System.currentTimeMillis();
        quickSort(arr4, 0, arr4.length-1, 0);
        System.out.println("Sorted Array (Random 500000): " + printArray(arr4));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        // Test against generated array of 1000000 elements with timestamp in Milliseconds
        int[] arr5 = generateArray(1000000);
        System.out.println("Initial Array (Random 1000000): " + printArray(arr5));
        startTime = System.currentTimeMillis();
        quickSort(arr5, 0, arr5.length-1, 0);
        System.out.println("Sorted Array (Random 1000000): " + printArray(arr5));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");


    }

    // printArray method
    // This method prints out the contents of an array
    public static String printArray(int[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str += arr[i] + " ";
        }
        return str;
    }

    // printArray method
    // This method only prints out the contents of an array between two indices
    public static String printArray(int[] arr, int left, int right){
        String str = "";
        for(int i = left; i <= right; i++){
            str += arr[i] + " ";
        }
        return str;
    }

    // quickSort method
    public static void quickSort(int[] arr, int left, int right, int level){
        if(left < right){
            int pivot = partition(arr, left, right);
            System.out.println("Full array: " + printArray(arr));
            System.out.println("Level " + level + ": Before Left: " + printArray(arr, left, pivot-1));
            System.out.println("Level " + level + ": Before Pivot is: " + arr[pivot]);
            System.out.println("Level " + level + ": Before Right: " + printArray(arr, pivot+1, right));
            quickSort(arr, left, pivot-1, level+1);
            quickSort(arr, pivot+1, right, level+1);
        }
    }

    //partition function
    // This function takes last element as pivot, places
    // the pivot element at its correct position in sorted
    // array, and places all smaller (smaller than pivot)
    // to left of pivot and all greater elements to right
    // of pivot
    static int partition(int[] arr, int left, int right)
    {
        // Choosing the pivot
        int pivot = arr[right];
        System.out.println("pivot: " + pivot);

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

    // 
    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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

