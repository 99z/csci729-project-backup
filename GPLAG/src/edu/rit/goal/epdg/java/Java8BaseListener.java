package edu.rit.goal.epdg.java;

// Generated from Java8.g4 by ANTLR 4.5.1

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.rit.goal.epdg.java.antlr.Java8Listener;
import edu.rit.goal.epdg.java.antlr.Java8Parser;
import edu.rit.goal.epdg.object.Edge;
import edu.rit.goal.epdg.object.EdgeType;
import edu.rit.goal.epdg.object.Vertex;
import edu.rit.goal.epdg.object.VertexSubtype;
import edu.rit.goal.epdg.object.VertexType;

/**
 * This class provides an empty implementation of {@link Java8Listener}, which
 * can be extended to create a listener which only needs to handle a subset of
 * the available methods.
 */

public class Java8BaseListener implements Java8Listener {
	// TODO 0: Need to take the scope of variables into account!

	private String currentMethod = "";

	private Map<String, List<Vertex>> vertices = new HashMap<>();
	private Map<String, List<Edge>> edges = new HashMap<>();

	private Map<String, Vertex> lastAppearanceOfVariables = new HashMap<>();
	private int vertexCounter;
	private Stack<Vertex> controlStack = new Stack<>();

	private List<String> errorMessages = new ArrayList<>();

	private int errorLineOffset;

	public Java8BaseListener(int errorLineOffset) {
		this.errorLineOffset = errorLineOffset;
	}

	// METHODS & PARAMETERS.

	private boolean collectExpressionVars;

	@Override
	public void enterMethodHeader(Java8Parser.MethodHeaderContext ctx) {
	}

	@Override
	public void exitMethodHeader(Java8Parser.MethodHeaderContext ctx) {
	}

	@Override
	public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
	}

	@Override
	public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {

	}

	@Override
	public void enterMethodModifier(Java8Parser.MethodModifierContext ctx) {
	}

	@Override
	public void exitMethodModifier(Java8Parser.MethodModifierContext ctx) {
	}

	@Override
	public void enterMethodBody(Java8Parser.MethodBodyContext ctx) {
	}

	@Override
	public void exitMethodBody(Java8Parser.MethodBodyContext ctx) {
	}

	// methodDeclarator: Identifier '(' formalParameterList? ')' dims?.
	@Override
	public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
		currentMethod = ctx.getChild(0).getText();
		vertices.put(currentMethod, new ArrayList<>());
		edges.put(currentMethod, new ArrayList<>());
		lastAppearanceOfVariables.clear();
		vertexCounter = 0;
		controlStack.clear();
	}

	@Override
	public void exitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
	}

	// One specific parameter: variableModifier* unannType variableDeclaratorId.

	@Override
	public void enterFormalParameter(Java8Parser.FormalParameterContext ctx) {
	}

	@Override
	public void exitFormalParameter(Java8Parser.FormalParameterContext ctx) {
		dealWithDeclaration();
	}

	// BLOCK
	@Override
	public void enterBlock(Java8Parser.BlockContext ctx) {
	}

	@Override
	public void exitBlock(Java8Parser.BlockContext ctx) {
	}

	@Override
	public void enterBlockStatements(Java8Parser.BlockStatementsContext ctx) {
	}

	@Override
	public void exitBlockStatements(Java8Parser.BlockStatementsContext ctx) {
	}

	@Override
	public void enterBlockStatement(Java8Parser.BlockStatementContext ctx) {
	}

	@Override
	public void exitBlockStatement(Java8Parser.BlockStatementContext ctx) {
	}

	@Override
	public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) {
	}

	@Override
	public void exitLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) {
	}

	// variableModifier* unannType variableDeclaratorList

	@Override
	public void enterLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
	}

	@Override
	public void exitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
	}

	// FOR
	private Vertex delayedNode;
	private boolean forUpdate;

	@Override
	public void enterBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
	}

	@Override
	public void exitBasicForStatement(Java8Parser.BasicForStatementContext ctx) {
		controlStack.pop();
	}

	@Override
	public void enterForStatement(Java8Parser.ForStatementContext ctx) {
	}

	@Override
	public void exitForStatement(Java8Parser.ForStatementContext ctx) {
		if (delayedNode != null) {
			expressionVariables.addAll(delayedNode.getReadingVariables());
			createDataEdges(delayedNode);
			lastAppearanceOfVariables.put(delayedNode.getAssignedVariable(), delayedNode);

			expressionVariables.clear();
			delayedNode = null;
		}
	}

	@Override
	public void enterForInit(Java8Parser.ForInitContext ctx) {
	}

	@Override
	public void exitForInit(Java8Parser.ForInitContext ctx) {
	}

	@Override
	public void enterForUpdate(Java8Parser.ForUpdateContext ctx) {
		forUpdate = true;
	}

	@Override
	public void exitForUpdate(Java8Parser.ForUpdateContext ctx) {
		forUpdate = false;
	}

	@Override
	public void enterForExpression(Java8Parser.ForExpressionContext ctx) {
		dealWithEnterLoop(ctx);
	}

	@Override
	public void exitForExpression(Java8Parser.ForExpressionContext ctx) {
		dealWithExitLoop(ctx);
	}

	private void dealWithEnterLoop(ParserRuleContext ctx) {
		collectExpressionVars = true;
	}

	private Vertex dealWithExitLoop(ParserRuleContext ctx) {
		Vertex v = dealWithExitCondition(ctx);

		// Create self edge.
		createEdge(v, v, EdgeType.CTRL);

		return v;
	}

	// WHILE
	@Override
	public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
	}

	@Override
	public void exitWhileStatement(Java8Parser.WhileStatementContext ctx) {
		controlStack.pop();
	}

	@Override
	public void enterWhileCondition(Java8Parser.WhileConditionContext ctx) {
		dealWithEnterLoop(ctx);
	}

	@Override
	public void exitWhileCondition(Java8Parser.WhileConditionContext ctx) {
		dealWithExitLoop(ctx);
	}

	// EXPRESSIONS
	private String varAssigned;
	private Set<String> expressionVariables = new HashSet<>();

	@Override
	public void enterExpression(Java8Parser.ExpressionContext ctx) {
	}

	@Override
	public void exitExpression(Java8Parser.ExpressionContext ctx) {
	}

	// expressionName : Identifier | ambiguousName '.' Identifier ;

	@Override
	public void enterExpressionName(Java8Parser.ExpressionNameContext ctx) {
		dealWithAmbiguousName(ctx);
	}

	@Override
	public void exitExpressionName(Java8Parser.ExpressionNameContext ctx) {
	}

	// ambiguousName : Identifier | ambiguousName '.' Identifier ;

	@Override
	public void enterAmbiguousName(Java8Parser.AmbiguousNameContext ctx) {
		dealWithAmbiguousName(ctx);
	}

	@Override
	public void exitAmbiguousName(Java8Parser.AmbiguousNameContext ctx) {
	}

	private void dealWithAmbiguousName(ParserRuleContext ctx) {
		String var = ctx.getChild(0).getText();

		// If this is a variable, add to the set.
		if (collectExpressionVars && lastAppearanceOfVariables.containsKey(var)) {
			if (isLeftHandSide)
				varAssigned = var;
			else
				expressionVariables.add(var);
		}
	}

	@Override
	public void enterTypeName(Java8Parser.TypeNameContext ctx) {
		dealWithAmbiguousName(ctx);
	}

	@Override
	public void exitTypeName(Java8Parser.TypeNameContext ctx) {
	}

	// assignment : leftHandSide assignmentOperator expression ;

	private boolean isReadingAndWriting;

	@Override
	public void enterAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) {
	}

	@Override
	public void exitAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) {
	}

	// assignmentOperator : '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<='
	// | '>>=' | '>>>=' | '&=' | '^=' | '|=' ;
	@Override
	public void enterAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) {
		isReadingAndWriting = !ctx.getText().equals("=");
	}

	@Override
	public void exitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) {
	}

	@Override
	public void enterAssignment(Java8Parser.AssignmentContext ctx) {
		dealWitEnterAssignment(ctx);
	}

	@Override
	public void exitAssignment(Java8Parser.AssignmentContext ctx) {
		dealWitExitAssignment(ctx);
		isReadingAndWriting = false;
	}

	// TODO 0: Deal with arrays!

	private boolean isLeftHandSide;

	@Override
	public void enterLeftHandSide(Java8Parser.LeftHandSideContext ctx) {
		isLeftHandSide = true;
	}

	@Override
	public void exitLeftHandSide(Java8Parser.LeftHandSideContext ctx) {
		isLeftHandSide = false;
	}

	@Override
	public void enterPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
		dealWitEnterAssignment(ctx);
	}

	@Override
	public void exitPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
		dealWithIncrementsAndDecrements();
		dealWitExitAssignment(ctx);
	}

	@Override
	public void enterPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
		dealWitEnterAssignment(ctx);
	}

	@Override
	public void exitPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
		dealWithIncrementsAndDecrements();
		dealWitExitAssignment(ctx);
	}

	@Override
	public void enterPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
		dealWitEnterAssignment(ctx);
	}

	@Override
	public void exitPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
		dealWithIncrementsAndDecrements();
		dealWitExitAssignment(ctx);
	}

	@Override
	public void enterPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) {
		dealWitEnterAssignment(ctx);
	}

	@Override
	public void exitPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) {
		dealWithIncrementsAndDecrements();
		dealWitExitAssignment(ctx);
	}

	private void dealWithIncrementsAndDecrements() {
		// TODO 0: This will not work for a[i] = ...
		varAssigned = expressionVariables.iterator().next();
	}

	private void dealWitEnterAssignment(ParserRuleContext ctx) {
		collectExpressionVars = true;
	}

	private void dealWitExitAssignment(ParserRuleContext ctx) {
		if (isReadingAndWriting)
			expressionVariables.add(varAssigned);
		Set<VertexSubtype> subtypes = getSubtypes(ctx.getText());
		Vertex v = createVertex(VertexType.ASSIGN, subtypes, ctx.getText(), varAssigned, expressionVariables);

		if (!forUpdate) {
			createDataEdges(v);
			lastAppearanceOfVariables.put(varAssigned, v);
		} else
			// If we are in the update part of a for loop, we need to delay this
			// node until the end of the statement.
			delayedNode = v;

		collectExpressionVars = false;
		expressionVariables.clear();
	}

	private void createDataEdges(Vertex v) {
		for (String var : expressionVariables)
			createEdge(lastAppearanceOfVariables.get(var), v, EdgeType.DATA);
	}

	// IF
	@Override
	public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
	}

	@Override
	public void exitIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
		controlStack.pop();
	}

	@Override
	public void enterIfCondition(Java8Parser.IfConditionContext ctx) {
		dealWithEnterCondition(ctx);
	}

	@Override
	public void exitIfCondition(Java8Parser.IfConditionContext ctx) {
		dealWithExitCondition(ctx);
	}

	private void dealWithEnterCondition(ParserRuleContext ctx) {
		collectExpressionVars = true;
	}

	private Vertex dealWithExitCondition(ParserRuleContext ctx) {
		Set<VertexSubtype> subtypes = getSubtypes(ctx.getText());
		Vertex v = createVertex(VertexType.CTRL, subtypes, ctx.getText(), null, expressionVariables);
		createDataEdges(v);

		controlStack.push(v);

		collectExpressionVars = false;
		expressionVariables.clear();

		return v;
	}

	private Set<VertexSubtype> getSubtypes(String text) {
		Set<VertexSubtype> result = new HashSet<>();
		if (text.contains("<"))
			result.add(VertexSubtype.LT);
		if (text.contains(">"))
			result.add(VertexSubtype.GT);
		if (text.contains("<="))
			result.add(VertexSubtype.LEQ);
		if (text.contains(">="))
			result.add(VertexSubtype.GEQ);
		if (text.contains("=="))
			result.add(VertexSubtype.EQ);
		if (text.contains("!="))
			result.add(VertexSubtype.INEQ);
		if (text.contains("%"))
			result.add(VertexSubtype.MOD);
		if (text.contains("&&"))
			result.add(VertexSubtype.AND);
		if (text.contains("||"))
			result.add(VertexSubtype.OR);
		if (text.contains("++"))
			result.add(VertexSubtype.INCR);
		if (text.contains("--"))
			result.add(VertexSubtype.DECR);
		if (text.contains("+="))
			result.add(VertexSubtype.SH_PLUS);
		if (text.contains("-="))
			result.add(VertexSubtype.SH_MINUS);
		if (text.contains(".print"))
			result.add(VertexSubtype.PRINT);
		if (text.contains("(") && text.contains(")"))
			result.add(VertexSubtype.CALL);
		return result;
	}

	// VARIABLES

	private String varDecl;
	private boolean declaredAndNotInitVar;

	@Override
	public void enterVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx) {
	}

	@Override
	public void exitVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx) {
	}

	// variableDeclarator : variableDeclaratorId ('=' variableInitializer)? ;
	@Override
	public void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
		declaredAndNotInitVar = ctx.getChildCount() == 1;
		collectExpressionVars = true;
	}

	@Override
	public void exitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
		if (declaredAndNotInitVar)
			dealWithDeclaration();
		else {
			varAssigned = varDecl;
			dealWitExitAssignment(ctx);
		}
		declaredAndNotInitVar = false;
	}

	private void dealWithDeclaration() {
		Vertex v = createVertex(VertexType.DECL, new HashSet<>(), varDecl, varDecl, new HashSet<>());
		lastAppearanceOfVariables.put(varDecl, v);
	}

	@Override
	public void enterVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
		varDecl = ctx.getText();
	}

	@Override
	public void exitVariableDeclaratorId(Java8Parser.VariableDeclaratorIdContext ctx) {
	}

	@Override
	public void enterVariableInitializer(Java8Parser.VariableInitializerContext ctx) {
		collectExpressionVars = true;
	}

	@Override
	public void exitVariableInitializer(Java8Parser.VariableInitializerContext ctx) {
		collectExpressionVars = false;
	}

	// METHOD INVOCATION
	@Override
	public void enterMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
		collectExpressionVars = true;
	}

	@Override
	public void exitMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
		Set<VertexSubtype> subtypes = getSubtypes(ctx.getText());
		Vertex v = createVertex(VertexType.CALL, subtypes, ctx.getText(), null, expressionVariables);
		createDataEdges(v);

		if (v.getAssignedVariable() != null)
			lastAppearanceOfVariables.put(v.getAssignedVariable(), v);

		collectExpressionVars = false;
		expressionVariables.clear();
	}

	@Override
	public void enterMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx) {
	}

	@Override
	public void exitMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx) {
	}

	// RETURN
	@Override
	public void enterReturnStatement(Java8Parser.ReturnStatementContext ctx) {
		collectExpressionVars = true;
	}

	@Override
	public void exitReturnStatement(Java8Parser.ReturnStatementContext ctx) {
		Set<VertexSubtype> subtypes = getSubtypes(ctx.getText());
		Vertex v = createVertex(VertexType.RETURN, subtypes, ctx.getText(), null, expressionVariables);
		createDataEdges(v);

		collectExpressionVars = false;
		expressionVariables.clear();
	}

	// BREAK
	@Override
	public void enterBreakStatement(Java8Parser.BreakStatementContext ctx) {
		Set<VertexSubtype> subtypes = getSubtypes(ctx.getText());
		createVertex(VertexType.BREAK, subtypes, ctx.getText(), null, new HashSet<>());
	}

	@Override
	public void exitBreakStatement(Java8Parser.BreakStatementContext ctx) {
	}

	// AUX

	private Vertex createVertex(VertexType type, Set<VertexSubtype> subtypes, String lbl, String assignedVar,
			Set<String> refVars) {
		Vertex v = new Vertex(vertexCounter++);
		v.setAssignedVariable(assignedVar);
		v.setType(type);
		v.setSubtypes(subtypes);
		v.setLabel(lbl);
		v.getReadingVariables().addAll(refVars);
		vertices.get(currentMethod).add(v);

		if (!controlStack.isEmpty())
			createEdge(controlStack.peek(), v, EdgeType.CTRL);

		return v;
	}

	private Edge createEdge(Vertex from, Vertex to, EdgeType type) {
		Edge e = new Edge(from, to, type);
		edges.get(currentMethod).add(e);
		return e;
	}

	// NOT SUPPORTED
	private void addNotSupportedErrorMsg(String structure, ParserRuleContext ctx) {
		errorMessages.add(
				structure + " in line " + (ctx.getStart().getLine() - errorLineOffset) + " is not currently supported");
	}

	@Override
	public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
		addNotSupportedErrorMsg("Do-While", ctx);
	}

	@Override
	public void exitDoStatement(Java8Parser.DoStatementContext ctx) {
	}

	@Override
	public void enterLambdaExpression(Java8Parser.LambdaExpressionContext ctx) {
		addNotSupportedErrorMsg("Lambda", ctx);
	}

	@Override
	public void exitLambdaExpression(Java8Parser.LambdaExpressionContext ctx) {
	}

	@Override
	public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
		addNotSupportedErrorMsg("If-Else", ctx);
	}

	@Override
	public void exitIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
	}

	@Override
	public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
		addNotSupportedErrorMsg("Switch", ctx);
	}

	@Override
	public void exitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
	}

	@Override
	public void enterEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
		addNotSupportedErrorMsg("For", ctx);
	}

	@Override
	public void exitEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
	}

	@Override
	public void enterClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx) {
		addNotSupportedErrorMsg("New", ctx);
	}

	@Override
	public void exitClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx) {
	}

	@Override
	public void enterClassInstanceCreationExpression_lf_primary(
			Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
		addNotSupportedErrorMsg("New", ctx);
	}

	@Override
	public void exitClassInstanceCreationExpression_lf_primary(
			Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
	}

	@Override
	public void enterClassInstanceCreationExpression_lfno_primary(
			Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
		addNotSupportedErrorMsg("New", ctx);
	}

	@Override
	public void exitClassInstanceCreationExpression_lfno_primary(
			Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
	}

	// SYNTAX ERROR
	public void addSyntaxErrorMsg(int line, int charPositionInLine, String msg) {
		errorMessages.add(
				"Syntax error in line " + (line - errorLineOffset) + " and column " + charPositionInLine + ": " + msg);
	}

	@Override
	public void enterUnannType(Java8Parser.UnannTypeContext ctx) {
	}

	@Override
	public void exitUnannType(Java8Parser.UnannTypeContext ctx) {
	}

	@Override
	public void enterTypeParameter(Java8Parser.TypeParameterContext ctx) {
	}

	@Override
	public void enterIfThenElseStatementNoShortIf(Java8Parser.IfThenElseStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitIfThenElseStatementNoShortIf(Java8Parser.IfThenElseStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
		ctx.toString();
	}

	@Override
	public void enterLiteral(Java8Parser.LiteralContext ctx) {
	}

	@Override
	public void exitLiteral(Java8Parser.LiteralContext ctx) {
	}

	@Override
	public void enterType(Java8Parser.TypeContext ctx) {
	}

	@Override
	public void exitType(Java8Parser.TypeContext ctx) {
	}

	@Override
	public void enterPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
	}

	@Override
	public void exitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
	}

	@Override
	public void enterNumericType(Java8Parser.NumericTypeContext ctx) {
	}

	@Override
	public void exitNumericType(Java8Parser.NumericTypeContext ctx) {
	}

	@Override
	public void enterIntegralType(Java8Parser.IntegralTypeContext ctx) {
	}

	@Override
	public void exitIntegralType(Java8Parser.IntegralTypeContext ctx) {
	}

	@Override
	public void enterFloatingPointType(Java8Parser.FloatingPointTypeContext ctx) {
	}

	@Override
	public void exitFloatingPointType(Java8Parser.FloatingPointTypeContext ctx) {
	}

	@Override
	public void enterReferenceType(Java8Parser.ReferenceTypeContext ctx) {
	}

	@Override
	public void exitReferenceType(Java8Parser.ReferenceTypeContext ctx) {
	}

	@Override
	public void enterClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void exitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterClassType(Java8Parser.ClassTypeContext ctx) {
	}

	@Override
	public void exitClassType(Java8Parser.ClassTypeContext ctx) {
	}

	@Override
	public void enterClassType_lf_classOrInterfaceType(Java8Parser.ClassType_lf_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void exitClassType_lf_classOrInterfaceType(Java8Parser.ClassType_lf_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterClassType_lfno_classOrInterfaceType(Java8Parser.ClassType_lfno_classOrInterfaceTypeContext ctx) {
		// System.out.println("Class Type " +ctx.getText());
	}

	@Override
	public void exitClassType_lfno_classOrInterfaceType(Java8Parser.ClassType_lfno_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterInterfaceType(Java8Parser.InterfaceTypeContext ctx) {
	}

	@Override
	public void exitInterfaceType(Java8Parser.InterfaceTypeContext ctx) {
	}

	@Override
	public void enterInterfaceType_lf_classOrInterfaceType(
			Java8Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void exitInterfaceType_lf_classOrInterfaceType(
			Java8Parser.InterfaceType_lf_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterInterfaceType_lfno_classOrInterfaceType(
			Java8Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx) {
		// System.out.println("Class Type " +ctx.getText());
	}

	@Override
	public void exitInterfaceType_lfno_classOrInterfaceType(
			Java8Parser.InterfaceType_lfno_classOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterTypeVariable(Java8Parser.TypeVariableContext ctx) {
		// System.out.println("Line number "+"218 "+"Type Variable"
		// +ctx.getText());
	}

	@Override
	public void exitTypeVariable(Java8Parser.TypeVariableContext ctx) {
	}

	@Override
	public void enterArrayType(Java8Parser.ArrayTypeContext ctx) {
	}

	@Override
	public void exitArrayType(Java8Parser.ArrayTypeContext ctx) {
	}

	@Override
	public void enterDims(Java8Parser.DimsContext ctx) {
		// System.out.println("Dim Type " +ctx.getText());
	}

	@Override
	public void exitDims(Java8Parser.DimsContext ctx) {
	}

	@Override
	public void exitTypeParameter(Java8Parser.TypeParameterContext ctx) {
	}

	@Override
	public void enterTypeParameterModifier(Java8Parser.TypeParameterModifierContext ctx) {
	}

	@Override
	public void exitTypeParameterModifier(Java8Parser.TypeParameterModifierContext ctx) {
	}

	@Override
	public void enterTypeBound(Java8Parser.TypeBoundContext ctx) {
	}

	@Override
	public void exitTypeBound(Java8Parser.TypeBoundContext ctx) {
	}

	@Override
	public void enterAdditionalBound(Java8Parser.AdditionalBoundContext ctx) {
	}

	@Override
	public void exitAdditionalBound(Java8Parser.AdditionalBoundContext ctx) {
	}

	@Override
	public void enterTypeArguments(Java8Parser.TypeArgumentsContext ctx) {
	}

	@Override
	public void exitTypeArguments(Java8Parser.TypeArgumentsContext ctx) {
	}

	@Override
	public void enterTypeArgumentList(Java8Parser.TypeArgumentListContext ctx) {
	}

	@Override
	public void exitTypeArgumentList(Java8Parser.TypeArgumentListContext ctx) {
	}

	@Override
	public void enterTypeArgument(Java8Parser.TypeArgumentContext ctx) {
	}

	@Override
	public void exitTypeArgument(Java8Parser.TypeArgumentContext ctx) {
	}

	@Override
	public void enterWildcard(Java8Parser.WildcardContext ctx) {
	}

	@Override
	public void exitWildcard(Java8Parser.WildcardContext ctx) {
	}

	@Override
	public void enterWildcardBounds(Java8Parser.WildcardBoundsContext ctx) {
	}

	@Override
	public void exitWildcardBounds(Java8Parser.WildcardBoundsContext ctx) {
	}

	@Override
	public void enterPackageName(Java8Parser.PackageNameContext ctx) {
		// System.out.println("Packagename Type " +ctx.getText());
	}

	@Override
	public void exitPackageName(Java8Parser.PackageNameContext ctx) {
	}

	@Override
	public void enterPackageOrTypeName(Java8Parser.PackageOrTypeNameContext ctx) {
	}

	@Override
	public void exitPackageOrTypeName(Java8Parser.PackageOrTypeNameContext ctx) {
	}

	@Override
	public void enterMethodName(Java8Parser.MethodNameContext ctx) {
	}

	@Override
	public void exitMethodName(Java8Parser.MethodNameContext ctx) {
	}

	@Override
	public void enterCompilationUnit(Java8Parser.CompilationUnitContext ctx) {

		// System.out.println("Compilation Name " +ctx.getText());

	}

	@Override
	public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
	}

	@Override
	public void enterPackageDeclaration(Java8Parser.PackageDeclarationContext ctx) {
	}

	@Override
	public void exitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx) {
	}

	@Override
	public void enterPackageModifier(Java8Parser.PackageModifierContext ctx) {
		// System.out.println("PackageModifier Type " +ctx.getText());
	}

	@Override
	public void exitPackageModifier(Java8Parser.PackageModifierContext ctx) {
	}

	@Override
	public void enterImportDeclaration(Java8Parser.ImportDeclarationContext ctx) {
		// System.out.println("Import Type " +ctx.getText());
	}

	@Override
	public void exitImportDeclaration(Java8Parser.ImportDeclarationContext ctx) {
	}

	@Override
	public void enterSingleTypeImportDeclaration(Java8Parser.SingleTypeImportDeclarationContext ctx) {
		// System.out.println("Import Type " +ctx.getText());
	}

	@Override
	public void exitSingleTypeImportDeclaration(Java8Parser.SingleTypeImportDeclarationContext ctx) {
	}

	@Override
	public void enterTypeImportOnDemandDeclaration(Java8Parser.TypeImportOnDemandDeclarationContext ctx) {
	}

	@Override
	public void exitTypeImportOnDemandDeclaration(Java8Parser.TypeImportOnDemandDeclarationContext ctx) {
	}

	@Override
	public void enterSingleStaticImportDeclaration(Java8Parser.SingleStaticImportDeclarationContext ctx) {
	}

	@Override
	public void exitSingleStaticImportDeclaration(Java8Parser.SingleStaticImportDeclarationContext ctx) {
	}

	@Override
	public void enterStaticImportOnDemandDeclaration(Java8Parser.StaticImportOnDemandDeclarationContext ctx) {
	}

	@Override
	public void exitStaticImportOnDemandDeclaration(Java8Parser.StaticImportOnDemandDeclarationContext ctx) {
	}

	@Override
	public void enterTypeDeclaration(Java8Parser.TypeDeclarationContext ctx) {
		// System.out.println("TypeDeclaration " +ctx.getText());
	}

	@Override
	public void exitTypeDeclaration(Java8Parser.TypeDeclarationContext ctx) {
	}

	@Override
	public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
		// System.out.println(" enterClassDeclaration " +ctx.getText());

	}

	@Override
	public void exitClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
	}

	@Override
	public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {

		// System.out.println(" enterClassDeclaration " +ctx.getText());

	}

	@Override
	public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
	}

	@Override
	public void enterClassModifier(Java8Parser.ClassModifierContext ctx) {

		// System.out.println(" ClassModifier " +ctx.getText());

	}

	@Override
	public void exitClassModifier(Java8Parser.ClassModifierContext ctx) {
	}

	@Override
	public void enterTypeParameters(Java8Parser.TypeParametersContext ctx) {
	}

	@Override
	public void exitTypeParameters(Java8Parser.TypeParametersContext ctx) {
	}

	@Override
	public void enterTypeParameterList(Java8Parser.TypeParameterListContext ctx) {
	}

	@Override
	public void exitTypeParameterList(Java8Parser.TypeParameterListContext ctx) {
	}

	@Override
	public void enterSuperclass(Java8Parser.SuperclassContext ctx) {
	}

	@Override
	public void exitSuperclass(Java8Parser.SuperclassContext ctx) {
	}

	@Override
	public void enterSuperinterfaces(Java8Parser.SuperinterfacesContext ctx) {
	}

	@Override
	public void exitSuperinterfaces(Java8Parser.SuperinterfacesContext ctx) {
	}

	@Override
	public void enterInterfaceTypeList(Java8Parser.InterfaceTypeListContext ctx) {
	}

	@Override
	public void exitInterfaceTypeList(Java8Parser.InterfaceTypeListContext ctx) {
	}

	@Override
	public void enterClassBody(Java8Parser.ClassBodyContext ctx) {
	}

	@Override
	public void exitClassBody(Java8Parser.ClassBodyContext ctx) {
	}

	@Override
	public void enterClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx) {
	}

	@Override
	public void exitClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx) {
	}

	@Override
	public void enterClassMemberDeclaration(Java8Parser.ClassMemberDeclarationContext ctx) {
		// System.out.println(" Class Member " +ctx.getText());

	}

	@Override
	public void exitClassMemberDeclaration(Java8Parser.ClassMemberDeclarationContext ctx) {
	}

	@Override
	public void enterFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {

		// System.out.println(" ClassField " +ctx.getText());
	}

	@Override
	public void exitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
	}

	@Override
	public void enterFieldModifier(Java8Parser.FieldModifierContext ctx) {
	}

	@Override
	public void exitFieldModifier(Java8Parser.FieldModifierContext ctx) {
	}

	@Override
	public void enterUnannPrimitiveType(Java8Parser.UnannPrimitiveTypeContext ctx) {
	}

	@Override
	public void exitUnannPrimitiveType(Java8Parser.UnannPrimitiveTypeContext ctx) {
	}

	@Override
	public void enterUnannReferenceType(Java8Parser.UnannReferenceTypeContext ctx) {
	}

	@Override
	public void exitUnannReferenceType(Java8Parser.UnannReferenceTypeContext ctx) {
	}

	@Override
	public void enterUnannClassOrInterfaceType(Java8Parser.UnannClassOrInterfaceTypeContext ctx) {

		// System.out.println("Class Name "+ctx.getText());

	}

	@Override
	public void exitUnannClassOrInterfaceType(Java8Parser.UnannClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannClassType(Java8Parser.UnannClassTypeContext ctx) {
	}

	@Override
	public void exitUnannClassType(Java8Parser.UnannClassTypeContext ctx) {
	}

	@Override
	public void enterUnannClassType_lf_unannClassOrInterfaceType(
			Java8Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx) {
		// System.out.println("Class Name "+ctx.getText());
	}

	@Override
	public void exitUnannClassType_lf_unannClassOrInterfaceType(
			Java8Parser.UnannClassType_lf_unannClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannClassType_lfno_unannClassOrInterfaceType(
			Java8Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx) {
		// System.out.println("Class Name "+ctx.getText());
	}

	@Override
	public void exitUnannClassType_lfno_unannClassOrInterfaceType(
			Java8Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannInterfaceType(Java8Parser.UnannInterfaceTypeContext ctx) {
	}

	@Override
	public void exitUnannInterfaceType(Java8Parser.UnannInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannInterfaceType_lf_unannClassOrInterfaceType(
			Java8Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx) {
		// System.out.println("Class Name "+ctx.getText());
	}

	@Override
	public void exitUnannInterfaceType_lf_unannClassOrInterfaceType(
			Java8Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannInterfaceType_lfno_unannClassOrInterfaceType(
			Java8Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx) {
		// System.out.println("Class Name "+ctx.getText());

	}

	@Override
	public void exitUnannInterfaceType_lfno_unannClassOrInterfaceType(
			Java8Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext ctx) {
	}

	@Override
	public void enterUnannTypeVariable(Java8Parser.UnannTypeVariableContext ctx) {
	}

	@Override
	public void exitUnannTypeVariable(Java8Parser.UnannTypeVariableContext ctx) {
	}

	@Override
	public void enterUnannArrayType(Java8Parser.UnannArrayTypeContext ctx) {
	}

	@Override
	public void exitUnannArrayType(Java8Parser.UnannArrayTypeContext ctx) {
	}

	@Override
	public void enterResult(Java8Parser.ResultContext ctx) {
	}

	@Override
	public void exitResult(Java8Parser.ResultContext ctx) {
	}

	@Override
	public void enterFormalParameterList(Java8Parser.FormalParameterListContext ctx) {
	}

	@Override
	public void exitFormalParameterList(Java8Parser.FormalParameterListContext ctx) {
		// System.out.println("FORMAL PARAMETER "+ctx.getText());
	}

	@Override
	public void enterFormalParameters(Java8Parser.FormalParametersContext ctx) {
		// System.out.println("FORRMAL PARAMETERS " +ctx.getText());
	}

	@Override
	public void exitFormalParameters(Java8Parser.FormalParametersContext ctx) {
	}

	@Override
	public void enterVariableModifier(Java8Parser.VariableModifierContext ctx) {
	}

	@Override
	public void exitVariableModifier(Java8Parser.VariableModifierContext ctx) {
	}

	@Override
	public void enterLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
	}

	@Override
	public void exitLastFormalParameter(Java8Parser.LastFormalParameterContext ctx) {
	}

	@Override
	public void enterReceiverParameter(Java8Parser.ReceiverParameterContext ctx) {
	}

	@Override
	public void exitReceiverParameter(Java8Parser.ReceiverParameterContext ctx) {
	}

	@Override
	public void enterThrows_(Java8Parser.Throws_Context ctx) {
	}

	@Override
	public void exitThrows_(Java8Parser.Throws_Context ctx) {
	}

	@Override
	public void enterExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
	}

	@Override
	public void exitExceptionTypeList(Java8Parser.ExceptionTypeListContext ctx) {
	}

	@Override
	public void enterExceptionType(Java8Parser.ExceptionTypeContext ctx) {
	}

	@Override
	public void exitExceptionType(Java8Parser.ExceptionTypeContext ctx) {
	}

	@Override
	public void enterInstanceInitializer(Java8Parser.InstanceInitializerContext ctx) {
	}

	@Override
	public void exitInstanceInitializer(Java8Parser.InstanceInitializerContext ctx) {
	}

	@Override
	public void enterStaticInitializer(Java8Parser.StaticInitializerContext ctx) {
	}

	@Override
	public void exitStaticInitializer(Java8Parser.StaticInitializerContext ctx) {
	}

	@Override
	public void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
	}

	@Override
	public void exitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
	}

	@Override
	public void enterConstructorModifier(Java8Parser.ConstructorModifierContext ctx) {
	}

	@Override
	public void exitConstructorModifier(Java8Parser.ConstructorModifierContext ctx) {
	}

	@Override
	public void enterConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) {
	}

	@Override
	public void exitConstructorDeclarator(Java8Parser.ConstructorDeclaratorContext ctx) {
	}

	@Override
	public void enterSimpleTypeName(Java8Parser.SimpleTypeNameContext ctx) {
	}

	@Override
	public void exitSimpleTypeName(Java8Parser.SimpleTypeNameContext ctx) {
	}

	@Override
	public void enterConstructorBody(Java8Parser.ConstructorBodyContext ctx) {
	}

	@Override
	public void exitConstructorBody(Java8Parser.ConstructorBodyContext ctx) {
	}

	@Override
	public void enterExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx) {
	}

	@Override
	public void exitExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx) {
	}

	@Override
	public void enterEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
	}

	@Override
	public void exitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx) {
	}

	@Override
	public void enterEnumBody(Java8Parser.EnumBodyContext ctx) {
	}

	@Override
	public void exitEnumBody(Java8Parser.EnumBodyContext ctx) {
	}

	@Override
	public void enterEnumConstantList(Java8Parser.EnumConstantListContext ctx) {
	}

	@Override
	public void exitEnumConstantList(Java8Parser.EnumConstantListContext ctx) {
	}

	@Override
	public void enterEnumConstant(Java8Parser.EnumConstantContext ctx) {
	}

	@Override
	public void exitEnumConstant(Java8Parser.EnumConstantContext ctx) {
	}

	@Override
	public void enterEnumConstantModifier(Java8Parser.EnumConstantModifierContext ctx) {
	}

	@Override
	public void exitEnumConstantModifier(Java8Parser.EnumConstantModifierContext ctx) {
	}

	@Override
	public void enterEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx) {
	}

	@Override
	public void exitEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx) {
	}

	@Override
	public void enterInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) {
	}

	@Override
	public void exitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) {
	}

	@Override
	public void enterNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) {
	}

	@Override
	public void exitNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) {
	}

	@Override
	public void enterInterfaceModifier(Java8Parser.InterfaceModifierContext ctx) {
	}

	@Override
	public void exitInterfaceModifier(Java8Parser.InterfaceModifierContext ctx) {
	}

	@Override
	public void enterExtendsInterfaces(Java8Parser.ExtendsInterfacesContext ctx) {
	}

	@Override
	public void exitExtendsInterfaces(Java8Parser.ExtendsInterfacesContext ctx) {
	}

	@Override
	public void enterInterfaceBody(Java8Parser.InterfaceBodyContext ctx) {
	}

	@Override
	public void exitInterfaceBody(Java8Parser.InterfaceBodyContext ctx) {
	}

	@Override
	public void enterInterfaceMemberDeclaration(Java8Parser.InterfaceMemberDeclarationContext ctx) {
	}

	@Override
	public void exitInterfaceMemberDeclaration(Java8Parser.InterfaceMemberDeclarationContext ctx) {
	}

	@Override
	public void enterConstantDeclaration(Java8Parser.ConstantDeclarationContext ctx) {
	}

	@Override
	public void exitConstantDeclaration(Java8Parser.ConstantDeclarationContext ctx) {
	}

	@Override
	public void enterConstantModifier(Java8Parser.ConstantModifierContext ctx) {
	}

	@Override
	public void exitConstantModifier(Java8Parser.ConstantModifierContext ctx) {
	}

	@Override
	public void enterInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx) {
	}

	@Override
	public void exitInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx) {
	}

	@Override
	public void enterInterfaceMethodModifier(Java8Parser.InterfaceMethodModifierContext ctx) {
	}

	@Override
	public void exitInterfaceMethodModifier(Java8Parser.InterfaceMethodModifierContext ctx) {
	}

	@Override
	public void enterAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx) {
	}

	@Override
	public void exitAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx) {
	}

	@Override
	public void enterAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx) {
	}

	@Override
	public void exitAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx) {
	}

	@Override
	public void enterAnnotationTypeMemberDeclaration(Java8Parser.AnnotationTypeMemberDeclarationContext ctx) {
	}

	@Override
	public void exitAnnotationTypeMemberDeclaration(Java8Parser.AnnotationTypeMemberDeclarationContext ctx) {
	}

	@Override
	public void enterAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx) {
	}

	@Override
	public void exitAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx) {
	}

	@Override
	public void enterAnnotationTypeElementModifier(Java8Parser.AnnotationTypeElementModifierContext ctx) {
	}

	@Override
	public void exitAnnotationTypeElementModifier(Java8Parser.AnnotationTypeElementModifierContext ctx) {
	}

	@Override
	public void enterDefaultValue(Java8Parser.DefaultValueContext ctx) {
	}

	@Override
	public void exitDefaultValue(Java8Parser.DefaultValueContext ctx) {
	}

	@Override
	public void enterAnnotation(Java8Parser.AnnotationContext ctx) {
	}

	@Override
	public void exitAnnotation(Java8Parser.AnnotationContext ctx) {
	}

	@Override
	public void enterNormalAnnotation(Java8Parser.NormalAnnotationContext ctx) {
	}

	@Override
	public void exitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx) {
	}

	@Override
	public void enterElementValuePairList(Java8Parser.ElementValuePairListContext ctx) {
	}

	@Override
	public void exitElementValuePairList(Java8Parser.ElementValuePairListContext ctx) {
	}

	@Override
	public void enterElementValuePair(Java8Parser.ElementValuePairContext ctx) {
	}

	@Override
	public void exitElementValuePair(Java8Parser.ElementValuePairContext ctx) {
	}

	@Override
	public void enterElementValue(Java8Parser.ElementValueContext ctx) {
	}

	@Override
	public void exitElementValue(Java8Parser.ElementValueContext ctx) {
	}

	@Override
	public void enterElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx) {
	}

	@Override
	public void exitElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx) {
	}

	@Override
	public void enterElementValueList(Java8Parser.ElementValueListContext ctx) {
	}

	@Override
	public void exitElementValueList(Java8Parser.ElementValueListContext ctx) {
	}

	@Override
	public void enterMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx) {
	}

	@Override
	public void exitMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx) {
	}

	@Override
	public void enterSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx) {
	}

	@Override
	public void exitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx) {
	}

	@Override
	public void enterArrayInitializer(Java8Parser.ArrayInitializerContext ctx) {
	}

	@Override
	public void exitArrayInitializer(Java8Parser.ArrayInitializerContext ctx) {
	}

	@Override
	public void enterVariableInitializerList(Java8Parser.VariableInitializerListContext ctx) {
	}

	@Override
	public void exitVariableInitializerList(Java8Parser.VariableInitializerListContext ctx) {
	}

	@Override
	public void enterStatement(Java8Parser.StatementContext ctx) {
	}

	@Override
	public void exitStatement(Java8Parser.StatementContext ctx) {
	}

	@Override
	public void enterStatementNoShortIf(Java8Parser.StatementNoShortIfContext ctx) {
	}

	@Override
	public void exitStatementNoShortIf(Java8Parser.StatementNoShortIfContext ctx) {
	}

	@Override
	public void enterStatementWithoutTrailingSubstatement(Java8Parser.StatementWithoutTrailingSubstatementContext ctx) {
	}

	@Override
	public void exitStatementWithoutTrailingSubstatement(Java8Parser.StatementWithoutTrailingSubstatementContext ctx) {
	}

	@Override
	public void enterEmptyStatement(Java8Parser.EmptyStatementContext ctx) {
	}

	@Override
	public void exitEmptyStatement(Java8Parser.EmptyStatementContext ctx) {
	}

	@Override
	public void enterLabeledStatement(Java8Parser.LabeledStatementContext ctx) {
	}

	@Override
	public void exitLabeledStatement(Java8Parser.LabeledStatementContext ctx) {
	}

	@Override
	public void enterLabeledStatementNoShortIf(Java8Parser.LabeledStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitLabeledStatementNoShortIf(Java8Parser.LabeledStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterExpressionStatement(Java8Parser.ExpressionStatementContext ctx) {
	}

	@Override
	public void exitExpressionStatement(Java8Parser.ExpressionStatementContext ctx) {
	}

	@Override
	public void enterStatementExpression(Java8Parser.StatementExpressionContext ctx) {
	}

	@Override
	public void exitStatementExpression(Java8Parser.StatementExpressionContext ctx) {
	}

	@Override
	public void enterAssertStatement(Java8Parser.AssertStatementContext ctx) {
	}

	@Override
	public void exitAssertStatement(Java8Parser.AssertStatementContext ctx) {
	}

	@Override
	public void enterSwitchBlock(Java8Parser.SwitchBlockContext ctx) {
	}

	@Override
	public void exitSwitchBlock(Java8Parser.SwitchBlockContext ctx) {
	}

	@Override
	public void enterSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx) {
	}

	@Override
	public void exitSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx) {
	}

	@Override
	public void enterSwitchLabels(Java8Parser.SwitchLabelsContext ctx) {
	}

	@Override
	public void exitSwitchLabels(Java8Parser.SwitchLabelsContext ctx) {
	}

	@Override
	public void enterSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
	}

	@Override
	public void exitSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
	}

	@Override
	public void enterEnumConstantName(Java8Parser.EnumConstantNameContext ctx) {
	}

	@Override
	public void exitEnumConstantName(Java8Parser.EnumConstantNameContext ctx) {
	}

	@Override
	public void enterWhileStatementNoShortIf(Java8Parser.WhileStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitWhileStatementNoShortIf(Java8Parser.WhileStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterForStatementNoShortIf(Java8Parser.ForStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitForStatementNoShortIf(Java8Parser.ForStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterBasicForStatementNoShortIf(Java8Parser.BasicForStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitBasicForStatementNoShortIf(Java8Parser.BasicForStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterStatementExpressionList(Java8Parser.StatementExpressionListContext ctx) {

	}

	@Override
	public void exitStatementExpressionList(Java8Parser.StatementExpressionListContext ctx) {
	}

	@Override
	public void enterEnhancedForStatementNoShortIf(Java8Parser.EnhancedForStatementNoShortIfContext ctx) {
	}

	@Override
	public void exitEnhancedForStatementNoShortIf(Java8Parser.EnhancedForStatementNoShortIfContext ctx) {
	}

	@Override
	public void enterContinueStatement(Java8Parser.ContinueStatementContext ctx) {
	}

	@Override
	public void exitContinueStatement(Java8Parser.ContinueStatementContext ctx) {
	}

	@Override
	public void enterThrowStatement(Java8Parser.ThrowStatementContext ctx) {
	}

	@Override
	public void exitThrowStatement(Java8Parser.ThrowStatementContext ctx) {
	}

	@Override
	public void enterSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
	}

	@Override
	public void exitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
	}

	@Override
	public void enterTryStatement(Java8Parser.TryStatementContext ctx) {
	}

	@Override
	public void exitTryStatement(Java8Parser.TryStatementContext ctx) {
	}

	@Override
	public void enterCatches(Java8Parser.CatchesContext ctx) {
	}

	@Override
	public void exitCatches(Java8Parser.CatchesContext ctx) {
	}

	@Override
	public void enterCatchClause(Java8Parser.CatchClauseContext ctx) {
	}

	@Override
	public void exitCatchClause(Java8Parser.CatchClauseContext ctx) {
	}

	@Override
	public void enterCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx) {
	}

	@Override
	public void exitCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx) {
	}

	@Override
	public void enterCatchType(Java8Parser.CatchTypeContext ctx) {
	}

	@Override
	public void exitCatchType(Java8Parser.CatchTypeContext ctx) {
	}

	@Override
	public void enterFinally_(Java8Parser.Finally_Context ctx) {
	}

	@Override
	public void exitFinally_(Java8Parser.Finally_Context ctx) {
	}

	@Override
	public void enterTryWithResourcesStatement(Java8Parser.TryWithResourcesStatementContext ctx) {
	}

	@Override
	public void exitTryWithResourcesStatement(Java8Parser.TryWithResourcesStatementContext ctx) {
	}

	@Override
	public void enterResourceSpecification(Java8Parser.ResourceSpecificationContext ctx) {
	}

	@Override
	public void exitResourceSpecification(Java8Parser.ResourceSpecificationContext ctx) {
	}

	@Override
	public void enterResourceList(Java8Parser.ResourceListContext ctx) {
	}

	@Override
	public void exitResourceList(Java8Parser.ResourceListContext ctx) {
	}

	@Override
	public void enterResource(Java8Parser.ResourceContext ctx) {
	}

	@Override
	public void exitResource(Java8Parser.ResourceContext ctx) {
	}

	@Override
	public void enterPrimary(Java8Parser.PrimaryContext ctx) {
	}

	@Override
	public void exitPrimary(Java8Parser.PrimaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray(Java8Parser.PrimaryNoNewArrayContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray(Java8Parser.PrimaryNoNewArrayContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lf_arrayAccess(Java8Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lf_arrayAccess(Java8Parser.PrimaryNoNewArray_lf_arrayAccessContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lfno_arrayAccess(Java8Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lfno_arrayAccess(Java8Parser.PrimaryNoNewArray_lfno_arrayAccessContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lf_primary(Java8Parser.PrimaryNoNewArray_lf_primaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(
			Java8Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary(
			Java8Parser.PrimaryNoNewArray_lf_primary_lf_arrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(
			Java8Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary(
			Java8Parser.PrimaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lfno_primary(Java8Parser.PrimaryNoNewArray_lfno_primaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(
			Java8Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary(
			Java8Parser.PrimaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void enterPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(
			Java8Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void exitPrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary(
			Java8Parser.PrimaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void enterTypeArgumentsOrDiamond(Java8Parser.TypeArgumentsOrDiamondContext ctx) {
	}

	@Override
	public void exitTypeArgumentsOrDiamond(Java8Parser.TypeArgumentsOrDiamondContext ctx) {
	}

	@Override
	public void enterFieldAccess(Java8Parser.FieldAccessContext ctx) {
	}

	@Override
	public void exitFieldAccess(Java8Parser.FieldAccessContext ctx) {
	}

	@Override
	public void enterFieldAccess_lf_primary(Java8Parser.FieldAccess_lf_primaryContext ctx) {
	}

	@Override
	public void exitFieldAccess_lf_primary(Java8Parser.FieldAccess_lf_primaryContext ctx) {
	}

	@Override
	public void enterFieldAccess_lfno_primary(Java8Parser.FieldAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void exitFieldAccess_lfno_primary(Java8Parser.FieldAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void enterArrayAccess(Java8Parser.ArrayAccessContext ctx) {
	}

	@Override
	public void exitArrayAccess(Java8Parser.ArrayAccessContext ctx) {
	}

	@Override
	public void enterArrayAccess_lf_primary(Java8Parser.ArrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void exitArrayAccess_lf_primary(Java8Parser.ArrayAccess_lf_primaryContext ctx) {
	}

	@Override
	public void enterArrayAccess_lfno_primary(Java8Parser.ArrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void exitArrayAccess_lfno_primary(Java8Parser.ArrayAccess_lfno_primaryContext ctx) {
	}

	@Override
	public void enterMethodInvocation_lf_primary(Java8Parser.MethodInvocation_lf_primaryContext ctx) {
		// System.out.println("Method invocation statement "+ctx.getText());

	}

	@Override
	public void exitMethodInvocation_lf_primary(Java8Parser.MethodInvocation_lf_primaryContext ctx) {
		// System.out.println("Method invocation statement "+ctx.getText());

	}

	@Override
	public void enterArgumentList(Java8Parser.ArgumentListContext ctx) {
		// System.out.println("Enter argument statement "+ctx.getText());
	}

	@Override
	public void exitArgumentList(Java8Parser.ArgumentListContext ctx) {
	}

	@Override
	public void enterMethodReference(Java8Parser.MethodReferenceContext ctx) {
	}

	@Override
	public void exitMethodReference(Java8Parser.MethodReferenceContext ctx) {
	}

	@Override
	public void enterMethodReference_lf_primary(Java8Parser.MethodReference_lf_primaryContext ctx) {
	}

	@Override
	public void exitMethodReference_lf_primary(Java8Parser.MethodReference_lf_primaryContext ctx) {
	}

	@Override
	public void enterMethodReference_lfno_primary(Java8Parser.MethodReference_lfno_primaryContext ctx) {
	}

	@Override
	public void exitMethodReference_lfno_primary(Java8Parser.MethodReference_lfno_primaryContext ctx) {
	}

	@Override
	public void enterArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) {
	}

	@Override
	public void exitArrayCreationExpression(Java8Parser.ArrayCreationExpressionContext ctx) {
	}

	@Override
	public void enterDimExprs(Java8Parser.DimExprsContext ctx) {
	}

	@Override
	public void exitDimExprs(Java8Parser.DimExprsContext ctx) {
	}

	@Override
	public void enterDimExpr(Java8Parser.DimExprContext ctx) {
		// System.out.println("Dim statement "+ctx.getText());
	}

	@Override
	public void exitDimExpr(Java8Parser.DimExprContext ctx) {
	}

	@Override
	public void enterConstantExpression(Java8Parser.ConstantExpressionContext ctx) {
	}

	@Override
	public void exitConstantExpression(Java8Parser.ConstantExpressionContext ctx) {
	}

	@Override
	public void enterLambdaParameters(Java8Parser.LambdaParametersContext ctx) {
	}

	@Override
	public void exitLambdaParameters(Java8Parser.LambdaParametersContext ctx) {
	}

	@Override
	public void enterInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx) {
	}

	@Override
	public void exitInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx) {
	}

	@Override
	public void enterLambdaBody(Java8Parser.LambdaBodyContext ctx) {
	}

	@Override
	public void exitLambdaBody(Java8Parser.LambdaBodyContext ctx) {
	}

	@Override
	public void enterConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {

	}

	@Override
	public void exitConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {
	}

	@Override
	public void enterConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) {
	}

	@Override
	public void exitConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) {
	}

	@Override
	public void enterConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) {
	}

	@Override
	public void exitConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) {
	}

	@Override
	public void enterInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx) {
	}

	@Override
	public void exitInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx) {
	}

	@Override
	public void enterExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx) {
	}

	@Override
	public void exitExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx) {
	}

	@Override
	public void enterAndExpression(Java8Parser.AndExpressionContext ctx) {
	}

	@Override
	public void exitAndExpression(Java8Parser.AndExpressionContext ctx) {
	}

	@Override
	public void enterEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
	}

	@Override
	public void exitEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
	}

	@Override
	public void exitRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
	}

	@Override
	public void enterShiftExpression(Java8Parser.ShiftExpressionContext ctx) {
	}

	@Override
	public void exitShiftExpression(Java8Parser.ShiftExpressionContext ctx) {
	}

	@Override
	public void enterAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
	}

	@Override
	public void exitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
	}

	@Override
	public void enterMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
	}

	@Override
	public void exitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
	}

	@Override
	public void enterUnaryExpression(Java8Parser.UnaryExpressionContext ctx) {
	}

	@Override
	public void exitUnaryExpression(Java8Parser.UnaryExpressionContext ctx) {
	}

	@Override
	public void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
	}

	@Override
	public void exitUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
	}

	@Override
	public void enterPostfixExpression(Java8Parser.PostfixExpressionContext ctx) {
	}

	@Override
	public void exitPostfixExpression(Java8Parser.PostfixExpressionContext ctx) {
	}

	@Override
	public void enterPostIncrementExpression_lf_postfixExpression(
			Java8Parser.PostIncrementExpression_lf_postfixExpressionContext ctx) {
	}

	@Override
	public void exitPostIncrementExpression_lf_postfixExpression(
			Java8Parser.PostIncrementExpression_lf_postfixExpressionContext ctx) {
	}

	@Override
	public void enterPostDecrementExpression_lf_postfixExpression(
			Java8Parser.PostDecrementExpression_lf_postfixExpressionContext ctx) {
	}

	@Override
	public void exitPostDecrementExpression_lf_postfixExpression(
			Java8Parser.PostDecrementExpression_lf_postfixExpressionContext ctx) {
	}

	@Override
	public void enterCastExpression(Java8Parser.CastExpressionContext ctx) {
	}

	@Override
	public void exitCastExpression(Java8Parser.CastExpressionContext ctx) {
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
	}

	@Override
	public void visitTerminal(TerminalNode node) {
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
	}

	public Set<String> getMethods() {
		return vertices.keySet();
	}

	public List<Vertex> getVertices(String method) {
		return vertices.get(method);
	}

	public List<Edge> getEdges(String method) {
		return edges.get(method);
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}
}