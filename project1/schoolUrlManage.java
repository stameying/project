import java.net.*;
import redis.clients.jedis.Jedis;
import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.Scanner;
import org.htmlparser.*;
import org.htmlparser.util.*;
import org.htmlparser.visitors.TextExtractingVisitor;

public class schoolUrlManage{

public schoolUrlManage()
{
}

public void addAll(Jedis jedis) throws IOException, ParserException
{
String school1 = "Carneigie_Mellon_University";
//URL url_cmu =new URL("http://people.cs.cmu.edu/faculty/");
jedis.sadd("School", school1);
NodeFilter filt_cmu = new myownFilter_1(school1);

URL url_cmu1 = new URL("http://people.cs.cmu.edu/faculty/a");
URL url_cmu2 = new URL("http://people.cs.cmu.edu/faculty/d");
String web_cmu1 = getweb(url_cmu1);
webparse(web_cmu1,filt_cmu);
String web_cmu2 = getweb(url_cmu2);
webparse(web_cmu2,filt_cmu);


String school2 = "Ohio_State_University";
URL  url_osu = new URL("http://www.cse.ohio-state.edu/people/faculty.shtml");
jedis.sadd("School", school2);
NodeFilter filt = new myownFilter(school2);
String web = getweb(url_osu);
webparse(web,filt);
//System.out.println(url_osu);
}

public void webparse(String url, NodeFilter filt) throws ParserException
{
Parser parser = new Parser(url);
parser.parse(filt);
}

public String getweb(URL url) throws IOException
{
URLConnection uc = url.openConnection();
InputStreamReader is  = new InputStreamReader(uc.getInputStream());
int line;
StringBuffer  sb = new StringBuffer("");
while ((line=is.read())!=-1)
{
sb.append((char)line);
}
String str = sb.toString();
return str;
}

public static void main(String[] args) throws IOException, ParserException
{
Scanner scan = new Scanner(System.in);
System.out.println("1: List current school");
System.out.println("2: Add a school");
System.out.println("3: Delete a school");
System.out.println("4: Add all schools(top 40)");
System.out.println("5: Exit");

schoolUrlManage manage = new schoolUrlManage();
Jedis jedis = new Jedis("localhost",6379);

int number = scan.nextInt();

while (number != 5)
{
if (number == 1)
{
System.out.println(jedis.smembers("School"));
}
else if (number == 4)
{
manage.addAll(jedis);
}

System.out.println("Option:");
number = scan.nextInt();
}

}

} 
