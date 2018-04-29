import java.util.*;
import java.io.*;

public class Prim{

	List<node> _Graph = new ArrayList<node>();
	int _total_Edges;
	int _total_nodes;

	public static void main(String[] args)
	{
		Prim g = new Prim();
		g.generate_graph();
		g.Prim();
		g.Prim_heap();
	}

	void generate_graph()
	{
		try
		{
			File _file = new File("P2Edges.txt");

		    Scanner file_read = new Scanner(_file);

	        _total_nodes = file_read.nextInt();
	        _total_Edges = file_read.nextInt();
	        int temp_int, temp_node, temp_edge, current = 0;

	        for(int i = 0; i < _total_Edges; i++)
	        {
	        	temp_int = file_read.nextInt();
	        	temp_node = file_read.nextInt();
	        	temp_edge = file_read.nextInt();

	    		if(!check_exists(temp_int))
	    		{
	    			_Graph.add(new node(temp_int));
	    			_Graph.get(current).insert_node(temp_node);
	    			_Graph.get(current).insert_edge(temp_edge);
	   			}
	   			if(!check_exists(temp_node))
	   			{
   					_Graph.add(new node(temp_node));
	    			_Graph.get(temp_int).insert_node(temp_int);
	    			_Graph.get(temp_int).insert_edge(temp_edge);
	   			}
	    		else
	    		{
	    			for(int j = 0; j < _Graph.size(); j++)
	    			{
	    				if(_Graph.get(j).get_value() == temp_int)
	    				{
			    			_Graph.get(j).insert_node(temp_node);
			    			_Graph.get(j).insert_edge(temp_edge);
		    			}
		    			else if ( _Graph.get(j).get_value() == temp_node)
		    			{
		    				_Graph.get(j).insert_node(temp_int);
			    			_Graph.get(j).insert_edge(temp_edge);
		    			}
		    		}
	    		}
	    		current += 2;
	        }

	        file_read.close();
    	}
    	catch(FileNotFoundException e){e.printStackTrace();}
	}

	boolean check_exists(int node)
	{
		int temp_graph;
		for(int i = 0; i < _Graph.size();i++)
		{
			temp_graph = _Graph.get(i).get_value();
			if(temp_graph == node)
				return true;
		}
		return false;
	}

	void Prim()
	{
		long startTime = System.currentTimeMillis();
        int current = 0, _min = 0, temp_min = 0;

        for (int i = 0;i < _total_nodes - 1; i++) 
        { 
            if(!_Graph.get(i).isVisited())
            {
                _min += _Graph.get(i).find_min_edge();
                                current = i;
			}
			 _Graph.get(current).set_visited(true);
		}
        
        System.out.println("\n Minimum cost (Prim) = " + _min);
        long endTime = System.currentTimeMillis();
        System.out.print("\n Time of execution: " + (endTime - startTime) + " milliseconds");
	}

		void Prim_heap()
	{
		long startTime = System.currentTimeMillis();
        int current = 0, _min = 0, temp_min = 0;

        for (int i = 0;i < _total_nodes - 1; i++) 
        { 
            if(!_Graph.get(i).isVisited())
            {
                _min += _Graph.get(i).find_min_edge_heap();
                current = i;
			}
			 _Graph.get(current).set_visited(true);
		}
        
        System.out.println("\n Minimum cost (Prim heap) = " + _min);
        long endTime = System.currentTimeMillis();
        System.out.print("\n Time of execution: " + (endTime - startTime) + " milliseconds");
	}

}

class node
{
	int _value;
	List<Integer> _nodes_connected = new ArrayList<Integer>();
	List<Integer> _edges_connected = new ArrayList<Integer>();
	boolean _visited = false;

	node(int value){ _value = value;}

	void insert_node(int node){_nodes_connected.add(node);}

	void insert_edge(int edge){_edges_connected.add(edge);}

	int get_value(){return _value;}

	boolean isVisited(){return _visited;}

	void set_visited(boolean visited){_visited = visited;}

	int find_min_edge()
	{
		int min = _edges_connected.get(0);

		for(int i = 0; i < _edges_connected.size(); i++)
		{
			if(min > _edges_connected.get(i)){
				min = _edges_connected.get(i);
			}
		}

		return min;
	}

	int find_min_edge_heap()
	{

		HeapSort heap = new HeapSort();

		heap.sort(_edges_connected);

		return _edges_connected.get(_edges_connected.size() - 1);
	}
}
 
class HeapSort 
{    
    private static int N;

    void sort(List<Integer> _Edges)
    {       
        heapify(_Edges);        
        for (int i = N; i > 0; i--)
        {
            swap(_Edges,0, i);
            N = N-1;
            maxheap(_Edges, 0);
        }
    }     
  
    void heapify(List _Edges)
    {
        N = _Edges.size()-1;
        for (int i = N/2; i >= 0; i--)
            maxheap(_Edges, i);        
    }
        
	void maxheap(List<Integer> _Edges, int i)
    { 
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && _Edges.get(left) > _Edges.get(i))
            max = left;
        if (right <= N && _Edges.get(right) > _Edges.get(max))        
            max = right;
 
        if (max != i)
        {
            swap(_Edges, i, max);
            maxheap(_Edges, max);
        }
    }    

    void swap(List<Integer> _Edges, int i, int j)
    {
        int tmp = _Edges.get(i);
        _Edges.set(i,_Edges.get(j));
        _Edges.set(j, tmp); 
    }     
}

