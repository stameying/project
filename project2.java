import java.util.*;
import redis.clients.jedis.Jedis;

public class project2 {

public static void main(String[] args)
{
getIndexWord wordLibrary = new getIndexWord();
getIndexPath indexPath = new getIndexPath();
generateGraph genGraph = new generateGraph();
Jedis jedis = new Jedis("localhost",6379);
//genGraphSize genSize = new genGraphSize();


Set<String> professorList = new HashSet<String>();
professorList = jedis.smembers("OSU");
Iterator it2 = professorList.iterator();
while (it2.hasNext())
{
String professorName = (String)it2.next();
String interest = jedis.hget(professorName,"interest");
ArrayList<Integer> result = wordLibrary.indexString(interest);
indexPath.IndexPath(professorName,result);
int index = indexPath.getIndex(professorName);
genGraph.add(index,result);
genGraph.addIndex(index);
Map<Integer,String> library = new HashMap<Integer,String>();
 library = wordLibrary.getLibrary();
genGraph.addSize(interest);
genGraph.addSize(index,result, library);
}
wordLibrary.printLibrary();
indexPath.printPath();
genGraph.printGraph();
}


}
