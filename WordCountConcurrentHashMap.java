import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class WordCountConcurrentHashMap implements Callable<ConcurrentHashMap<String, Integer>> {
    String fileName;
    protected static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public void addWord(String word) {
        //compute is atomic operations in the ConcurrentHashMap class
        map.compute(word, (k,v)-> v == null ? 1 : v + 1);
    }

    WordCountConcurrentHashMap(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ConcurrentHashMap<String, Integer> call() throws Exception {
        Stream<String> stream = Files.lines(Paths.get(this.fileName));
        stream.forEach(
                line -> {
                    String data[] = line.replace(",", "").split(" ");
                    for (int i = 0; i < data.length; i++) {
                        addWord(data[i]);
                    }
                }
        );
        return map;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<WordCountConcurrentHashMap> list = new ArrayList<>();
        Future<ConcurrentHashMap<String, Integer>> futureResult = null;
        ConcurrentHashMap<String, Integer> resultMap;
        PriorityQueue<WordCount> minHeap = new PriorityQueue<>(Comparator.comparingInt(wc -> wc.count));

        //adding files to list
        String folderPath = "src/main/resources/";
        File file = new File(folderPath);
        File[] listOfFiles = {};
        if (!file.isFile()) {
            listOfFiles = file.listFiles();
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            list.add(new WordCountConcurrentHashMap(listOfFiles[i].getAbsolutePath()));
        }

        //submitting task with callable
        for (int i = 0; i < list.size(); i++) {
            futureResult = executorService.submit(list.get(i));
        }


        resultMap = futureResult.get();
        System.out.println(resultMap);
        //Sort using MinHeap
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            HeapHelper.updateMinHeap(minHeap, new WordCount(entry.getKey(), entry.getValue()), 2);
        }
        System.out.println(minHeap);
        executorService.shutdown();
    }
}
