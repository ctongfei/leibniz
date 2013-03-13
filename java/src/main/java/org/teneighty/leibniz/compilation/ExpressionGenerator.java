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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * Generates an expression list for a differentiable.
 */
final class ExpressionGenerator
	implements Context
{

	/**
	 * Map of differentiables to their code expressions.
	 */
	private final Map<Differentiable, ReferenceExpression> differentiableExpressions;
		
	/**
	 * List of expressions.
	 */
	private List<ReferenceExpression> expressions;
	
	/**
	 * Variable id generator.
	 */
	private int variableIdGenerator;
	
	/**
	 * Constructor.
	 */
	ExpressionGenerator()
	{
		differentiableExpressions = new HashMap<Differentiable, ReferenceExpression>();
		
		variableIdGenerator = 0;
	}
	
	/**
	 * Generate expressions for the specified differentiable.
	 * 
	 * @param differentiable The differentiable.
	 * @return The expression list.
	 */
	List<ReferenceExpression> generate(final Differentiable differentiable)
	{
		expressions = new ArrayList<ReferenceExpression>();
		getExpression(differentiable);
		return expressions;
	}
		
	/**
	 * Get the next variable name.
	 * 
	 * @return The next variable name.
	 */
	private String getNextVariableName()
	{
		variableIdGenerator += 1;
		return String.format("var_%1$s", variableIdGenerator);
	}
		
	/**
	 * @see org.teneighty.leibniz.Context#getExpression(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Expression getExpression(final Differentiable differentiable)
	{
		ReferenceExpression reference = differentiableExpressions.get(differentiable);
		if(reference != null)
		{
			reference.addReference();
			return reference;
		}
		
		// get the native expression.
		Expression nativeExpression = differentiable.expression(this);
		String localName = getNextVariableName();
		
		// create and cache new reference expression.
		reference = new ReferenceExpression(localName, nativeExpression);		
		differentiableExpressions.put(differentiable, reference);				
		expressions.add(reference);
		
		return reference;
	}
	
}
