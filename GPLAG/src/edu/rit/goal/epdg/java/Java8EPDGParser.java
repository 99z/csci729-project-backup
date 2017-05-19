package edu.rit.goal.epdg.java;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import edu.rit.goal.epdg.builder.IEPDGParser;
import edu.rit.goal.epdg.java.antlr.Java8Lexer;
import edu.rit.goal.epdg.java.antlr.Java8Parser;
import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;
import edu.rit.goal.epdg.object.VertexType;

public class Java8EPDGParser implements IEPDGParser {
	private Java8BaseListener listener;
	private final Map<String, DirectedGraph<Vertex, Edge>> graphs = new HashMap<>();
	private final Map<String, Map<VertexType, List<Vertex>>> verticesByType = new HashMap<>();

	@Override
	public void parse(String program, int errorLineOffset) {
		final Lexer lexer = new Java8Lexer(new ANTLRInputStream(program));
		lexer.removeErrorListeners();
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final Java8Parser parser = new Java8Parser(tokens);

		listener = new Java8BaseListener(errorLineOffset);

		parser.removeErrorListeners();
		parser.addErrorListener(new ErrorListener(listener));

		final ParserRuleContext t = parser.compilationUnit();
		final ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(listener, t);

		// Generate the graphs.
		for (final String method : listener.getMethods()) {
			final DirectedGraph<Vertex, Edge> graph = new DefaultDirectedGraph<>(Edge.class);
			graphs.put(method, graph);
			verticesByType.put(method, new HashMap<>());

			for (final Vertex current : listener.getVertices(method)) {
				graph.addVertex(current);

				List<Vertex> list = verticesByType.get(method).get(current.getType());
				if (list == null)
					verticesByType.get(method).put(current.getType(), list = new ArrayList<>());
				list.add(current);
			}
			for (final Edge current : listener.getEdges(method))
				graph.addEdge(current.getFromVertex(), current.getToVertex(), current);
		}
	}

	public Set<String> getMethods() {
		return listener.getMethods();
	}

	public Map<String, DirectedGraph<Vertex, Edge>> getGraphs() {
		return graphs;
	}

	public Map<String, Map<VertexType, List<Vertex>>> getVerticesByType() {
		return verticesByType;
	}

}