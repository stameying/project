package project6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NodeGraph {

	private static ArrayList<Node> Node_Group;
	
	private static ArrayList<KeyNode> Key_Node_Group;
	
	private int size1,size2,size3;
	
	private static Map<Integer,Integer> nodehitList;
	
	public NodeGraph()
	{
		Node_Group = new ArrayList<Node>();
		Key_Node_Group = new ArrayList<KeyNode>();
		nodehitList = new HashMap<Integer,Integer>();
	}
	
	public void addNode(Node node)
	{
		if (!hasNode(node))
		{
		Node_Group.add(node);
		}
	}
	
	public void connectNode(int mailId, ArrayList<Node> node_list,String content, int content_lengt)
	{
		
		int count = 0;
		
		KeyNode key_node = new KeyNode("Key"+mailId+"", mailId, content, content_lengt, node_list);
		EndNode stop_node = new EndNode("End"+mailId+"", mailId);
		
		add_Key_Node(key_node);
		
		/*
		 * Connect key_node 
		 * */
		Node first_node = node_list.get(0);
		Map<Integer,Integer> edge1 = new HashMap<Integer,Integer>();
		int firstIndex = first_node.getIndex();
		int weight1 = first_node.getSize();
		edge1.put(firstIndex, weight1);
		key_node.addNext(edge1);
		
		/*
		 * Connect end_node
		 * */
		Node end_node = node_list.get(node_list.size()-1);
		Map<Integer,Integer> edge2 = new HashMap<Integer,Integer>();
		int endIndex = end_node.getIndex();
		edge1.put(endIndex, 0);
		stop_node.addNext(edge1);
		
		while (count < node_list.size())
		{
			
			if (node_list.size() > 1)
			{
			if (count == 0)
			{
				/*
				 * Only going backward
				 * */
				Node currentNode = node_list.get(count);
				Node nextNode = node_list.get(count+1);
				Map<Integer,Integer> edge = new HashMap<Integer,Integer>();
				int nextIndex = nextNode.getIndex();
				int weight = nextNode.getSize();
				edge.put(nextIndex, weight);
				currentNode.addNext(edge);
				
				currentNode.toKeyNode(key_node);
				
			}
			else if (count + 1 == node_list.size() )
			{
				Node currentNode = node_list.get(count);
				Node frontNode = node_list.get(count-1);
				Map<Integer,Integer> edge3 = new HashMap<Integer,Integer>();
				int frontIndex = frontNode.getIndex();
				edge3.put(frontIndex, 0);
				currentNode.addNext(edge3);
				
				// connect end_node
				currentNode.toEndNode(stop_node);
			}
			else
			{
				/*
				 * Going backward
				 * */
				Node currentNode = node_list.get(count);
				Node nextNode = node_list.get(count+1);
				Map<Integer,Integer> edge4 = new HashMap<Integer,Integer>();
				int nextIndex = nextNode.getIndex();
				int weight = nextNode.getSize();
				edge4.put(nextIndex, weight);
				currentNode.addNext(edge4);
				
				/*
				 * Going forward
				 * */
				Node frontNode = node_list.get(count-1);
				Map<Integer,Integer> edge5 = new HashMap<Integer,Integer>();
				int frontIndex = frontNode.getIndex();
				edge5.put(frontIndex, 0);
				currentNode.addNext(edge5);
			}
			
			}
			
			Node currentNode = node_list.get(count);
			addNode(currentNode);
			
			nodehitList.put(currentNode.getIndex(), 0);
			count++;
		}
	}
	
	public void add_Key_Node(KeyNode key_node)
	{
		if (!this.Key_Node_Group.contains(key_node))
		{
		this.Key_Node_Group.add(key_node);
		}
	}
	
	public boolean hasNode(Node node)
	{
		for (int i = 0; i < Node_Group.size(); i++ )
		{
			Node Checknode = Node_Group.get(i);
			if (Checknode.equals(node))
			{
				return true;
			}
		}
		return false;
	}

	
	public void printGraph()
	{
		
		int count = 0;
		while (count < Node_Group.size())
		{
			Node node = Node_Group.get(count);
			ArrayList<Map<Integer,Integer>> next = node.getNext();
			
			if (node.connect_start())
			{
			node.printStart();
			}
			
			
		
			for (int i = 0 ; i < next.size(); i ++)
			{
				Map nextMap = next.get(i);
				
				System.out.println("Node" + node.getIndex() + " , Node" + nextMap.keySet().toString() + " , weight=" + nextMap.values().toString());
			}
			

			if (node.connect_end())
			{
				node.printEnd();
			}
			
				count ++;
		
		}
		
		
	
	}
	
	public void show_key_nodes()
	{
		for(int i = 0 ; i < this.Key_Node_Group.size(); i++)
		{
			this.Key_Node_Group.get(i).printself();
		}
	}
	
	public void weightGraph1()
	{
		for(int i = 0 ; i < this.Key_Node_Group.size(); i++)
		{
			size1 += this.Key_Node_Group.get(i).getLength();
		}
		System.out.println("Size1 is " + size1);
	}
	
	public void weightGraph2()
	{
		for(int i = 0 ; i < this.Key_Node_Group.size(); i++)
		{
			size2 += this.Key_Node_Group.get(i).getSize();
			ArrayList<Node> nodeList = this.Key_Node_Group.get(i).get_nodeList();
			for (int j = 0; j < nodeList.size(); j++)
			{
				Node next_node = nodeList.get(j);
				if ( !node_hitted(next_node))
				{
					size2 += next_node.getSize();
					hit_node(next_node);
				}
			}
			
		}
		System.out.println("Size2 is " + size2);
	}
	
	public void weightGraph3()
	{
		for(int i = 0 ; i < this.Key_Node_Group.size(); i++)
		{
			KeyNode key_Node = this.Key_Node_Group.get(i);
			if ( checkNodeList(key_Node.get_nodeList()))
			{
				size3 += 5;
				for (int j = 0; j < key_Node.get_nodeList().size(); j++)
				{
					Node next_node = key_Node.get_nodeList().get(j);
					if ( !node_hitted(next_node))
					{
						size3 += next_node.getSize();
						hit_node(next_node);
					}
				}
				
			}
			else
			{
				size3 += key_Node.getLength(); 
			}
			
		}
		System.out.println("Size3 is " + size3);
	}
	
	public boolean checkNodeList(ArrayList<Node> node_list)
	{
	for (int i = 0; i < node_list.size() ; i++)
	{
		if (node_list.get(i).getDegree() >= 3)
		{
			return true;
		}
	}
	return false;
	}
	
	public boolean node_hitted(Node node)
	{
		int index = node.getIndex();
		if (nodehitList.get(index) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void hit_node(Node node)
	{
		nodehitList.remove(node.getIndex());
		nodehitList.put(node.getIndex(), 1 );
	}
	
	public int get_index(Map<Integer,Integer> edge)
	{
		return edge.keySet().iterator().next();
	}
	
	public int get_weight(Map<Integer,Integer> edge)
	{
		return edge.values().iterator().next();
	}
	
	public void test1()
	{
		int count = 0 ; 
		while (count < Node_Group.size())
		{
			Node_Group.get(count).printself();
			count ++;
		}
	}

}
