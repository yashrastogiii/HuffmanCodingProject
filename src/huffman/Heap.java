package huffman;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> data = new ArrayList<>();

    public void insert(T item) {
        data.add(item);
        upHeapify(data.size() - 1);
    }

    public T remove() {
        swap(0, data.size() - 1);
        T removed = data.remove(data.size() - 1);
        downHeapify(0);
        return removed;
    }

    public int size() {
        return this.data.size();
    }

    private void upHeapify(int ci) {
        int pi = (ci - 1) / 2;

        if (data.get(ci).compareTo(data.get(pi)) < 0) {
            swap(ci, pi);
            upHeapify(pi);
        }
    }

    private void downHeapify(int pi) {
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;

        int mini = pi;

        if (lci < data.size() && data.get(lci).compareTo(data.get(mini)) < 0) {
            mini = lci;
        }

        if (rci < data.size() && data.get(rci).compareTo(data.get(mini)) < 0) {
            mini = rci;
        }

        if (mini != pi) {
            swap(pi, mini);
            downHeapify(mini);
        }
    }

    private void swap(int i, int j) {
        T ith = data.get(i);
        T jth = data.get(j);
        data.set(i, jth);
        data.set(j, ith);
    }
}
