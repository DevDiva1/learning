Learning by doing!

Problem1 : Find the top k frequently occurring words from single file
Follow up : Extend this to include multiple files

Questions regarding problem: 
Can file containing words fit into main memory?
How many distinct words are there in the file?

---------------------------------------------------------------------------------------------------------------------------

(Assumption : All words in the file can be accommodated in the memory.)

Approach 1 :
Utilizing HashMap and maintaing Priority Queue (min heap) to keep track of top k frequent occurring words

Time complexity - O (n log k) where n is the total number of elements and k is the number of top occurrence elements
Space complexity - O(k + d), d is the total number of distinct words in the file.
Implementation Classes : WordCountUsingMinHeap

---------------------------------------------------------------------------------------------------------------------------
Approach 2 :
Utilizing HashMap datastructure and applying imperative style of Java 8 programming. 
Utilizing latest feature added : Map.Entry.comparingByValue() method from the java.util.Map.Entry class. 

Implementation Classes : WordCountSingleFile, HashMapHelper classes to sort HashMap and return top k frequent occuring words

----------------------------------------------------------------------------------------------------------------------------
(Other approaches : If we have very limited memory in our device then we can utilize memory efficient data structures for 
storing words - TRIE and DAG.
TRIE - memory is shared between multiple words with common prefix)


----------------------------------------------------------------------------------------------------------------------------
Follow - up : Find the top k frequently occurring words from multiple files

Reading multiple files under folder with executor service in Java8. 
WordCountMultipleFiles class is multi-threaded as executor is able to use multiple simultaneous threads (2 threads in this case) 
FileReader is callable task to read file that is responsible for building individual file wordCount HashMap
Approach : Each thread has it's individual HashMap.
Then once future task is complete, results of the asynchronous tasks are combined in another finalResult hashmap.
