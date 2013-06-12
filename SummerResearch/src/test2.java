import java.util.ArrayList;


public class test2 {

	public static void main(String[] args)
	{
		String word1 = " Computer";
		String word2 = " Computer ";
		
		if (word1.hashCode() == word2.hashCode())
		{
		System.out.println("its equal");
		}
		else
		{
			System.out.println("Different!");
		}
	}
}

/*
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
*/