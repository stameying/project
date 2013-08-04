package project6;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project5.IndexParagraph;
import project5.IndexPath;
import project5.InputReader;
import project5.NodeGraph;
import project5.normalizeAgent;
import project5.task5;
import redis.clients.jedis.Jedis;

public class task6 {
	public static void main(String[] agrs) throws IOException
	{
		Jedis jedis = new Jedis("localhost",6379);
		
		task6 test = new task6();
		InputReader ir = new InputReader();
		ir.read("wiki.txt");
		ArrayList<String> content = ir.getContent();
		ArrayList<String> title = ir.getTitles();
		IndexPath indexPath = new IndexPath();
		
		IndexParagraph getParagraph = new IndexParagraph();
		
		normalizeAgent normalAgent = new normalizeAgent();
		
		NodeGraph graph = new NodeGraph();
		
		//System.out.println(content.size());
		long startTime=System.currentTimeMillis();  
		
		for (int i = 0; i < content.size() ; i++)
		{
			String mailIndex = title.get(i);
			String mailContent = content.get(i);
	
			//System.out.println(mailContent);
			ArrayList<Integer> result = getParagraph.indexString(i,mailContent,graph);

			Map<Integer,String> library = new HashMap<Integer,String>();
		    library = getParagraph.getLibrary();

		    indexPath.getPath(i ,result);
			
		}
		//indexPath.printPath();
		//graph.printGraph();
		//graph.show_key_nodes();
		graph.weightGraph1();
		graph.weightGraph2();
		graph.weightGraph3();
		getParagraph.printNodeList();
		//getParagraph.printLibrary();
		long endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
	}
}
