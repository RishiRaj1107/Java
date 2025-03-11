import java.util.Scanner;

class Solution {
    // Function to sort an array of 0s, 1s, and 2s
    public void sort012(int[] arr) {
        int low = 0, mid = 0, high = arr.length - 1;
        
        while (mid <= high) {
            if (arr[mid] == 0) { 
                // Swap arr[mid] and arr[low], then increment both
                swap(arr, mid, low);
                mid++;
                low++;
            } else if (arr[mid] == 1) { 
                // 1 is already in the correct place, just move mid
                mid++;
            } else { 
                // Swap arr[mid] and arr[high], then decrement high
                swap(arr, mid, high);
                high--;
            }
        }
    }
    
    // Swap function to exchange elements
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

public class DutchNational {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input array size
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();
        int[] arr = new int[n];

        // Input array elements (only 0, 1, 2 allowed)
        System.out.println("Enter elements (0, 1, or 2 only): ");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Sorting the array
        Solution solution = new Solution();
        solution.sort012(arr);

        // Display sorted array
        System.out.println("Sorted array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        scanner.close();
    }
}