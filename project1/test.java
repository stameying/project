import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.htmlparser.*;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

public class test {

	public test()
	{
		
	}
	
	public static void main(String[] args) throws IOException, ParserException
	{
 test wg = new test();
 
	URL url = new URL("http://www.cse.ohio-state.edu/people/faculty.shtml");
	/*
	URLConnection uc = url.openConnection();
    InputStreamReader is = new InputStreamReader(uc.getInputStream());
    int line;
    StringBuffer sb = new StringBuffer("");
    while((line=is.read())!=-1){
        sb.append((char)line);
    }
    String str = sb.toString();
    */
	
	 Parser parser = new Parser ("http://www.cse.ohio-state.edu/people/faculty.shtml");
	 TextExtractingVisitor visitor = new TextExtractingVisitor ();
	 
     NodeList list = parser.parse(new myownFilter());
     
    
     /*
     NodeFilter filter = new TagNameFilter("td");
     NodeList nodes = parser.extractAllNodesThatMatch(filter);
     Node textnode=(Node)nodes.elementAt(1);     
     */
     //System.out.println(textnode.getText());
     //parser.parse(new NodeClassFilter());
     //System.out.println (list.toHtml ());
	
  
     	
	}

	
}


