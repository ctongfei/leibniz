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

import org.teneighty.leibniz.compilation.expression.AbstractExpression;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * A variable reference expression.
 */
final class ReferenceExpression
	extends AbstractExpression
{
	
	/**
	 * The variable name.
	 */
	private final String variableName;
	
	/**
	 * The referenced expression.
	 */
	private final Expression referent;
	
	/**
	 * Number of times we've reference this expression.
	 */
	private int referenceCount;
				
	/**
	 * Constructor.
	 * 
	 * @param variableName The variable name.
	 * @param referent The referenced expression.
	 */
	ReferenceExpression(final String variableName, final Expression referent)
	{
		this.variableName = variableName;
		this.referent = referent;
		referenceCount = 1;
	}
	
	/**
	 * Add a reference.
	 */
	void addReference()
	{
		referenceCount += 1;
	}
	
	/**
	 * Get the reference count.
	 * 
	 * @return The reference count.
	 */
	int getReferenceCount()
	{
		return referenceCount;
	}
	
	/**
	 * Get the variable name.
	 * 
	 * @return The variable name.
	 */
	String getVariableName()
	{
		return variableName;
	}

	/**
	 * Get the referent.
	 * 
	 * @return The referenced expression.
	 */
	Expression referent()
	{
		return referent;
	}
		
	/**
	 * @see org.teneighty.leibniz.compilation.expression.Expression#code()
	 */
	@Override
	public String code()
	{
		return referenceCount > 1 ? variableName : referent.code();
	}

}
