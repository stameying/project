import redis.clients.jedis.Jedis;
import java.util.*;

public class getProfessorInterest {

public getProfessorInterest(Jedis jedis, String name){
System.out.println(jedis.hget(name,"interest"));
}

public static void main(String[] args)
{
Jedis jedis = new Jedis("localhost",6379);

for (int i = 0; i< args.length; i++)
{
String str = args[i];
getProfessorInterest gpi = new getProfessorInterest(jedis,str);
}
}


}
