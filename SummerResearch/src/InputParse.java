import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class InputParse {

	private String fileData;
	
	private Jedis jedis;
	
	public InputParse(Jedis injedis)
	{
		fileData = "";
		jedis = injedis;
	}
	
	public void readFile(String filename) throws IOException
	{	
		BufferedReader br = new BufferedReader(new FileReader(filename));	
		try{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			fileData = sb.toString();
		}
		finally{	
			br.close();
		}
	}
	
	public ArrayList<String> parseString(String input)
	{
		String inputCp = input;
		ArrayList<String> result = new ArrayList<String>();
		
		int count = 0;
		String word = "";
		while (count < inputCp.length())
		{
			char ch = inputCp.charAt(count);
			if (count != 0 && ch != ' ')
			{
				word += ch;
			}
		}
		
		return result;
	}
	public void printFile()
	{
		System.out.println(fileData);
	}
	
	public void parseData()
	{
		int readCount = 0;
		String ProfessorName = "";
		String Interest = "";
		int ProfessorFlag = 0;
		while (readCount < fileData.length())
		{
			char ch = fileData.charAt(readCount);
			if (ch != ':' && ProfessorFlag == 0 && ch != '\n')
			{
				if (ch == ' ')
				{
					ch = '_';
				}
				ProfessorName += ch;
				readCount ++;
			}
			else if ( ch != ':' && ProfessorFlag == 1 && ch != '\n')
			{
				Interest += ch;
				readCount++;
			}
			else if ( ch == ':')
			{
				ProfessorFlag = 1;
				readCount++;
			}
			else if (ch == '\n')
			{
				//System.out.println(ProfessorName);
				//System.out.println(Interest);
				jedis.sadd("OSU",ProfessorName);
				jedis.hset(ProfessorName,"interest",Interest);
				//parseInterest(Interest);
				ProfessorName = "";
				Interest = "";
				ProfessorFlag = 0;
				readCount++;
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		Jedis jedis = new Jedis("localhost",6379);
		InputParse ip = new InputParse(jedis);
		ip.readFile("test.txt");
		//ip.printFile();
		ip.parseData();
		
		Set<String> s  =  new HashSet<String>();
		s = jedis.smembers("OSU");
		System.out.println(s);

		
		
	}
	
}
