package project6;

import java.util.ArrayList;
import java.util.Map;

public class EndNode {
	
	private ArrayList<Map<Integer,Integer>> next;
	 
	private int index;
	
	private String name;
	
	private int size; 
	
	public EndNode(String node_name, Integer id)
	{
		this.name = node_name;
		next = new ArrayList<Map<Integer,Integer>>();
		this.size = 0;
		this.index = id;
	}
	
	public void addNext(Map<Integer,Integer> edge)
	{
		if (!includeEdge(edge))
		{
			if (edge.keySet().iterator().next() != this.index)
			{
			this.next.add(edge);
			}
		}
	}

	
	public boolean includeEdge(Map<Integer,Integer> edge)
	{
	return this.next.contains(edge);
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public  ArrayList<Map<Integer,Integer>> getNext()
	{
		return this.next;
	}
	public String getName()
	{
		return this.name;
	}

}
