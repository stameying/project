import redis.clients.jedis.Jedis;
import java.util.*;

public class project1{

public project1(Jedis jedis, String schoolname)
{
Set<String> s  =  new HashSet<String>();
s = jedis.smembers(schoolname);
System.out.println(s);

}


public static void main(String[] args)
{
String schoolname = args[0];

Jedis jedis = new Jedis("localhost",6379);
project1 pj1 = new project1(jedis,schoolname);



}

}
