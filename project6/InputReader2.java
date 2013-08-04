package project6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;

public class InputReader2 {


	private String fileData;
	
	private ArrayList<String> content;

	private ArrayList<String> titleList;

	private static int nountitlecount;
	
	public InputReader2()
	{
		fileData = "";
		nountitlecount = 0;
		content = new ArrayList<String>();
		titleList = new ArrayList<String>();
	}
	
	public ArrayList<String> readFile(String filename) throws IOException
	{	
		BufferedReader br = new BufferedReader(new FileReader(filename));	
		try{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			int pagecount = 1;
			int titlecount = 0;
			int linecount = 0;
			while (line != null)
			{
				linecount ++;
				String index = "///"+ pagecount;
				if (line.contains(index))
				{
					//indicator of new page
					if (pagecount!=1)
					{
					fileData = sb.toString();
					String data = RemoveLastSpace(fileData);
					content.add(data);
					titleList.add(linecount+"");
					sb.setLength(0);
					pagecount++;
					titlecount++;
					}
					else
					{
						pagecount++;
					}
					
				}
				sb.append(line);
				sb.append("\n");
				line = br.readLine();	
			}
	
			if (sb.length() > 0)
			{
			fileData = sb.toString();
			content.add(fileData);
			titleList.add(linecount+"");
			}
			return content;
		}
		finally{	
			br.close();
		}
	}	
	
	public String RemoveLastSpace(String incomingString)
	{
		String output = incomingString.substring(0, incomingString.length()-1);
		return output;
	}
	
	public void print()
	{
		for (int i = 0; i < content.size(); i++)
		{
		System.out.println(content.get(i));
		}
	}
	
	public void printContentSize()
	{
		System.out.println("There are " + content.size() + " mails. ");
	}
	
	public void printContent(int num)
	{
		String ADcontent = adjustContent(num);
		System.out.println("The content of the " + num + "th email is :" + ADcontent  );
	}

	public ArrayList<String> getTitles()
	{
	return this.titleList;	
	}

	public String adjustContent(int num)
	{
		String ADcontent = "";
		int count = 0;
		while (count < content.get(num).length())
		{
			char ch = content.get(num).charAt(count);
			if (ch == '\n')
			{
				ch = '*';
			}
			ADcontent+=ch;
			count++;
		}
		return ADcontent;
	}
	
	public ArrayList<String> getContentList()
	{
	return this.content;	
	}

	
}
