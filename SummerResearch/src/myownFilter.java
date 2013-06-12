import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import redis.clients.jedis.Jedis;
import java.net.*;

public class myownFilter implements NodeFilter {
public String school;
public myownFilter(String schoolname)
{
school = schoolname;
}
	@Override
	public boolean accept(Node node) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost",6379);
		if (node instanceof TableRow) {
			int check1 = 0;
			int check2 = 0;
			String professorname = null, interest = null;
			TableRow tr = (TableRow) node;

			NodeList children = tr.getChildren();

			Node child = children.elementAt(1);
			
			if (child.toHtml().charAt(1) != '!') {
				NodeList pronametag = child.getChildren();
				
				if (pronametag.elementAt(0).toHtml().charAt(1) == 'a'
						&& pronametag.elementAt(0).toHtml().charAt(3) == 'h') {
					Node nametag = pronametag.elementAt(0);
					professorname = nametag.getFirstChild().toHtml();
					check1 = 1;
				}

				if (check1 == 1) {
					Node child2 = children.elementAt(3);
					// System.out.println(child2.toHtml());

					// System.out.println("number " + child2.toHtml());
					NodeList intchildren = child2.getChildren();

					if (child2.toHtml().contains("Interests:")) {
						check2 = 1;
					}

					if (check2 == 1) {
						int number = intchildren.size();

						if (intchildren.elementAt(number - 1).toHtml()
								.contains("em")) {

							Node intchild1 = intchildren.elementAt(number - 1);
							if (intchild1.toHtml().charAt(1) == 'd') {
								if (!intchild1.getChildren().elementAt(5)
										.toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(5);
									interest = intchild2.toHtml();
								} else if (!intchild1.getChildren()
										.elementAt(6).toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(6);
									interest = intchild2.toHtml();
								} else if (!intchild1.getChildren()
										.elementAt(4).toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(4);
									interest = intchild2.toHtml();
								}
							}
						} else {
							Node intchild1 = intchildren.elementAt(number - 2);
							if (intchild1.toHtml().charAt(1) == 'd') {
								if (!intchild1.getChildren().elementAt(5)
										.toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(5);
									interest = intchild2.toHtml();
								} else if (!intchild1.getChildren()
										.elementAt(6).toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(6);
									interest = intchild2.toHtml();
								} else if (!intchild1.getChildren()
										.elementAt(4).toHtml().contains("/")) {
									Node intchild2 = intchild1.getChildren()
											.elementAt(4);
									interest = intchild2.toHtml();
								}
							}
						}

					}

				}

			}
	/*
			if (check1 ==1 && check2 ==1)
			{
				if (professorname.length() == 0 )
				{
					System.out.println("Warning1:!!!!!!!!!!!!");
				}		
				else if (interest.length() == 0 )
				{
					System.out.println("Warning2:!!!!!!!!!!!!");
				}		
				else
				{
				System.out.println("Professor: " + professorname );
				System.out.println("Interest: "+ interest);
				}
			}
	*/

		if (check1 == 1 && check2 == 1)
	{
		namefix nf = new namefix();
		String fixname = nf.namechange(professorname);
                String fixint = nf.intchange(interest);
	//	System.out.println("111");
	//	System.out.println(school);
	//	System.out.println(fixname);
		jedis.sadd(school,fixname);
		jedis.hset(fixname,"interest",fixint);
	}			
			return true;
		}
		return false;
	}

}
