import java.util.*;
import java.io.*;

public class Kruskal {
    
    public static void main(String[] args)
    {
    
        long startTime = System.currentTimeMillis();
        int[] Array = readFiles("Test.txt");

    	int v = Array[0];
    	int e = Array[1];	

        Graph graph = new Graph(e);

    	for(int i = 1; i <= e; i++)
        {
    	   graph.addVertex(i);
    	}
    	
    	for(int j = 2; j <= (v*3);j = j+3)
        {
    	   graph.addEdge(Array[j], Array[j+1], Array[j+2]);
    	}

        graph.applyKrushkalAlgo();

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println(" ");
        System.out.println("Miliseconds: ");
        System.out.println(totalTime);
    }

  public static int[] readFiles(String file){
    
    try
    {
        File f = new File(file);
        Scanner s = new Scanner(f);
        int ctr = 0;
        
        while (s.hasNextInt())
        {
            ctr++;
            s.nextInt();
        }
        
        int[] Array = new int [ctr];
        Scanner s1 = new Scanner(f);

        for (int i = 0; i < Array.length; i++){
            Array[i] = s1.nextInt();
            System.out.println(Array[i]);    
        }
        return Array;
    }
    catch(Exception e){return null;}
  }


    public static class Graph {
        Vertex[] vertices;
        Edge edgeList;
        int maxSize;
        int size;
        int edgeNum;

        public Graph(int maxSize) {
            this.maxSize = maxSize;
            vertices = new Vertex[maxSize];
        }

        public class Vertex {
            int rank;
            Vertex representative;
            int name;
            Neighbour adj;

            Vertex(int name) {
                this.name = name;
                representative = this; // makeset
            }
        }

        public class Neighbour {
            int index;
            Neighbour next;
            int weight;

            Neighbour(int index, int weight, Neighbour next) {
                this.index = index;
                this.weight = weight;
                this.next = next;
            }
        }

        public class Edge {
            Vertex src;
            Vertex desti;
            Edge next;
            int weight;

            Edge(Vertex src, Vertex desti, int weight, Edge next) {
                this.src = src;
                this.desti = desti;
                this.weight = weight;
                this.next = next;
            }
        }

        public void addVertex(int name)
        {
            vertices[size++] = new Vertex(name);
        }

        public void addEdge(int src, int dest, int weight)
        {
            vertices[src - 1].adj = new Neighbour(dest - 1, weight, vertices[src - 1].adj);
            edgeList = new Edge(vertices[src - 1], vertices[dest - 1], weight, edgeList);
            edgeNum++;
        }

        public void applyKrushkalAlgo() 
        {
            Edge[] edges = new Edge[edgeNum];
            int i = 0;
            int tot_weight = 0;
            
            while (edgeList != null) 
            {
                edges[i] = edgeList;
                i++;
                edgeList = edgeList.next;
            }
            
            quicksort(edges, 0, edgeNum - 1);
            
            for (i = 0; i < edgeNum; i++) {
                Vertex u = findSet(edges[i].src);
                Vertex v = findSet(edges[i].desti);
                int minWeight = 0;
                
                if (u != v)
                {
                   union(u, v);
		           int weight = edges[i].weight;
		           tot_weight += weight;		    
                }
	
            }

	        System.out.println("Minimum Weight: "+tot_weight);
        }

        public Vertex findSet(Vertex u) {
            if (u.representative != u) {
                u.representative = findSet(u.representative); // path compression
            }
            return u.representative;
        }

        public void union(Vertex u, Vertex v) {
            if(u.rank == v.rank)
            {
                v.representative = u;
                u.rank++;
            }
            else if(u.rank < v.rank)
            {
                v.representative = u;
            }
            else
            {
                u.representative = v;
            }
        }

        public void quicksort(Edge[] edges, int start, int end)
        {
            if (start < end) 
            {
                swap(edges, end, start + (end - start) / 2);
                int pIndex = pivot(edges, start, end);
                quicksort(edges, start, pIndex - 1);
                quicksort(edges, pIndex + 1, end);
            }
        }

        public int pivot(Edge[] edges, int start, int end)
        {
            int pIndex = start;
            Edge pivot = edges[end];
            for (int i = start; i < end; i++)
            {
                if (edges[i].weight < pivot.weight) 
                {
                    swap(edges, i, pIndex);
                    pIndex++;
                }
            }
            swap(edges, end, pIndex);
            return pIndex;
        }

        public void swap(Edge[] edges, int index1, int index2) 
        {
            Edge temp = edges[index1];
            edges[index1] = edges[index2];
            edges[index2] = temp;
        }
    }
}