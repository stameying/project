import redis.clients.jedis.Jedis;
import java.util.*;

public class getInterestIndex {

	
	
	public static void main(String[] args)
	{
		Jedis jedis = new Jedis("localhost",6379);
		for (int i = 0; i< args.length; i++)
		{
		String professorname = args[i];
		String interest = jedis.hget(professorname,"interest");
		getProfessorInterest getPI = new getProfessorInterest(jedis,professorname);
		//System.out.println(interest);
		getIndexWord giw = new getIndexWord();
		giw.indexString(interest);
		giw.printLibrary();
		}
		
	}
	
}
