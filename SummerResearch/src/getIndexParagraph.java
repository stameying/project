import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class getIndexParagraph {
private static Map<Integer, String> library;
private ArrayList<Integer> result;
private int paragraphCount;

public getIndexParagraph()
{
library = new HashMap<Integer,String>();
paragraphCount = 0;
}

public Map<Integer,String> getLibrary()
{
return this.library;
}

public ArrayList<Integer> indexString(int pageid, String inputString, normalizeAgent nlagent )
{
	result = new ArrayList<Integer>();
	String page = inputString;
	ArrayList<String> paragraphList = this.parsePage(page);
	int index = 0;
	
	while (index < paragraphList.size())
	{
		
	String paragraph = paragraphList.get(index);
	String norParagraph = nlagent.normalize(pageid,paragraph);
	
	int hashCode = getHashCode(norParagraph);
	int checkResult = checkWord(hashCode,norParagraph);
	
	if (checkResult != 1)
	{
		addToLibrary(norParagraph , checkResult);
	}
	
	if (checkResult != 2)
	{
	result.add(index, hashCode);
	}
	else
	{
		result.add(index,0);
	}
	index++;
	}
	return result;
	
}

public ArrayList<String> parsePage( String page)
{
	String pageCp = page;
	ArrayList<String> result = new ArrayList<String>();
	int count = 0;
	String paragraph = "";
	while (count + 1 < pageCp.length())
	{
		char ch1 = pageCp.charAt(count);
		char ch2 = pageCp.charAt(count+1);
		if (ch1 == '\n' && ch2 == '\n')
		{
			paragraph+=ch1;
			result.add(paragraph);
			paragraphCount ++;
			paragraph = "";
		}
		else
		{
			paragraph+=ch1;
		}
		count++;
	}
	
	result.add(paragraph);
	paragraphCount ++;
	paragraph = "";

	return result;
}

public int getHashCode(String word)
{
return word.hashCode() % 150;	
}

public int checkWord(int HashCode, String word)
{
	int result = -1;
	// word is not included , ok to be added into library
if (!library.containsKey(HashCode))
{
	result = 0;
	return result;	
}
else
{
 String storeWord = (String) library.get(HashCode);
 // word is included
 if (storeWord.equals(word))
 {
	 result = 1;
	 return result;
 }
 // word is not included but has a same hashcode to some included words
 else
 {
	 result = 2;
	 return result;
 }
}
	 
 
}

public void addToLibrary(String paragraph, int checkResult)
{
	// word not included, no hash conflict,  add nomally
if (checkResult == 0)
{
	int hashCode = getHashCode(paragraph);
	library.put(hashCode, paragraph);
}
// word not included, hash conflict, add as 0
else
{
	if (library.containsKey(0))
	{
		String conflictParagraphList = library.get(0);
		conflictParagraphList += ",";
		conflictParagraphList += paragraph;
		library.remove(0);
		library.put(0, conflictParagraphList);
	}
	else
	{
	library.put(0, paragraph);
	}
}
}



public void printLibrary()
{
System.out.println("--------CURRENT LIBRARY---------");
Iterator iter = this.library.keySet().iterator();
while (iter.hasNext())
{
int key = (Integer) iter.next();
String value = this.library.get(key);
System.out.println(key + " : " + value);
}
}




}