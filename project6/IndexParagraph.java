package project6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class IndexParagraph {
	private static Map<Integer, Integer> library;
	private ArrayList<Integer> result;
	private int paragraphCount;
	private static ArrayList<Node> node_Library;
	private int zeroNodeFlag;
	private Jedis jedis;
	
	public IndexParagraph()
	{
	library = new HashMap<Integer,Integer>();
	paragraphCount = 0;
	zeroNodeFlag = 0;
	node_Library = new ArrayList<Node>();
	Jedis jedis = new Jedis("localhost",6379);
	
	}

	public Map<Integer,Integer> getLibrary()
	{
	return this.library;
	}
	
	public ArrayList<Node> getNodeLibrary()
	{
		return this.node_Library;
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
	
	public ArrayList<Integer> indexString(int mailID, String mailContent, NodeGraph graph)
	{
		// System.out.println("5");
		result = new ArrayList<Integer>();
		String idNumber = "///"+mailID+"";
		String content = mailContent.substring(idNumber.length()+1, mailContent.length());
		ArrayList<String> paragraphList = this.parseContent(content);
		
		//System.out.println("There are " + paragraphList.size() + " paragraphs.");
		//this.testPrint1(paragraphList);
		
		int index = 0;
		
		while (index < paragraphList.size())
		{
		int paragraphId = index;	
		String paragraph = paragraphList.get(index);
		
		int hashCode = getHashCode(paragraph);
		
		int checkResult = checkWord(hashCode,paragraph);
		
		if (checkResult != 1)
		{
			addToLibrary(paragraph , checkResult);
		}
		
		if (checkResult != 2)
		{
		result.add(index, hashCode);
		}
		else
		{
			result.add(index,0);
		}
		
		// System.out.println("7");
		if (checkResult == 0)
		{
			if (hashCode == 0)
			{
				zeroNodeFlag = 1;
			}
			Node new_Node = new Node(hashCode,paragraph);
			node_Library.add(new_Node);
		}
		else if (checkResult == 1)
		{
			int pos = getNodePos(hashCode);
			Node node = node_Library.get(pos);
			node.degreeIncrement();
		}
		else
		{
		
			if (zeroNodeFlag == 0)
			{
				Node new_Node = new Node(0,paragraph);
				node_Library.add(new_Node);
				zeroNodeFlag = 1;
			}
			else
			{
				int pos = getNodePos(0);
				Node node = node_Library.get(pos);			
				node.degreeIncrement();
			}
		}
		
		index++;
		}
		
		ArrayList<Node> NodeList = generateNodeList(result);
		
	//	System.out.println("Content is " + content + "Length is " + content.length());
		graph.connectNode(mailID, NodeList,content, content.length());
		
		return result;
		
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
	
	public void printNodeList()
	{
	for (int i = 0 ; i < node_Library.size() ; i++ )
	{
		Node node = node_Library.get(i);
		int degree = node.getDegree();
		System.out.println("Index:"+ node.getIndex() + ", Degree:" + degree );
	}
	}
	
	public int getNodePos(int hashCode)
	{
		int find = 0;
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
	
	public Node getNode(int position)
	{
		return node_Library.get(position);
	}
	
	public ArrayList<String> parseContent(String content)
	{
		String contentCopy = content;
		ArrayList<String> result = new ArrayList<String>();
		
		int count = 0;
		String paragraph = "";
		int newParagraphFlag = 0;
		while (count < contentCopy.length())
		{
			char ch = contentCopy.charAt(count);
			if (ch == '\n' && newParagraphFlag == 1)
			{
				paragraph += ch;
				result.add(paragraph);
				paragraphCount ++;
				paragraph = "";
				newParagraphFlag = 0;
			}
			else if (ch == '\n' && newParagraphFlag == 0)
			{
				paragraph += ch;
				newParagraphFlag = 1;
			}
			else
			{
				paragraph += ch;
				newParagraphFlag = 0;
			}
			count++;
		}
		
		if(paragraph.length() > 0)
		{
		result.add(paragraph);
		paragraphCount ++;
		paragraph = "";
		}
		return result;

	}
	
	
	public void testPrint1(ArrayList<String> content)
	{
		for (int i = 0; i < content.size(); i++)
		{
			System.out.println("Paragraph "+ i + " is:" + content.get(i));
		}
	}
	
	public void printLibrary()
	{
	System.out.println("--------CURRENT LIBRARY---------");
	Iterator iter = this.library.keySet().iterator();
	while (iter.hasNext())
	{
	int key = (Integer) iter.next();
	String value = jedis.get(key+"");
	System.out.println(key + " : " + value);
	}
	}
	
	
	public int getHashCode(String word)
	{
	//Here to change the size of HashCode
	return word.hashCode();	
	}

	public int checkWord(int HashCode, String word)
	{
		int result = -1;
		// word is not included , ok to be added into library
	if (!library.containsKey(HashCode))
	{
		result = 0;
		return result;	
	}
	else
	{
		String storeWord = jedis.get(HashCode+"");
	 // word is included
	 if (storeWord.equals(word))
	 {
		 result = 1;
		 return result;
	 }
	 // word is not included but has a same hashcode to some included words
	 else
	 {
		 result = 2;
		 return result;
	 }
	}
		 
	 
	}

	public void addToLibrary(String paragraph, int checkResult)
	{
		// word not included, no hash conflict,  add nomally
	if (checkResult == 0)
	{
		int hashCode = getHashCode(paragraph);
		library.put(hashCode, 1);
		jedis.set(hashCode+"", paragraph);
	}
	// word not included, hash conflict, add as 0
	else
	{
		if (library.containsKey(0))
		{
			String conflictParagraphList = jedis.get("0");
			conflictParagraphList += ",";
			conflictParagraphList += paragraph;
			library.remove(0);
			library.put(0, 1);
		//	jedis.set("0", conflictParagraphList);
			jedis.set("0", conflictParagraphList);
		}
		else
		{
		library.put(0, 1);
		jedis.set("0", paragraph);
		}
	}
	}


}
