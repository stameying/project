import java.util.*;
import java.io.*;

public class generateGraph {
static String graph;
static String start;
static ArrayList<Integer> indexList;
static int size1,size2,size3; 
static int size2count; 
//Jedis jedis ;
static Map<Integer,Integer> degreeList;

public generateGraph(){
graph = "";
start = "";
indexList = new ArrayList<Integer>();
size1 = 0;
size2 = 0;
size3 = 0;
size2count = 0;
//jedis = new Jedis("localhost",6379);
degreeList = new HashMap<Integer,Integer>();
}

public void add(int index, ArrayList<Integer> result)
{
graph += "\n-------------\n";
graph += "Node: " + index + "\n";
graph += "Out0: startNode , weight = 0 \n";	
int count = 0;
while (count < result.size())
{
if (degreeList.containsKey(result.get(count)))
{
int degree = degreeList.get(result.get(count));
degree += 1;
degreeList.remove(result.get(count));
degreeList.put(result.get(count),degree);
}
else
{
degreeList.put(result.get(count),1);
}

int number = count+1;
if (count == 0)
{
/*
graph += "Out" + number + ": " + result.get(count) + ", weight = Sizeof(" + index + ") + Sizeof" + result.get(count) + ")\n";
*/
graph += "From startNode to Node" + result.get(count) + ", weight = Sizeof(key(" + index +")) + Sizeof" + result.get(count) + ")\n";  
}
else
{
/*
graph += "Out" + number + ": " + result.get(count) + ", weight = Sizeof(" + result.get(count) + ")\n";
*/

graph += "From Node" + result.get(count-1) + " to Node" + result.get(count) + ", weight = Sizeof(" + result.get(count) + ")\n";

}

if (number == result.size())
{
//number ++;
/*
graph += "Out" + number + ": endPointOf " + index + " , weight = 0\n";  
*/

graph += "From Node" + result.get(number-1) + " to endPointOf " + index + " , weight = 0\n";

}
count ++;

}

/*add the size*/

}


public void addIndex(int index)
{
indexList.add(index);
}

public void printGraph()
{
int count = 0;
start += "\n-----------------\n";
start += "node: startNode\n";

while (count < indexList.size())
{
start += "Out" + count + ": " + indexList.get(count) + " , weight = 0\n";
count++;
}

//System.out.println(start);
//System.out.println(graph);

String degreeGraph = degreeList.toString();

try {
FileWriter fstream = new FileWriter("graph.txt");
BufferedWriter outfile = new BufferedWriter(fstream);
//outfile.write(start);
//outfile.write(graph);
outfile.write(degreeGraph);
outfile.write("Size1 = " + size1 + "\n");
outfile.write("Size2 = " + size2 + "\n");
outfile.write(start);
outfile.write(graph);

}
catch (IOException e)
{
System.err.println("ERROR: " + e.getMessage());
}

}

public void addSize(String interest)
{
int length = interest.length();
size1 += length;
}

public void addSize(int index, ArrayList<Integer> result, Map<Integer,String> library)
{
if (size2count == 0)
{
size2 += 5;
size2count = 1;
}
int count = 0;
while (count < result.size())
{
int indexnumber = result.get(count);
String word = library.get(indexnumber);
int length = word.length();
size2 += length;
count ++;
}

if (count == result.size())
{
System.out.println("Add successfully");
size2count = 0;
}

}



}

