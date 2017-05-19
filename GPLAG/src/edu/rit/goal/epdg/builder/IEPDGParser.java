package edu.rit.goal.epdg.builder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;

import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;
import edu.rit.goal.epdg.object.VertexType;

public interface IEPDGParser {

	void parse(String program, int errorLineOffset);

	Set<String> getMethods();

	Map<String, DirectedGraph<Vertex, Edge>> getGraphs();

	Map<String, Map<VertexType, List<Vertex>>> getVerticesByType();

}
