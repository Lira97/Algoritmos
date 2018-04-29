
import java.io.*;
import java.util.*;

public class PrimMST {
	
	public static void main(String[] args) throws FileNotFoundException {
		Graph graph = new Graph("Test.txt");
		System.out.println(graph.computeMST());
	}
}

class Graph {
	
	ArrayList<ArrayList<int[]>> vertices;
	boolean[] explored;
	Heap unexplored;
	int numberOfVertices;
	int numberOfEdges;
	
	public Graph(String inputFileName) throws FileNotFoundException 
	{
		Scanner in = new Scanner(new File(inputFileName));
		numberOfVertices = in.nextInt();
		numberOfEdges = in.nextInt();
		vertices = new ArrayList<ArrayList<int[]>>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add(new ArrayList<int[]>());
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int cost = in.nextInt();
			System.out.println(u);
			vertices.get(u - 1).add(new int[]{v, cost});
			vertices.get(v - 1).add(new int[]{u, cost});
		}
		in.close();
	}
	
	public int computeMST() {
		explored = new boolean[numberOfVertices];
		explored[0] = true;
		unexplored = new Heap();
		int sumCost = 0;
		for (int i = 1; i < numberOfVertices; i++) {
			int minCost = 1000000000;
			for (int[] edge : vertices.get(i)) {
				if (edge[0] == 1 && edge[1] < minCost) {
					minCost = edge[1];
				}
			}
			// System.out.println(i + 1);
			// System.out.println(minCost);
			unexplored.add(new int[]{i + 1, minCost});
		}
		for (int i = 1; i < numberOfVertices; i++) {
			int[] u = unexplored.poll();
			for (int[] edge : vertices.get(u[0] - 1)) {
				int v = edge[0];
				if (!explored[v - 1]) {
					int cost = edge[1];
					if (cost < unexplored.getMinCost(v)) {
						unexplored.update(new int[]{v, cost});
					}
				}
			}
			explored[u[0] - 1] = true;
			sumCost = sumCost + u[1];
		}
		return sumCost;
	}
}

class Heap {
	private ArrayList<int[]> heap;
	HashMap<Integer, Integer> indexMap;	
	public int check() {
		int code = 0;
		for (int i = 0; i < heap.size(); i++) {
			if (2 * i + 1 < heap.size() && 2 * i + 2 < heap.size()) {
				if (heap.get(i)[1] > heap.get(2 * i + 1)[1] || heap.get(i)[1] > heap.get(2 * i + 2)[1]) {
					code = 1;
				}
			}
		}
		return code;
	}
	
	
	public Heap() {
		heap = new ArrayList<int[]>();
		indexMap = new HashMap<Integer, Integer>();
	}
	
	public void add(int[] a) {
		int n = heap.size() - 1;
		heap.add(a);
		n++;
		while (n > 0 && heap.get((n-1)/2)[1] > heap.get(n)[1]) {
			Collections.swap(heap, (n-1)/2, n);
			int[] b = heap.get(n);
			indexMap.remove(b[0]);
			indexMap.put(b[0], n);
			n = (n-1)/2;
		}
		// System.out.println(a[0]);
		// System.out.println(n);
		indexMap.put(a[0], n);
	}
	
	public int[] poll() {
		if (heap.size() == 0) {
			return (int[]) null;
		}
		int[] smallest = heap.get(0);
		int n = heap.size() - 1;
		int i = 0;
		Collections.swap(heap, 0, n);
		heap.remove(n);
		indexMap.remove(smallest[0]);
		if (heap.size() > 0) {
			indexMap.remove(heap.get(0)[0]);
			indexMap.put(heap.get(0)[0], 0);
		}
		n--;
		boolean balanced = false;
		while (!balanced) {
			if (i * 2 + 1 > n) {
				balanced = true;
			} else if (i * 2 + 1 == n) {
				if (heap.get(i)[1] > heap.get(i * 2 + 1)[1]) {
					Collections.swap(heap, i, 2 * i + 1);
					indexMap.remove(heap.get(i)[0]);
					indexMap.remove(heap.get(i * 2 + 1)[0]);
					indexMap.put(heap.get(i)[0], i);
					indexMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
				}
				balanced = true;
			} else {
				if (heap.get(i)[1] <= heap.get(i * 2 + 1)[1] && heap.get(i)[1] <= heap.get(i * 2 + 2)[1]) {
					balanced = true;
				} else {
					if (heap.get(i * 2 + 1)[1] < heap.get(i * 2 + 2)[1]) {
						Collections.swap(heap, i, i * 2 + 1);
						indexMap.remove(heap.get(i)[0]);
						indexMap.remove(heap.get(i * 2 + 1)[0]);
						indexMap.put(heap.get(i)[0], i);
						indexMap.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
						i = i * 2 + 1;
					} else {
						Collections.swap(heap, i, i * 2 + 2);
						indexMap.remove(heap.get(i)[0]);
						indexMap.remove(heap.get(i * 2 + 2)[0]);
						indexMap.put(heap.get(i)[0], i);
						indexMap.put(heap.get(i * 2 + 2)[0], i * 2 + 2);
						i = i * 2 + 2;
					}
				}
			}
		}
		return smallest;
	}
	public boolean update(int[] vertexWithCost) {
		boolean updated = false;
		if (indexMap.containsKey(vertexWithCost[0])) {
			int index = indexMap.get(vertexWithCost[0]);
			heap.set(index, vertexWithCost);
			while (index > 0 && heap.get((index-1)/2)[1] > heap.get(index)[1]) {
				Collections.swap(heap, (index-1)/2, index);
				int[] b = heap.get(index);
				indexMap.remove(b[0]);
				indexMap.put(b[0], index);
				index = (index-1)/2;
			}
			indexMap.remove(vertexWithCost[0]);
			indexMap.put(vertexWithCost[0], index);
			updated = true;
		}
		return updated;
	}
	
	public int getMinCost(int vertex) {
		int index = indexMap.get(vertex);
		return heap.get(index)[1];
	}
	public int size() {
		return heap.size();
	}
}