import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int n = 1;

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (n <= k) {
                q.enqueue(item);
            }
            else if (StdRandom.uniform(n) < k) {
                q.dequeue();
                q.enqueue(item);
            }
            n++;
        }

        for (String s: q) {
            StdOut.println(s);
        }
    }
}
