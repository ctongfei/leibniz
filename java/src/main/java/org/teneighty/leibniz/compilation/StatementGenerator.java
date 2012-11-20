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
import java.util.List;

import org.teneighty.leibniz.compilation.statement.LocalDoubleDeclarationStatement;
import org.teneighty.leibniz.compilation.statement.ReturnStatement;
import org.teneighty.leibniz.compilation.statement.Statement;

/**
 * Generates code statements given an expression list.
 */
final class StatementGenerator
{

	/**
	 * The statements.
	 */
	private final List<Statement> statements;

	/**
	 * Constructor.
	 * 
	 * @param expressions The expressions.
	 */
	StatementGenerator(final List<ReferenceExpression> expressions)
	{
		statements = new ArrayList<Statement>();
		generateStatements(expressions);
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
	 * Generate the list of statements.
	 * 
	 * @param expressions The expression list.
	 */
	private void generateStatements(final List<ReferenceExpression> expressions)
	{
		for(int index = 0; index < expressions.size(); index++)
		{
			ReferenceExpression reference = expressions.get(index);

			if(index == (expressions.size() - 1))
			{
				// this is the last expression. add the return statement.
				statements.add(new ReturnStatement(reference));
			}
			else
			{
				// otherwise, add a local if the expression is referenced more
				// than once. Expressions with only a single reference will be 
				// inlined
				
				if(reference.getReferenceCount() > 1)
				{
					LocalDoubleDeclarationStatement lds = new LocalDoubleDeclarationStatement(reference.getVariableName(), reference.referent());
					statements.add(lds);
				}
			}
		}
	}

}
