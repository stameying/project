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
		System.out.println("1");
		task6 test = new task6();
		InputReader ir = new InputReader();
		ir.read("wiki.txt");
		System.out.println("2");
		ArrayList<String> content = ir.getContent();
		ArrayList<String> title = ir.getTitles();
		IndexPath indexPath = new IndexPath();
		System.out.println("3");
		IndexParagraph getParagraph = new IndexParagraph();
		
		normalizeAgent normalAgent = new normalizeAgent();
		
		NodeGraph graph = new NodeGraph();
		System.out.println("4");
		//System.out.println(content.size());
		long startTime=System.currentTimeMillis();  
		
		for (int i = 0; i < content.size() ; i++)
		{
			System.out.println("6");
			String mailIndex = title.get(i);
			String mailContent = content.get(i);
			System.out.println("7");
			//System.out.println(mailContent);
			ArrayList<Integer> result = getParagraph.indexString(i,mailContent,graph);

			System.out.println("8");
			Map<Integer,String> library = new HashMap<Integer,String>();
		    //library = getParagraph.getLibrary();

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
