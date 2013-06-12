
public class test3 {

public test3(){
	System.out.println("nihao");
}

public static void main(String[] args)
{
test3 t3 = new test3();
String sentence = "<page>\n<title>An impending scalability problem</title>\n<ns>0</ns>\n";
int index = 0;
index = sentence.indexOf("<title>");
System.out.println(index);
index = sentence.indexOf("</title>");
System.out.println(index);
System.out.println(sentence.substring(14, 45));

}
}
