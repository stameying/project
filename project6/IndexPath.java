package project6;
import java.util.*;
import redis.clients.jedis.Jedis;


public class IndexPath {

private static Map<Integer, String> path;

public IndexPath()
{
path = new HashMap<Integer,String>();
}

public void getPath(int mailId, ArrayList<Integer> mailContent)
{
String contentpath = "";
contentpath += " ";
for (int i = 0; i < mailContent.size(); i++)
{
	contentpath += mailContent.get(i);
	if (i + 1 != mailContent.size())
	{
	contentpath += ",";
	}
}
path.put(mailId+1, contentpath);
}

public int getIndex(String professorname)
{
return (professorname.hashCode())%1000+1;
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

}

