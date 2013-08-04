package project6;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;


public class InputReader {

private String fileData;
	
private ArrayList<String> content;

private ArrayList<String> titleList;

private static int nountitlecount;

public InputReader()
{
		fileData = "";
		nountitlecount = 0;
		content = new ArrayList<String>();
		titleList = new ArrayList<String>();
}

public ArrayList<String> read(String filename) throws IOException
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
			if (line.contains("<page>"))
			{
				if (linecount != 1)
				{
				//indicator of new page
				fileData = sb.toString();
				content.add(fileData);
				String title = getTitle(fileData);
				titleList.add(title);
				sb.setLength(0);
				pagecount++;
				titlecount++;
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
		String title = getTitle(fileData);
		titleList.add(title);
		}
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
	System.out.println(content.get(i));
	}
}

public void printpages()
{
System.out.println("There are " + content.size() + " pages. ");	
}

public void printTitlessize()
{
System.out.println("There are " + titleList.size() + " titles. ");	
}

public void printTitles()
{
for (int i = 0; i < titleList.size() ; i++)
{
System.out.println(titleList.get(i));	
}
}

public String getTitle(String Page)
{
String title = "";
boolean startFound = false;
boolean endFound = false;
int start = 0;
int end = 0;

if (Page.indexOf("<title>") > -1)
{
start = Page.indexOf("<title>") + 7;
startFound = true;
}
if (Page.indexOf("</title>") > -1)
{
end = Page.indexOf("</title>");
endFound = true;
}
if (startFound == true && endFound == true)
{
title = Page.substring(start,end);
}
else
{
title = "NonTitlePage" + nountitlecount;
nountitlecount++;
}
return title;
}

public ArrayList<String> getContent()
{
return this.content;	
}

public ArrayList<String> getTitles()
{
return this.titleList;	
}

public void printpage(int number)
{
System.out.println(content.get(number));	
}

}
