class main {
	public static void main(String[] args) throws Exception 
		{
			Graph graph = new Graph(); 
			graph.kruskalDFS("P2Edges.txt");	
			//graph.kruskalUF("P2Edges.txt");
			graph.prim("P2Edges.txt");	
					
		}

}


