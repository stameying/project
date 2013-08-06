import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class task7 {
	private static Map<Integer, Integer> library;
	private Jedis jedis;
	
	public task7(){
		library = new HashMap<Integer,Integer>();
		Jedis jedis = new Jedis("localhost",6379);
	}
	
	public static void main(String[] agrs) throws IOException
	{
		Jedis jedis = new Jedis("localhost",6379);
		task7 test = new task7();
		InputReader2 ir = new InputReader2();
		ir.readFile("E-mail6.txt");
		ArrayList<String> content = ir.getContentList();
		ArrayList<String> title = ir.getTitles();
		IndexPath indexPath = new IndexPath();
		IndexParagraph4 getParagraph = new IndexParagraph4();
		
		normalizeAgent normalAgent = new normalizeAgent();
		
		NodeGraph graph = new NodeGraph();
	
		long startTime=System.currentTimeMillis();  
		
		//System.out.println(content.get(0));
		for (int i = 0; i < content.size() ; i++)
		{
			String mailIndex = title.get(i);
			String mailContent = content.get(i);
			ArrayList<Integer> result = getParagraph.indexString(i,mailContent,graph);
			Map<Integer,String> library = new HashMap<Integer,String>();
		    indexPath.getPath(i ,result);
		}
		long endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
		
		//indexPath.printPath();
		//graph.printGraph();
		//graph.show_key_nodes();
		startTime=System.currentTimeMillis();  
		graph.weightGraph1();
		endTime=System.currentTimeMillis(); 
		//System.out.println("Weight1:" + (endTime-startTime) + " ms");
		
		startTime=System.currentTimeMillis();  
		graph.weightGraph2();
		endTime=System.currentTimeMillis(); 
		//System.out.println("Weight2:" + (endTime-startTime) + " ms");
		
		startTime=System.currentTimeMillis();  
		graph.weightGraph3();
		endTime=System.currentTimeMillis(); 
		//System.out.println("Weight3:" + (endTime-startTime) + " ms");
		
		
		//getParagraph.printNodeList();
		//getParagraph.printLibrary();
	
		//graph.test2();
		
		library = getParagraph.getLibrary();
		
		test.run(content,getParagraph);
		test.run(content,getParagraph);
		test.run(content,getParagraph);
		test.run(content,getParagraph);
		test.run(content,getParagraph);
		test.run(content,getParagraph);
	}
	
	public void run(ArrayList<String> incontent, IndexParagraph4 inpara) throws IOException
	{	
		long startTime=System.currentTimeMillis();  
		for (int i = 0 ; i < incontent.size() ; i++)
		{
		ArrayList<Integer> result = inpara.indexString2(i, incontent.get(i));
	
		int count = 0;
		while (count < result.size())
		{
			int hash = result.get(count);
			if (!checkHash(hash))
			{
				String paragraph = jedis.get(result.get(count)+"");
				inpara.addToLibrary(paragraph,0);
			}
			count++;
		}
		}
		
		long endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
	}

	public boolean checkHash(int hash)
	{
	 return library.containsKey(hash);
	}

}


