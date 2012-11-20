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
package org.teneighty.leibniz.compilation.expression;


/**
 * 
 */
public class BinaryOperationExpression
	extends AbstractExpression
{
	
	/**
	 * The left expression.
	 */
	private final Expression left;
	
	/**
	 * The operator.
	 */
	private final BinaryOperator operator;
	
	/**
	 * The right expression.
	 */
	private final Expression right;
	
	/**
	 * Constructor.
	 * 
	 * @param left
	 * @param operator
	 * @param right
	 */
	public BinaryOperationExpression(final Expression left, final BinaryOperator operator,
			final Expression right)
	{
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	/**
	 * @see org.teneighty.leibniz.compilation.expression.Expression#code()
	 */
	@Override
	public String code()
	{
		return String.format("(%1$s %2$s %3$s)", left.code(), operator.code(), right.code());
	}

}
