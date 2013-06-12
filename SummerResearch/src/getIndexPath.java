import java.util.*;
import redis.clients.jedis.Jedis;


public class getIndexPath {

private static Map<Integer, String> path;

public getIndexPath()
{
path = new HashMap<Integer,String>();
}

public void IndexPath(String name, ArrayList<Integer> interest)
{
String professorInfo = name;
professorInfo += " ";
for (int i = 0; i < interest.size(); i++)
{
professorInfo += interest.get(i);
professorInfo += ",";
}
path.put(name.hashCode()%10000, professorInfo);
}

public int getIndex(String professorname)
{
return (professorname.hashCode())%10000;
}

public Map<Integer,String> getPath()
{
return path;
}

public void printPath()
{
Iterator iter = this.path.keySet().iterator();
while (iter.hasNext())
{
int key = (Integer) iter.next();
String value = this.path.get(key);
System.out.println(key + " : "+ value);
}
}


public static void main(String[] args)
{
Jedis jedis = new Jedis("localhost",6379);
getIndexWord giw = new getIndexWord();
getIndexPath path = new getIndexPath();
for (int i =0; i<args.length ; i++)
{
String professorname = args[i];
String interest = jedis.hget(professorname, "interest");
ArrayList<Integer> result = giw.indexString(interest);
path.IndexPath(professorname,result);
}
giw.printLibrary();
path.printPath();


} 

}

