import java.util.PriorityQueue;

public class HeapHelper {
    public static void updateMinHeap(PriorityQueue<WordCount> minHeap, WordCount data, int maxSize) {
        if (minHeap.size() < maxSize) {
            minHeap.offer(data);
        } else if (minHeap.peek().count < data.count) {
            minHeap.poll();
            minHeap.offer(data);
        }
    }
}
