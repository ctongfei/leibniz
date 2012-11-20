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
package org.teneighty.leibniz.function;

import java.io.Serializable;

import org.teneighty.leibniz.AbstractComposedDifferentiable;
import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.BinaryOperationExpression;
import org.teneighty.leibniz.compilation.expression.BinaryOperator;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * Subtraction.
 */
public final class Subtraction
	extends AbstractComposedDifferentiable
	implements Serializable
{
	
	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Subtract the specified argument.
	 * 
	 * @param left Left hand side.
	 * @param right Right hand side.
	 * @return <code>left - right</code>
	 */
	public static Differentiable subtract(final Differentiable left, final Differentiable right)
	{
		if(left.isZero())
		{
			return right.negate();
		}
		
		if(right.isZero())
		{
			return left;
		}
		
		return new Subtraction(left, right);
	}
	
	/**
	 * Left hand side.
	 */
	private final Differentiable left;
	
	/**
	 * Right hand side.
	 */
	private final Differentiable right;
	
	/**
	 * Constructor.
	 * 
	 * @param left Left hand side.
	 * @param right Right hand side.
	 */
	private Subtraction(final Differentiable left, final Differentiable right)
	{
		super(left, right);
		
		this.left = left;
		this.right = right;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		double leftValue = left.value(assignment);
		double rightValue = right.value(assignment);
		double value = leftValue - rightValue;
		
		return value;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable leftDeriv = left.derivative(withRespectTo);
		Differentiable rightDerive = right.derivative(withRespectTo);
		Differentiable derivative = leftDeriv.minus(rightDerive);
		
		return derivative;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		Expression leftExpression = codeContext.getExpression(left);
		Expression rightExpression = codeContext.getExpression(right);		
		Expression expression = new BinaryOperationExpression(leftExpression, BinaryOperator.MINUS, rightExpression);
		
		return expression;
	}
	
}
