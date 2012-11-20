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

import org.teneighty.leibniz.AbstractComposedDifferentiable;
import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Constant;
import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.BinaryOperationExpression;
import org.teneighty.leibniz.compilation.expression.BinaryOperator;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * Multiplication.
 */
public final class Multiplication
	extends AbstractComposedDifferentiable
{

	/**
	 * Multiply the specified differentiables.
	 * 
	 * @param left Left hand side.
	 * @param right Right hand side.
	 * @return <code>left * right</code>
	 */
	public static Differentiable multiply(final Differentiable left, final Differentiable right)
	{
		if(left.isZero() || right.isZero())
		{
			return Constant.ZERO;
		}
		
		if(left.isOne())
		{
			return right;
		}
		
		if(right.isOne())
		{
			return left;
		}
		
		return new Multiplication(left, right);
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
	 * @param left Left expression.
	 * @param right Right expression.
	 */
	private Multiplication(final Differentiable left, final Differentiable right)
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
		double value = leftValue * rightValue;
		
		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable leftDerivative = left.derivative(withRespectTo);
		Differentiable rightDerivative = right.derivative(withRespectTo);		
		Differentiable derivative = (leftDerivative.times(right).plus(left.times(rightDerivative)));
		
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
		Expression expression = new BinaryOperationExpression(leftExpression, BinaryOperator.TIMES, rightExpression);
		
		return expression;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return getClass().hashCode() * left.hashCode() * right.hashCode();
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other)
	{
		if(other == null)
		{
			return false;
		}
		
		if(other == this)
		{
			return true;
		}
		
		if(other instanceof Multiplication)
		{
			Multiplication that = (Multiplication)other;
			return (left.equals(that.left) && right.equals(that.right)) ||
					(left.equals(that.right) && right.equals(that.left)); 
		}
		
		return false;
	}	
	
}
