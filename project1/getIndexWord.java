import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class getIndexWord {
private static Map<Integer, String> library;
//Jedis jedis;
private ArrayList<Integer> result ;
//list of number representations of incoming string

public getIndexWord()
{
library = new HashMap<Integer,String>();
//jedis = new Jedis("localhost",6379);
}

public Map<Integer,String> getLibrary()
{
return this.library;
}

public ArrayList<Integer> indexString(String inputString)
{
	result = new ArrayList<Integer>();
	String input = inputString;
	ArrayList<String> wordList = this.parseString(input);
	int index = 0;
	while (index < wordList.size())
	{
	String word = wordList.get(index);
	int hashCode = getHashCode(word);
	int checkResult = checkWord(hashCode,word);
	if (checkResult != 1)
	{
		addToLibrary(word , checkResult);
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

public ArrayList<String> parseString(String input)
{
	String temp = input;
	ArrayList<String> result = new ArrayList<String>();
	int i = 0;
	int index = 0;
	String word = "";
	while ( i < temp.length())
	{
		char ch = temp.charAt(i);
		if (i+1 != temp.length())
		{
		if (ch != ',')
		{
			word+=ch;
		}
		else
		{
			result.add(index, word);
			word = "";
			index+=1;
		}
		}
		else
		{
			word+=ch;
			result.add(index, word);
			word = "";
			index+=1;
		}
		i++;
	}
	return result;
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

public int getHashCode(String word)
{
return word.hashCode() % 30;	
}

public void addToLibrary(String word, int checkResult)
{
	// word not included, no hash conflict,  add nomally
if (checkResult == 0)
{
	int hashCode = getHashCode(word);
	library.put(hashCode, word);
}
// word not included, hash conflict, add as 0
else
{
	if (library.containsKey(0))
	{
		String conflictWordList = library.get(0);
		conflictWordList += ",";
		conflictWordList += word;
		library.remove(0);
		library.put(0, conflictWordList);
	}
	else
	{
		System.out.println("word is "+ word);
	library.put(0, word);
	}
}
}

public String getWord(int index)
{
return this.library.get(index);	
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


/*
public void printIndexString()
{
	String output = "";
for (int i= 0; i < result.size() ; i++)
{
//	System.out.print(result.get(i) + ": " + this.getWord(result.get(i)) + "\n");
	output += result.get(i);
	if (i+1 != result.size())
	{
	output += ",";
	}
}
System.out.println("Incoming String becomes " +  output );
}
*/
/*
 * for testing
 * 
*/
/*
public static void main(String[] args)
{
getIndexWord giw = new getIndexWord();
String input = "Parallel and distributed computing, data intensive computing, grid computing, compilers";
giw.indexString(input);
giw.printLibrary();
giw.indexString(input);
giw.printLibrary();
String input2 = "Fault-tolerance, distributed systems, concurrency semantics";
giw.indexString(input2);
giw.printLibrary();
String input3 = "Real-time computing and communication, network security, distributed systems";
giw.indexString(input3);
giw.printLibrary();
String input4 = "Parallel and distributed computing, concurrency semantics, distributed systems";
giw.indexString(input4);
giw.printLibrary();
}
*/

	
}
