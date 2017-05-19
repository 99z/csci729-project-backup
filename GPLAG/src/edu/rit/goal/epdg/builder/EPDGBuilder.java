package edu.rit.goal.epdg.builder;

import edu.rit.goal.epdg.builder.ParsingResult.Language;
import edu.rit.goal.epdg.java.Java8EPDGParser;

public class EPDGBuilder 
{
	private Java8EPDGParser javaParser;

	public EPDGBuilder() 
	{
		javaParser = new Java8EPDGParser();
	}

	public ParsingResult fromCode(String code) 
	{
		ParsingResult result = new ParsingResult();

		// For Java, we need to wrap the code with a class and a method.
		String wrapped = wrapSubmission(code);
		javaParser.parse(wrapped, 2);
		result = new ParsingResult(Language.JAVA, javaParser.getGraphs());

		return result;
	}

	private String wrapSubmission(String submission) 
	{
		StringBuilder sb = new StringBuilder("public class Submission {\n");
		sb.append(submission);
		sb.append("}");
		return sb.toString();
	}

}
