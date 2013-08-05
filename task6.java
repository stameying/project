import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class task6 {
	
	public task6(){
		
	}
	
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
	
		long startTime=System.currentTimeMillis();  
		
		for (int i = 0; i < content.size() ; i++)
		{
			String mailIndex = title.get(i);
			String mailContent = content.get(i);
			ArrayList<Integer> result = getParagraph.indexString(i,mailContent,graph);
			Map<Integer,String> library = new HashMap<Integer,String>();
		    indexPath.getPath(i ,result);
		}
		graph.weightGraph1();
		graph.weightGraph2();
		graph.weightGraph3();
		graph.test2();
		getParagraph.printNodeList();
		long endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
		
		startTime=System.currentTimeMillis();  
		for (int i = 0; i < content.size() ; i++)
		{
			String mailIndex = title.get(i);
			String mailContent = content.get(i);
			ArrayList<Integer> result = getParagraph.indexString(i,mailContent,graph);
			Map<Integer,String> library = new HashMap<Integer,String>();
		    indexPath.getPath(i ,result);
		}
		endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
	}
}
