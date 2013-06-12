import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class generateGraph2 {
	static String graph;
	static String start;
	static String outgraph;
	static ArrayList<Integer> indexList;
	static int size1,size2,size3; 
	static int size2count;
	
	static Map<Integer,Integer> degreeList;
	static Map<Integer,Integer> nodehitList;
	static Map<Integer,Integer> nodehitList2;
	
	public generateGraph2(){
		graph = "";
		start = "";
		outgraph = "";
		indexList = new ArrayList<Integer>();
		//jedis = new Jedis("localhost",6379);
		degreeList = new HashMap<Integer,Integer>();
		nodehitList = new HashMap<Integer,Integer>();
		nodehitList2 = new HashMap<Integer,Integer>();
		size1 = 0;
		size2 = 0;
		size3 = 0;
		size2count = 0;
		}
	
	public void add(int index, ArrayList<Integer> result, Map<Integer,String> library ,String interest )
	{
		int count = 0;
		while (count + 1 <  result.size())
		{		
			int number = count+1;
			
			if (count == 0 )
			{
			graph += "Node" + index + " , Node" + result.get(0) 
					+ ", Weight: " + ( library.get(result.get(0)).length() + 10)+"\n"; 
			}
			else
			{
			graph += "Node" + result.get(count) + " , Node" + result.get(number) 
					+ ", Weight: " + library.get(result.get(number)).length() +"\n"; 
			graph += "Node" + result.get(number) + " , Node" + result.get(count) 
					+ ", Weight: 0 \n"  ; 
			}
			
			if (number+1 == result.size())
			{
			graph += "Node" + index + " , Node" + index + "'"
					+ ", Weight: " + interest.length() + "\n";
			}
			
			count ++;
		}
		
		count = 0;
		while (count < result.size())
		{
		if (degreeList.containsKey(result.get(count)))
		{
			int degree = degreeList.get(result.get(count));
			degree += 1;
			degreeList.remove(result.get(count));
			degreeList.put(result.get(count),degree);
		}
		else
		{
			degreeList.put(result.get(count),1);
		}
		
		if (!nodehitList.containsKey(result.get(count)))
		{
			nodehitList.put(result.get(count), 0);
		}
		if (!nodehitList2.containsKey(result.get(count)))
		{
			nodehitList2.put(result.get(count), 0);
		}
		count++;
		}
	}
	
	public void addIndex(int index)
	{
	indexList.add(index);
	}
	
	public void printGraph()
	{
	int count = 0;
	while (count < indexList.size())
	{
	start += "Connector" + " , Node" + indexList.get(count) + " , weight = 0\n";
	count++;
	}

	String degreeGraph = degreeList.toString();

	try {
	FileWriter fstream = new FileWriter("graph.txt");
	BufferedWriter outfile = new BufferedWriter(fstream);
	System.out.println(degreeGraph);
	System.out.println("Size1 = " + size1 + "\n");
	System.out.println("Size2 = " + size2 + "\n");
	System.out.println("Size3 = " + size3 + "\n");
	outfile.write(start);
	outfile.write(graph);
	outfile.close();
	
	FileWriter fstream1 = new FileWriter("outgraph.txt");
	BufferedWriter outfile1 = new BufferedWriter(fstream1);
	outfile1.write(start);
	outfile1.write(outgraph);
	outfile1.close();
	}
	
	catch (IOException e)
	{
	System.err.println("ERROR: " + e.getMessage());
	}

	}
	
	public void weightGraph(int index, String interest, ArrayList<Integer> result, Map<Integer,String> library)
	{
		for (int level = 1; level < 4 ; level++)
		{
		if (level == 1)
		{
			int length = interest.length();
			size1 += length;
		}
		
		else if (level == 2)
		{
	
			if (checkList(result))
			{
			if (size2count == 0)
			{
			size2 += 6;
			size2count = 1;
			}
			
			int count = 0;
			while (count < result.size())
			{
			int indexnumber = result.get(count);
			if (nodehitList.get(indexnumber) == 0)
			{
			String word = library.get(indexnumber);
			int length = word.length();
			size2 += length;
			nodehitList.remove(indexnumber);
			nodehitList.put(indexnumber, 1);
			}
			count ++;
			}
			
			if (count == result.size())
			{
			size2count = 0;
			}
			}
			else
			{
				int length = interest.length();
				size2 += length;
			}
		}
		else if (level == 3)
		{
			if (checkList(result))
			{
				if (checkDegree(result))
				{
					for (int i = 0 ; i < result.size() ; i++)
					{
						int nodeid = result.get(i);
						if (nodehitList2.get(nodeid) == 0)
						{
							if (i == 0)
							{
								outgraph += "Node" + index + " , Node" + result.get(0) 
										+ ", Weight: " + ( library.get(result.get(0)).length() + 6 ) +"\n"; 
								size3 += library.get(result.get(0)).length() + 10;
							}
							else
							{
								outgraph += "Node" +  result.get(i-1)  + " , Node" + result.get(i) 
										+ ", Weight: " + library.get( result.get(i)).length() +"\n"; 
								outgraph += "Node" +  result.get(i)  + " , Node" + result.get(i-1) 
										+ ", Weight: 0 \n"; 
								size3 += library.get(result.get(i)).length();
							}
						}
					}
				}
			}
			else
			{
				outgraph += "Node" + index + " , Node" + index + "'" 
						+ ", Weight: " + interest.length()  + "\n";
				validPath(result,library);
				size3 += interest.length();
			}
		}
		
		}
	}
	
	public boolean checkList(ArrayList<Integer> result)
	{
		boolean valid = true;
		for (int i = 0; i < result.size() ; i++)
		{
			if (result.get(i) == 0)
			{
				return false;
			}
		}
		return valid;
	}
	
	public boolean checkDegree(ArrayList<Integer> result)
	{
		boolean valid = true;
		for (int i = 0; i < result.size() ; i++)
		{
			int hashnumber = result.get(i);
			if (degreeList.get(hashnumber) >= 2)
			{
				return valid;
			}
		}
		return false;
	}
	
	public void validPath(ArrayList<Integer> result,Map<Integer,String> library)
	{
		for (int i = 0; i < result.size() ; i++)
		{
			int indexnumber = result.get(i);
			if (nodehitList2.get(indexnumber) == 0)
			{
			nodehitList2.remove(indexnumber);
			nodehitList2.put(indexnumber, 1);
			}
		}
	}
	
}
