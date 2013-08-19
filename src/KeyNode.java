import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyNode {
	
	private ArrayList<Map<Integer,Integer>> next;
	 
	private int index;
	
	private String name;
	
	private int size; 
	
	private Map<EndNode,Integer> end;
	
	private String content;
	
	private int contentLength;
	
	private ArrayList<Node> nodeList;
	
	private ArrayList<Integer> path;
	
	public KeyNode(String node_name, Integer id, String Incontent, int content_length, ArrayList<Node> node_list, ArrayList<Integer> key_path)
	{
		this.name = node_name;
		next = new ArrayList<Map<Integer,Integer>>();
		this.size = 5;
		this.index = id;
		this.content = Incontent;
		EndNode stop_node = new EndNode("End"+id+"", id);
		this.contentLength = content_length;
		this.end = new HashMap<EndNode,Integer>();
		this.end.put(stop_node, contentLength);
		this.nodeList = node_list;
		this.path = key_path;
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
	public String getContent()
	{
		return this.content;
	}
	
	public int getLength()
	{
		return this.contentLength;
	}
	
	public ArrayList<Node> get_nodeList()
	{
		return this.nodeList;
	}
	
	public ArrayList<Integer> get_path()
	{
		return this.path;
	}
	
	public String getKey()
	{
		return this.name;
	}
	
	public void printself()
	{
		System.out.println(this.name + " , " + this.end.keySet().iterator().next().getName() + " , weight =" + getLength());
	}

	public String adjustContent(String paragraph)
	{
		String ADcontent = "";
		int count = 0;
		while (count < paragraph.length())
		{
			char ch = paragraph.charAt(count);
			if (ch == '\n')
			{
				ch = '*';
			}
			ADcontent+=ch;
			count++;
		}
		return ADcontent;
	}
}
