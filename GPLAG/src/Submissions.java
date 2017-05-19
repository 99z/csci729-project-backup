import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.DirectedGraph;

import edu.rit.goal.epdg.builder.EPDGBuilder;
import edu.rit.goal.epdg.builder.ParsingResult;
import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;

public class Submissions {
	
	// Names of the submissions. They are located in folder 'submissions'.

    public static final String REFERENCE = "Reference";
    public static final String OTHER_1 = "Other1";
    public static final String OTHER_2 = "Other2";
    public static final String OTHER_3 = "Other3";
    public static final String OTHER_4 = "Other4";
    public static final String OTHER_5 = "Other5";
    public static final String SUBMISSION = OTHER_1;

    public static final String[] submissions = { REFERENCE, OTHER_1, OTHER_2, OTHER_4, OTHER_5 };
    
	private static Map<String, DirectedGraph<Vertex, Edge>> memoizedPdgs = new HashMap<>();
	
	public static DirectedGraph<Vertex, Edge> getMemoizedPdg(final String fileName) 
	{
		DirectedGraph<Vertex, Edge> result = memoizedPdgs.get(fileName);
//		if (result != null)
//		    return result;
		final String s = readSubmission(fileName); 
		final EPDGBuilder builder = new EPDGBuilder();
		final ParsingResult pr = builder.fromCode(s);
		final Map<String, DirectedGraph<Vertex, Edge>> epdgs = pr.getEpdgs();
		result = epdgs.get("main");
		if (result == null) {
		    result = epdgs.values().iterator().next();
		}
//		if (fileName == Submissions.OTHER_3) {
//		    final Iterator<DirectedGraph<Vertex, Edge>> it = epdgs.values().iterator();
//		    final DirectedGraph<Vertex, Edge> g2 = it.next();
//		    final DirectedGraph<Vertex, Edge> g3 = it.next();
//		    final Vertex v14 = g2.vertexSet().stream().filter(v -> v.getId() == 14).findFirst().get();
//		    final Vertex v0 = g3.vertexSet().stream().filter(v -> v.getId() == 0).findFirst().get();
//		    final Vertex v1 = g3.vertexSet().stream().filter(v -> v.getId() == 1).findFirst().get();
//		    v0.setId(19);
//		    v1.setId(20);
//		    Graphs.addGraph(g2, g3);
//		    g2.addEdge(v14, v0, new Edge(v14, v0, EdgeType.DATA));
//		    return g2;
//		}
		memoizedPdgs.put(fileName, result);
		return result;
	    }
	
	    public static String readSubmission(final String fileName) 
	    {
			String result = null;
			try 
			{
			    if (fileName.contains("submissions")) 
			    {
			    	result = new String(Files.readAllBytes(Paths.get(fileName)));
			    } 
			    else 
			    {
			    	result = new String(Files.readAllBytes(Paths.get("submissions/" + fileName)));
			    }
			} 
			catch (final IOException e) 
			{
			    e.printStackTrace();
			}
			return result;
	    }

}
