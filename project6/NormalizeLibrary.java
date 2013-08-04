package project6;
import java.util.ArrayList;


public class NormalizeLibrary {

	private ArrayList<Integer> pageList;
	private ArrayList<Integer> paragraphList;
	private ArrayList<String> prefixList;
	private ArrayList<String> suffixList;
	
	
	public NormalizeLibrary(){
		pageList = new ArrayList<Integer>();
		paragraphList = new ArrayList<Integer>();
		prefixList = new ArrayList<String>();
		suffixList = new ArrayList<String>();
	}
	
	public void AddToLibrary(int pageid, int paragraphid, String prefix, String suffix)
	{
		pageList.add(pageid);
		paragraphList.add(paragraphid);
		prefixList.add(prefix);
		suffixList.add(suffix);
	}
	
	
}
