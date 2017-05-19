package edu.rit.goal.epdg.object;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Vertex {
	private int id;
	private VertexType type;
	private Set<VertexSubtype> subtypes;
	private String label, assignedVariable;
	private final Set<String> readingVariables;

	public Vertex(final int id) {
		this.id = id;
		readingVariables = new HashSet<>();
	}

	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
	    	this.id = id;
	}

	public String getAssignedVariable() {
		return assignedVariable;
	}

	public void setAssignedVariable(final String assignedVariable) {
		this.assignedVariable = assignedVariable;
	}

	public VertexType getType() {
		return type;
	}

	public void setType(final VertexType type) {
		this.type = type;
	}

	public Set<VertexSubtype> getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(final Set<VertexSubtype> subtypes) {
		this.subtypes = subtypes;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Set<String> getReadingVariables() {
		return readingVariables;
	}

	@Override
	public String toString() {
		return id + "-" + type + "-" + label;
	}

	public String getHardCode() {
		String hardCode = label.replaceAll("[a-zA-Z]", "");
		hardCode = hardCode.replaceAll(" ", "");
		return hardCode;
	}

	public LinkedHashSet<String> getVarSet() {
		final LinkedHashSet<String> variables = new LinkedHashSet<>();
		final String labelCopy = new String(label);
		String alphaOnly = labelCopy.replaceAll("[^\\p{Alpha}]+", ",");
		alphaOnly = alphaOnly.replaceAll("length", "");
		alphaOnly = alphaOnly.replaceAll(",,", ",");
		alphaOnly = alphaOnly.replaceAll(" ", "");
		final String[] vars = alphaOnly.split(",");
		for (final String var : vars) {
			if (readingVariables.contains(var)) {
				variables.add(var);
			}
		}
		return variables;
	}

}
