/**
 * This class contains the naive subgraph matching algorithm, a method to compute the search space 
 * both with and without taking profiling into account, and also a method to compute the search
 * order. Also some helper functions.
 */
import org.jgrapht.DirectedGraph;
import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SubgraphMatching 
{
  public static boolean smDriver(DirectedGraph<Vertex, Edge> data, DirectedGraph<Vertex, Edge> query, double threshold)
  {
  		HashMap<Vertex, ArrayList<Vertex>> searchSpace = computeSearchSpace(data, query);
  		
  		// a list to store the nodes in which they should be processed
  		ArrayList<Vertex> searchOrder = new ArrayList<Vertex>();
  		Iterator<Vertex> queryVertices = query.vertexSet().iterator();
  		while(queryVertices.hasNext())
  		{
  			searchOrder.add(queryVertices.next());
  		}
  
  		Integer i = 0;
  	
  		HashMap<Vertex, Vertex> s = new HashMap<Vertex, Vertex>();
  		HashMap<Vertex, Vertex> t = RunSM(query, data, searchSpace, searchOrder, i, s);

  		if ((t.size() / query.vertexSet().size()) >= threshold)
  		{
  			return true;
  		}
  		
  		return false;
  }

  public static HashMap<Vertex, ArrayList<Vertex>> computeSearchSpace(DirectedGraph<Vertex, Edge> data, DirectedGraph<Vertex, Edge> query)
  {
	  HashMap<Vertex, ArrayList<Vertex>> searchSpace = new HashMap<Vertex, ArrayList<Vertex>>();
	  ArrayList<Vertex> dataMatches = new ArrayList<Vertex>();
	  
	  Iterator<Vertex> queryVertices = query.vertexSet().iterator();
	  Iterator<Vertex> dataVertices = data.vertexSet().iterator();
	  
	  ArrayList<Vertex> queryVerticesList = new ArrayList<Vertex>();
	  ArrayList<Vertex> dataVerticesList = new ArrayList<Vertex>();
	  
	  while(queryVertices.hasNext())
	  {
		  queryVerticesList.add(queryVertices.next());
	  }
	  while(dataVertices.hasNext())
	  {
		  dataVerticesList.add(dataVertices.next());
	  }
	  
	  for(Vertex q : queryVerticesList)
	  {
		  for (Vertex d : dataVerticesList)
		  {
			  if (q.getType().equals(d.getType()))
			  {
				  dataMatches.add(d);
			  }
		  }
		  searchSpace.put(q, dataMatches);
		  dataMatches = new ArrayList<Vertex>();
	  }
	  return searchSpace;  
  }

  /**
   * This function is the naive subgraph matching algorithm
   * @param query - the query graph
   * @param data - the data graph
   * @param searchSpace - the search space
   * @param searchOrder - the search order
   * @param i - the iteration
   * @param s - the solution space
   */
  public static HashMap<Vertex, Vertex> RunSM(DirectedGraph<Vertex, Edge> query, DirectedGraph<Vertex, Edge> data, HashMap<Vertex, ArrayList<Vertex>> searchSpace, ArrayList<Vertex> searchOrder, Integer i, HashMap<Vertex, Vertex> s) 
  {
	  if (query.vertexSet().size() == s.size()) {
		  return s;
	  } else {
		// get the value from search order specified by the key at i
		Vertex u = (Vertex)searchOrder.get(i);
		// the search space for u is equal to the value specified by the key which is u
		ArrayList<Vertex> uSearchSpace = (ArrayList<Vertex>) searchSpace.get(u);
		// for every vertex in the uSearchSpace
		for (Vertex v : uSearchSpace) 
		{
				// put in the s map
				s.put(u, v);
				// recurse by calling RunSM with i+1
				RunSM(query, data, searchSpace, searchOrder, i+1, s);
				// remove u from the s map
				//s.remove(u);
	  		}  
	  }
	  return s;
  	}
}