import java.io.IOException;
import java.util.*;

import redis.clients.jedis.Jedis;

public class project1 {

	private static Map<String, String> library;
	
	private static Map<String, String> redis_copy;
	
	private static ArrayList<Node> node_Library;
	
	public project1()
	{
		library = new HashMap<String,String>();
		redis_copy = new HashMap<String,String>();
		node_Library = new ArrayList<Node>();
	}
	
	public void run(Jedis jedis , ArrayList<String> MailsContent , NodeGraph graph , IndexPath indexPath )
	{
		for (int count = 0; count < MailsContent.size(); count++)
		{
		/*
		 * Get Mail Content
		 * */
		String SingleMailContent = MailsContent.get(count);
		
		/*
		 * Store MailID - MailContengt into Redis
		 * */
		int index = SingleMailContent.hashCode();
		String key = Integer.toString(index);
		jedis.set(key,SingleMailContent);
		library.put(key, SingleMailContent);
		redis_copy.put(key, SingleMailContent);
		/*
		 * Parse Mail
		 * */
		ArrayList<String> ParsedMail = this.parseContent(SingleMailContent);
		ArrayList<Integer> ParagraphIDList = this.indexParagraph(index,SingleMailContent,ParsedMail, graph, index);
		
		indexPath.getPath(count ,ParagraphIDList);
		}
	}
	
	public void compress(Jedis jedis  , NodeGraph graph , int index)
	{
		
		/*
		 *  
		 * */
		if ( index == 1)
		{
		//	graph.weightGraph1();
		//	graph.weightGraph2();
		//	graph.weightGraph3();
		}
		
		/*
		 * Always take path
		 * */
		else if ( index == 2)
		{
			ArrayList<KeyNode> KeyNodeGroup = graph.GetKeyNodeGroup();
			for (int pos = 0 ; pos < KeyNodeGroup.size(); pos++)
			{
				KeyNode key_Node = KeyNodeGroup.get(pos);
				String NodeKeyValue = key_Node.getKey();
				jedis.del(NodeKeyValue);
				//System.out.println("Key is " + NodeKeyValue);
				library.remove(NodeKeyValue);
				redis_copy.remove(NodeKeyValue);
				ArrayList<Integer> MailContentPath = key_Node.get_path();
				
				for (int i = 0 ; i < MailContentPath.size() ; i++)
				{
					String Key = Integer.toString(MailContentPath.get(i));
					String Value = library.get(Key);
					jedis.set(Key, Value);
					//System.out.println("Key is" + Key);
					//System.out.println("Value is" + Value);
					redis_copy.put(Key, Value);
				}
			}
		
		}
		/*
		 * 
		 * */
		else
		{
			ArrayList<KeyNode> Key_Node_Group = graph.GetKeyNodeGroup();
			for(int i = 0 ; i < Key_Node_Group.size(); i++)
			{
				KeyNode key_Node = Key_Node_Group.get(i);
				ArrayList<Integer> MailContentPath = key_Node.get_path();
				
				if ( graph.checkNodeList(key_Node.get_nodeList()))
				{
					String NodeKeyValue = key_Node.getKey();
					jedis.del(NodeKeyValue);
					//System.out.println("Key is " + NodeKeyValue);
					library.remove(NodeKeyValue);
					redis_copy.remove(NodeKeyValue);
					
					for (int j = 0; j < key_Node.get_nodeList().size(); j++)
					{
						Node next_node = key_Node.get_nodeList().get(j);
						if ( !graph.node_hitted(next_node))
						{
							graph.hit_node(next_node);
							String Key = Integer.toString(MailContentPath.get(i));
							String Value = library.get(Key);
							jedis.set(Key, Value);
							//System.out.println("Key is" + Key);
							//System.out.println("Value is" + Value);
							redis_copy.put(Key, Value);
						}
					}
					
				}
			}
				
		}
		
		System.out.println ( Get_Redis_Size(jedis, graph, index) ) ;
	}
	
	public int Get_Redis_Size(Jedis jedis  , NodeGraph graph , int index)
	{
		int size = 0 ; 
		
		/*
		 *  
		 * */
		if ( index == 1)
		{
			ArrayList<KeyNode> Key_Node_Group = graph.GetKeyNodeGroup();
			for(int i = 0 ; i < Key_Node_Group.size(); i++)
			{
				size += Key_Node_Group.get(i).getLength();
			}
		}
		
		/*
		 * Always take path
		 * */
		else if ( index == 2)
		{
			ArrayList<KeyNode> Key_Node_Group = graph.GetKeyNodeGroup();
			for(int i = 0 ; i < Key_Node_Group.size(); i++)
			{
				size += Key_Node_Group.get(i).getSize();
				ArrayList<Node> nodeList = Key_Node_Group.get(i).get_nodeList();
				for (int j = 0; j < nodeList.size(); j++)
				{
					Node next_node = nodeList.get(j);
					if ( !graph.node_hitted(next_node))
					{
						size += next_node.getSize();
						graph.hit_node(next_node);
					}
				}
				
			}
		}
		/*
		 * 
		 * */
		else
		{
			ArrayList<KeyNode> Key_Node_Group = graph.GetKeyNodeGroup();
			//System.out.println("KeySize is " + Key_Node_Group.size());
			graph.node_hit_clear();
			for(int i = 0 ; i < Key_Node_Group.size(); i++)
			{
				
				KeyNode key_Node = Key_Node_Group.get(i);
				if ( graph.checkNodeList(key_Node.get_nodeList()))
				{
					size += 5;
					 // System.out.println("size3 is " + size);
					for (int j = 0; j < key_Node.get_nodeList().size(); j++)
					{
						Node next_node = key_Node.get_nodeList().get(j);
						if ( !graph.node_hitted(next_node))
						{
					//		System.out.println(adjustContent(next_node.getContent()));
					//		System.out.println("size3 =" + size + " + " + next_node.getSize() );
							size += next_node.getSize();
							graph.hit_node(next_node);
						}
					}
					
				}
				else
				{
				//System.out.println("size3 =" + size + " + " + key_Node.getLength()) ;
					size += key_Node.getLength(); 
				}
				
			}
		}
		
		
		return size;
	}
	
	public ArrayList<String> parseContent(String content)
	{
		int newParagraphFlag = 0;
		String paragraph = "";
		ArrayList<String> ParagraphList = new ArrayList<String>();
		int count= 0 ;
		while (count < content.length())
		{
			char character = content.charAt(count);
			if (character == '\n' && newParagraphFlag == 1)
			{
				paragraph += character;
				ParagraphList.add(paragraph);
				paragraph = "";
				newParagraphFlag = 0;
			}
			else if (character == '\n' && newParagraphFlag == 0)
			{
				paragraph += character;
				newParagraphFlag = 1;
			}
			else
			{
				paragraph += character;
				newParagraphFlag = 0;
			}
			count++;
		}
		
		if(paragraph.length() > 0)
		{
		ParagraphList.add(paragraph);
		paragraph = "";
		}
		return ParagraphList;
	}
	
	public ArrayList<Integer> indexParagraph(int mailID, String content, ArrayList<String> MailContentList, NodeGraph graph, int Key_Index)
	{
		ArrayList<Integer> ParagraphIDList = new ArrayList<Integer>();
		
		int count = 0 ; 
		
		while (count < MailContentList.size())
		{
			String paragraph = MailContentList.get(count);
			int HashNumber = paragraph.hashCode();
			String Key = Integer.toString(HashNumber);		
			/*
			 * Key doesnt exist
			 * */
			if (!library.containsKey(Key))
			{
				library.put(Key, paragraph);
				Node new_Node = new Node(HashNumber,paragraph);
				node_Library.add(new_Node);
			}
			else
			{
				int pos = getNodePos(HashNumber);
				Node node = node_Library.get(pos);
				node.degreeIncrement();
			}
			ParagraphIDList.add(HashNumber);
			count++;
		}
		
		ArrayList<Node> NodeList = generateNodeList(ParagraphIDList);
		
		graph.connectNode(mailID, NodeList,content, content.length(),ParagraphIDList);
		
		return ParagraphIDList;
	}
	
	public ArrayList<Node> generateNodeList(ArrayList<Integer> nodeIndex)
	{
		ArrayList<Node> NodeList = new ArrayList<Node>();
		
		int count = 0;
		
		while (count < nodeIndex.size())
		{
			int index = nodeIndex.get(count);
			int pos = getNodePos(index);
			Node node = getNode(pos);
			NodeList.add(node);
			count++;
		}
		
		return NodeList;
	}
	
	public Node getNode(int position)
	{
		return node_Library.get(position);
	}
	
	public int getNodePos(int hashCode)
	{
		int count = 0;
		
		while (count < node_Library.size() )
		{
		Node checkNode  = node_Library.get(count);
		
		if (checkNode.getIndex() == hashCode)
		{
			return count;
		}
		count ++;
		}
		
		if (count == node_Library.size())
		{
			System.out.println("Node not found. HashCode is "+ hashCode);
		}
		return -1;
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
	
	public void PrintLibrary()
	{
		System.out.println("--------------------------");
		System.out.println("Library");
		Iterator<String> it = library.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String value = library.get(key);
			System.out.print(key + " : " + value);
		}
		System.out.println("--------------------------");
	}
	

	public void PrintRedis()
	{
		System.out.println("--------------------------");
		System.out.println("Redis Library");
		Iterator<String> it = redis_copy.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			String value = redis_copy.get(key);
			System.out.print(key + " : " + value);
		}
		System.out.println("--------------------------");
	}
	
	public static void main(String[] agrs) throws IOException
	{
		Jedis jedis = new Jedis("localhost",6379);
		
		project1 project = new project1();
		
		InputReader ir = new InputReader(jedis);
		
		ArrayList<String> MailsContent = ir.readFile("test5.txt");
		
		//System.out.println("Size = " + MailsContent.size());

		IndexPath indexPath = new IndexPath();
		
		NodeGraph graph = new NodeGraph();
		
		project.run(jedis , MailsContent  , graph , indexPath);
		
		project.compress(jedis, graph, 2);
				
		//project.PrintLibrary();
		
		//project.PrintRedis();
		
		//indexPath.printPath();
		
		//graph.printGraph();
		
		//graph.test1();
	}	
	
	
}
