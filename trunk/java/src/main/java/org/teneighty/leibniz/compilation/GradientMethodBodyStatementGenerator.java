/*
 * $Id$
 * 
 * Copyright (c) 2012-2013 Fran Lattanzio
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.teneighty.leibniz.compilation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.statement.LocalDoubleDeclarationStatement;
import org.teneighty.leibniz.compilation.statement.SnippetStatement;
import org.teneighty.leibniz.compilation.statement.Statement;


/**
 * Generates code for gradient method body.
 */
final class GradientMethodBodyStatementGenerator
{

	/**
	 * The statements.
	 */
	private final List<Statement> statements;

	/**
	 * Constructor.
	 * 
	 * @param differentiableExpressions Expressions for the gradient component
	 *            differentiables.
	 */
	GradientMethodBodyStatementGenerator(final LinkedHashMap<Variable, List<ReferenceExpression>> differentiableExpressions)
	{
		statements = new ArrayList<Statement>();
		generateStatements(differentiableExpressions);
	}

	/**
	 * Get the statements.
	 * 
	 * @return The statements.
	 */
	List<Statement> getStatements()
	{
		return statements;
	}

	/**
	 * 
	 * @param differentiableExpressions
	 */
	private void generateStatements(final LinkedHashMap<Variable, List<ReferenceExpression>> differentiableExpressions)
	{
		// hacky.
		statements.add(new SnippetStatement("MutableGradientValue value = new MutableGradientValue();"));
		
		for(Map.Entry<Variable, List<ReferenceExpression>> entry : differentiableExpressions.entrySet())
		{
			Variable variable = entry.getKey();
			List<ReferenceExpression> expressions = entry.getValue();
			
			for(int index = 0; index < expressions.size(); index++)
			{
				ReferenceExpression reference = expressions.get(index);

				if(reference.getReferenceCount() > 1)
				{
					// always add local, even if last expression.
					LocalDoubleDeclarationStatement lds = new LocalDoubleDeclarationStatement(reference.getVariableName(), reference.referent());
					statements.add(lds);
				}
				
				if(index == (expressions.size() - 1))
				{
					// hack into the result map.
					String putSnippet = String.format("value.set(new Variable(\"%1$s\"), %2$s);", variable.name(), reference.code());
					statements.add(new SnippetStatement(putSnippet));
				}
			}
		}
		
		// final hack of this class.
		statements.add(new SnippetStatement("return value;"));
	}

}
