package project6;
import java.util.*;


public class normalizeAgent {

private NormalizeLibrary library;
	
public normalizeAgent(){
	library = new NormalizeLibrary();
}

public String normalize(int pageid, int paragraphId, String paragraph){
	String NoParagraph = "";
	int startIndex = LookValidStart(paragraph);

	int	endIndex = LookValidEnd(paragraph);

	String prefix = "";
	String suffix = "";
	int prefixFlag = 0;
	int surfixFlag = 0;
	if (startIndex > 0 )
	{
		for (int i = 0; i < startIndex; i++)
		{
			prefix += paragraph.charAt(i);
		}
		prefixFlag = 1;
	}
	if (endIndex > 0 )
	{
		for (int i = endIndex; i > 0; i--)
		{
			suffix += paragraph.charAt(paragraph.length()-i);
		}
		surfixFlag = 1;
	}
	/*
	System.out.println("Paragraph is:" + paragraph);
	System.out.println("Paraid = " + paragraphId + " Start Pos = " + startIndex + " End Pos = " + endIndex);
	System.out.println("Paraid = " + paragraphId + " prefixFlag = " + prefixFlag + " surfixFlag = " + surfixFlag);
	System.out.println("!!!!");*/
	if (prefixFlag ==1 && surfixFlag == 1)
	{
		AddNormalizeLibrary(pageid, paragraphId, prefix, suffix);
		NoParagraph = paragraph.substring(startIndex, paragraph.length()-endIndex);
	}
	else if (prefixFlag ==1 && surfixFlag == 0)
	{
		AddNormalizeLibrary(pageid, paragraphId, prefix, "");
		NoParagraph = paragraph.substring(startIndex, paragraph.length());
	}
	else if (prefixFlag ==0 && surfixFlag == 1)
	{
		AddNormalizeLibrary(pageid, paragraphId, "", suffix);
		NoParagraph = paragraph.substring(0, paragraph.length()-endIndex);
	}
	else
	{
		NoParagraph = paragraph;
	}
	return NoParagraph;
}

public void AddNormalizeLibrary(int pageid, int paragraphId, String prefix, String suffix)
{
	library.AddToLibrary(pageid, paragraphId, prefix, suffix);
}

public int LookValidStart(String str)
{
int pos = 0;
int length = str.length();
int startLen = 5;
if (length < 5)
{
startLen = length;	
}
for (int i = 0; i < startLen; i++)
{
if (Character.isLetter(str.charAt(i)) == false )
{
pos++;	
}
else
{
return pos;	
}
}
return pos;
}

public int LookValidEnd(String str)
{
if (str.length() <= 5)
{
return 0;	
}
int pos = 0;
int diff = str.length() -5;
if (diff > 5)
{
diff = 5;	
}
for (int i = 0; i < diff; i++)
{
if (Character.isLetter(str.charAt(str.length()-1-i)) == false )
{
pos++;	
}
else
{
return pos;	
}
}
return pos;
}

}
