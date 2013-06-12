import java.util.*;
import redis.clients.jedis.Jedis;

public class project2 {

public static void main(String[] args)
{
getIndexWord wordLibrary = new getIndexWord();
getIndexPath indexPath = new getIndexPath();
generateGraph2 genGraph = new generateGraph2();
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
Map<Integer,String> library = new HashMap<Integer,String>();
library = wordLibrary.getLibrary();

indexPath.IndexPath(professorName,result);
int index = indexPath.getIndex(professorName);
genGraph.add(index,result,library,interest);
genGraph.addIndex(index);
//Map<Integer,String> library = new HashMap<Integer,String>();
// library = wordLibrary.getLibrary();
genGraph.weightGraph(index,interest,result,library);


}
wordLibrary.printLibrary();
indexPath.printPath();
genGraph.printGraph();

}


}
