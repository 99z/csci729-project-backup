package edu.rit.goal.epdg.builder;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.DirectedGraph;

import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.Vertex;

public class ParsingResult {

	public static enum Language {
		NONE, JAVA, PYTHON
	}

	private Language parsedLanguage;
	private Map<String, DirectedGraph<Vertex, Edge>> epdgs;

	public ParsingResult() {
		parsedLanguage = Language.NONE;
		epdgs = new HashMap<>();
	}

	public ParsingResult(Language parsedLanguage, Map<String, DirectedGraph<Vertex, Edge>> epdgs) {
		super();
		this.parsedLanguage = parsedLanguage;
		this.epdgs = epdgs;
	}

	public boolean isEmpty() {
		return epdgs.values().stream().allMatch(g -> g.vertexSet().isEmpty());
	}

	public Language getParsedLanguage() {
		if (epdgs == null || epdgs.isEmpty() || this.isEmpty()) {
			return Language.NONE;
		}
		return parsedLanguage;
	}

	public void setParsedLanguage(Language parsedLanguage) {
		this.parsedLanguage = parsedLanguage;
	}

	public Map<String, DirectedGraph<Vertex, Edge>> getEpdgs() {
		return epdgs;
	}

	public void setEpdgs(Map<String, DirectedGraph<Vertex, Edge>> epdgs) {
		this.epdgs = epdgs;
	}

}
