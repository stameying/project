import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class task7 {
	
	public task7(){
		
	}
	
	public static void main(String[] agrs) throws IOException
	{
		Jedis jedis = new Jedis("localhost",6379);
		task6 test = new task6();
		InputReader2 ir = new InputReader2();
		ir.readFile("E-mail.txt");
		ArrayList<String> content = ir.getContentList();
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
