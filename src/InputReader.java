import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;

public class InputReader {
	
	private ArrayList<String> content;
	
	private Jedis jedis;
	
	public InputReader(Jedis injedis)
	{
		content = new ArrayList<String>();
		this.jedis = injedis;	
	}
	
	public ArrayList<String> readFile(String filename) throws IOException
	{	
		BufferedReader br = new BufferedReader(new FileReader(filename));	
		try{
			StringBuilder mail = new StringBuilder();
			String line = br.readLine();
			int mailIndex = 2;
			while (line != null)
			{
				String index = "///"+ mailIndex;

				if (!line.contains(index))
				// same Mail
				{
					mail.append(line);
					mail.append("\n");
					line = br.readLine();	
				}
				else
				// new Mail indicator
				{
					String mailData = mail.toString();
					content.add(mailData);
					//clear paragraph 
					mail.setLength(0);
					mailIndex++;
					line = br.readLine();
				}
			}
			//
			// Handle last mail
			//
			
			String mailData = mail.toString();
			content.add(mailData);
			//clear paragraph 
			mail.setLength(0);
			mailIndex++;	
			return content;
		}
		finally{	
			br.close();
		}
	}	
	
	
	public void print()
	{
		for (int i = 0; i < content.size(); i++)
		{
		System.out.println(adjustContent(content.get(i)));
		}
	}
	
	
	public String adjustContent(String paragraph)
	{
		String ADcontent = "";
		int count = 0;
		while (count < paragraph.length())
		{
			char ch = paragraph.charAt(count);
			if (ch == '\n')
			{
				ch = '*';
			}
			ADcontent+=ch;
			count++;
		}
		return ADcontent;
	}
	
	public void printContentSize()
	{
		System.out.println("There are " + content.size() + " mails. ");
	}
}