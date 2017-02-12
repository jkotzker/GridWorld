package minHeap;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import com.gridworld.algorithm.Vertex;

public class minHeap {

	/* Mode: min or max heap */
	final static int MIN_HEAP = 1;
	final static int MAX_HEAP = 2;

	private ArrayList<Vertex> items;

	public minHeap() {
		items = new ArrayList<Vertex>();
	}

	private void siftUp() {
		int k = items.size() - 1; // index of last item (just inserted)
		while (k > 0) {
			int p = (k - 1) / 2; // k's parent index
			Vertex kid = items.get(k);
			Vertex parent = items.get(p);
			if (kid.compareTo(parent) < 0) {
				// swap kid with parent
				items.set(k, parent);
				items.set(p, kid);
				// move up one level
				k = p;
			} else {
				break;
			}
		}
	}

	public void insert(Vertex item) {
		items.add(item);
		siftUp();
	}

	private void siftDown() {
		int k = 0; // root index
		int l = 2 * k + 1; // left child index
		while (l < items.size()) {
			int r = l + 1; // right child index
			int max = l;
			if (r < items.size() && (items.get(r).compareTo(items.get(l)) < 0)) {
				max = r;
			}
			if (items.get(max).compareTo(items.get(k)) < 0) {
				// swap
				Vertex parent = items.get(k);
				items.set(k, items.get(max));
				items.set(max, parent);
				// move one level down
				k = max;
				l = 2 * k + 1;
			} else {
				break;
			}
		}
	}

	public Vertex delete() {

		if (items.size() == 0) {
			throw new NoSuchElementException();
		}
		if (items.size() == 1) {
			return items.remove(0);
		}
		Vertex root = items.get(0); // get the root
		Vertex lastItem = items.remove(items.size() - 1);
		items.set(0, lastItem); // move the last item into the root
		siftDown();
		return root;
	}

	public void delete(Vertex V) {

		if (items.size() == 0) {
			throw new NoSuchElementException();
		}
		if (items.size() == 1) {
			items.remove(0);
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == V) {
				items.remove(i);
				Vertex lastItem = items.remove(items.size() - 1);
				items.set(i, lastItem);
				siftDown();
			}
		}
		return;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
	public void print(){
		ArrayList<Vertex> temp = this.items;
		for(int i = 1; i<Math.pow(2,this.items.size()); i = 2*i){
			for(int j = 0; j<i; j++){
				System.out.print(this.delete().g+this.delete().g + " ");
			}
			System.out.println("");
		}
		
	}
}