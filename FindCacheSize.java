import java.util.Arrays;
/*
 * Author: Jakob MIllen
 * ID: 1507831
 * 
 * Material Referenced From:
 * Title: Gallery of Processor Cache Effects
 * Author: Igor Ostrovsky
 * Link: https://igoro.com/archive/gallery-of-processor-cache-effects/
 * Date: 05/06/2024
 */

public class FindCacheSize {
    
    // Initalise variables
    final int ITERATIONS = 50;
    final int WRAMUP_ITERATIONS = 5; 
    final int KB = 1024;
    final int MB = 1024 * KB;
    final double SECONDS = 1000000000.0; // 1 second in nanoseconds
    // Array of sizes to test 1KB to 256MB
    final int[] sizes = {1 * KB, 2 * KB, 4 * KB, 8 * KB, 16 * KB, 32 * KB, 64 * KB, 128 * KB, 256 * KB, 512 * KB, 1 * MB, 2 * MB, 4 * MB, 8 * MB, 16 * MB, 32 * MB, 64 * MB, 128 * MB, 256 * MB};

    public static void main(String[] args) {
        System.out.println("Testing System Cache Size:");

        FindCacheSize cacheSize = new FindCacheSize();
        cacheSize.testCache();
    }


    public void testCache() {

        int steps = 64 * 1024 * 1024; // Large number of steps to test cache
        int lengthMod; // Used to store length of array
        long start, end, avgTime; // Variables for storing the start and end time

        for (int i = 0; i < sizes.length; i++) {

            byte[] array = new byte[sizes[i]]; // Allocate the array of the specified size
            Arrays.fill(array, (byte) 1); // Fill the array with "1"

            avgTime = 0; // Reset the average time
            lengthMod = array.length - 1;

            // Warm up the cache
            for (int m = 0; m <= WRAMUP_ITERATIONS; m++) {
                for (int j = 0; j < steps; j++) {
                    array[(j * 64) & lengthMod]++; // Access the array element
                }
            }

            // Test the Cache
            for (int m = 0; m <= ITERATIONS; m++) {
                start = System.nanoTime(); // Start the timer

                for (int j = 0; j < steps; j++) {
                    array[(j * 64) & lengthMod]++; // Access the array element
                }

                end = System.nanoTime(); // End the timer
                avgTime += (end - start); // Calculate the time taken
            }

            // Print the results to console
            System.out.println("Size: " + (sizes[i] / KB) + "KB, Time: " + (avgTime / ITERATIONS / SECONDS) + "s");
        }
    }
}
