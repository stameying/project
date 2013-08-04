package project6;

public class Edge {

	private int edge_index;
	private int edge_weight;
	
	public Edge(Integer index, Integer weight)
	{
		this.edge_index = index;
		this.edge_weight = weight;
	}
	
	public int get_index()
	{
		return edge_index;
	}
	
	public int get_weight()
	{
		return edge_weight;
	}
}
