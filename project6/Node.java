package project6;
import java.util.ArrayList;
import java.util.Map;


public class Node {

	private int degree;
	
	private ArrayList<Map<Integer,Integer>> next;
	
	private String content;
	
	private int index;
	
	private String name;
	
	private int size; 
	
	private ArrayList<KeyNode> to_start_next;
	
	private ArrayList<EndNode> to_end_next;
	
	public Node(int hashCode, String content )
	{
		this.degree = 1;
		this.index = hashCode;
		next = new ArrayList<Map<Integer,Integer>>();
		this.size = content.length();
		to_start_next = new ArrayList<KeyNode>();
		to_end_next = new ArrayList<EndNode>();
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
	
	public boolean connect_start()
	{
		if (to_start_next.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean connect_end()
	{
		if (to_end_next.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean include_key(KeyNode key_node)
	{
		return to_start_next.contains(key_node);
	}
	
	public boolean include_end(EndNode stop_node)
	{
		return to_end_next.contains(stop_node);
	}
	
	public boolean includeEdge(Map<Integer,Integer> edge)
	{
	return this.next.contains(edge);
	}
	
	public boolean checkNext(Map<Integer,Integer> edge)
	{
		boolean result = false;
		int i  = 0;
		while (i < this.next.size())
		{
			if (this.next.contains(edge))
			{
				return true;
			}
		}
		return result;
	}
	
	public void degreeIncrement()
	{
	this.degree++;	
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public int getDegree()
	{
		return this.degree;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public  ArrayList<Map<Integer,Integer>> getNext()
	{
		return this.next;
	}
	
	public void toKeyNode(KeyNode key_node)
	{
		if (!include_key(key_node))
		{
		to_start_next.add(key_node);
		}
	}
	
	public void toEndNode(EndNode stop_node)
	{
		if (!include_end(stop_node))
		{
			to_end_next.add(stop_node);
		}
	}
	
	public boolean equals(Node node)
	{
	if (this.index == node.index)
	{
		return true;
	}
	return false;
	}
	
	public ArrayList<KeyNode> getStart()
	{
		return this.to_start_next;
	}
	
	public ArrayList<EndNode> getEnd()
	{
		return this.to_end_next;
	}
	
	public void printStart()
	{
		for (int i = 0; i < to_start_next.size(); i++)
		{
			KeyNode key_node = to_start_next.get(i);
			System.out.println(key_node.getName() + " , Node" + this.index + " , weight = 0");
			key_node.printself();
		}
	}
	
	public void printEnd()
	{
		for (int i = 0; i < to_end_next.size(); i++)
		{
			EndNode stop_node = to_end_next.get(i);
			System.out.println("Node" + this.index + " , " + stop_node.getName() + " , weight = 0");
		}
	}
	
	public void printself()
	{
		System.out.println("I'm node:" + this.index);
		System.out.println("I have a degree of "+ this.degree);
		System.out.println("Out: " + this.next.toString());
	}
	
}
