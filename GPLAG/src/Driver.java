import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.io.File;

import org.jgrapht.DirectedGraph;
import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;

public class Driver
{
	public static void main(String[] args)
	{
		File dir = new File("/Users/egg/Desktop/ind/GPLAG/submissions/");
		File[] dirListing = dir.listFiles();

		Integer posCount = 0;
		Integer negCount = 0;


		for (Integer i = 0; i < 1000; i++) {
			DirectedGraph<Vertex, Edge> g1 = Submissions.getMemoizedPdg("Reference.java");
			DirectedGraph<Vertex, Edge> g2 = Submissions.getMemoizedPdg("Other4.java");
			if (GPLAG(g1, g2, 0.8, 0.9)) {
				posCount++;
			} else {
				negCount++;
			}
		}

		System.out.println("");
		System.out.println(posCount);
		System.out.println(negCount);

//		for (File child : dirListing) {
//			System.out.println("");
//			System.out.println(child.getName());

//			DirectedGraph<Vertex, Edge> g2 = Submissions.getMemoizedPdg(child.getName());
//			GPLAG(g1, g2, false, 0.8, 0.9);
//		}
//		DirectedGraph<Vertex, Edge> g1 = Submissions.getMemoizedPdg(Submissions.REFERENCE);
//		DirectedGraph<Vertex, Edge> g2 = Submissions.getMemoizedPdg(Submissions.SUBMISSION);
//
//		System.out.println();
//		System.out.println("Comparing " + Submissions.SUBMISSION + " (" + g2.vertexSet().size() + " nodes) with " + Submissions.REFERENCE + " ("
//				+ g1.vertexSet().size() + " nodes)\n");

	}

	public static boolean GPLAG (DirectedGraph<Vertex, Edge> data, DirectedGraph<Vertex, Edge> query, double gamma, double threshold)
	{
		Iterator<Vertex> queryVertices = query.vertexSet().iterator();
		ArrayList<Vertex> queryVertexList = new ArrayList<Vertex>();

  		while (queryVertices.hasNext())
  		{
  			queryVertexList.add(queryVertices.next());
  		}

  		Random rn = new Random();

		Vertex vertexToRemove = queryVertexList.get(0 + rn.nextInt(queryVertexList.size()));

		DirectedGraph<Vertex, Edge> queryPrime = query;

		queryPrime.removeVertex(vertexToRemove);

		if(queryPrime.vertexSet().size() >= (gamma * data.vertexSet().size()))
		{
			if(SubgraphMatching.smDriver(data, queryPrime, threshold))
			{
				return true;
			}
			else
			{
				GPLAG(data, queryPrime, gamma, threshold);
			}
		}
		return false;
	}
}
