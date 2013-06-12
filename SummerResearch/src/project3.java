import java.io.IOException;
import java.util.*;

import redis.clients.jedis.Jedis;


public class project3 {

public static void main(String[] agrs) throws IOException
	{

		Jedis jedis = new Jedis("localhost",6379);
		InputReader ir = new InputReader(jedis);
		getIndexParagraph getParagraph = new getIndexParagraph();
		getIndexPath2 indexPath = new getIndexPath2();
		generateGraph2 genGraph = new generateGraph2();
		ir.read("wiki.txt");
		//ir.print();
		ir.printpages();
		ir.printTitlessize();
		ArrayList<String> content = ir.getContent();
		ArrayList<String> title = ir.getTitles();
		//ir.printpage(1);
		normalizeAgent nlagent = new normalizeAgent();
		
		long startTime=System.currentTimeMillis();  
		for (int i = 0; i < content.size() ; i++)
		{
			String titile = title.get(i);
			String page = content.get(i);
			int pageid = i;
			ArrayList<Integer> result = getParagraph.indexString(i,page,nlagent);
			Map<Integer,String> library = new HashMap<Integer,String>();
			library = getParagraph.getLibrary();
			
			indexPath.IndexPath(titile,result);
			int index = indexPath.getIndex(titile);
			genGraph.add(index,result,library,page);
			genGraph.addIndex(index);
			genGraph.weightGraph(index,page,result,library);
		}
		
		getParagraph.printLibrary();
		indexPath.printPath();
		genGraph.printGraph();
		long endTime=System.currentTimeMillis(); 
		System.out.println((endTime-startTime) + " ms");
	}
	
}



