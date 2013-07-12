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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.teneighty.leibniz.HessianKey;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.statement.LocalDoubleDeclarationStatement;
import org.teneighty.leibniz.compilation.statement.SnippetStatement;
import org.teneighty.leibniz.compilation.statement.Statement;


/**
 * Generates code for gradient method body.
 */
final class HessianMethodBodyStatementGenerator
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
	HessianMethodBodyStatementGenerator(final LinkedHashMap<HessianKey, List<ReferenceExpression>> differentiableExpressions)
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
	 * Generate statements for the specified expression map.
	 * 
	 * @param differentiableExpressions The expressions.
	 */
	private void generateStatements(final LinkedHashMap<HessianKey, List<ReferenceExpression>> differentiableExpressions)
	{
		// hacky.
		statements.add(new SnippetStatement("MutableHessianValue value = new MutableHessianValue();"));
		
		// expressions for which we've already added a local variable.
		Set<ReferenceExpression> added = new HashSet<ReferenceExpression>();
		
		for(Map.Entry<HessianKey, List<ReferenceExpression>> entry : differentiableExpressions.entrySet())
		{
			HessianKey key = entry.getKey();
			List<ReferenceExpression> expressions = entry.getValue();
			
			for(int index = 0; index < expressions.size(); index++)
			{
				ReferenceExpression reference = expressions.get(index);

				if((reference.getReferenceCount() > 1) && (added.contains(reference) == false))
				{
					// always add a local in this case...
					LocalDoubleDeclarationStatement lds = new LocalDoubleDeclarationStatement(reference.getVariableName(), reference.referent());
					statements.add(lds);
					added.add(reference);
				}
				
				if(index == (expressions.size() - 1))
				{
					Variable first = key.first();
					Variable second = key.second();
					
					// hack into the result map.
					String putSnippet = String.format("value.set(new Variable(\"%1$s\"), new Variable(\"%2$s\"), %3$s);", first.name(), second.name(), reference.code());
					statements.add(new SnippetStatement(putSnippet));
				}
			}
		}
		
		// final hack of this class.
		statements.add(new SnippetStatement("return value;"));
	}

}
