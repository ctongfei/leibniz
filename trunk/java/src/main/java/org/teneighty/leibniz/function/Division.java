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
 * Division.
 */
public final class Division
	extends AbstractComposedDifferentiable
{
	
	/**
	 * Divide the specified differentiables.
	 * 
	 * @param numerator The numerator.
	 * @param denominator The denominator.
	 * @return <code>numerator / denominator</code>
	 */
	public static Differentiable divide(final Differentiable numerator, final Differentiable denominator)
	{
		if(numerator.isZero())
		{
			return Constant.ZERO;
		}
		
		if(denominator.isOne())
		{
			return numerator;
		}
		
		return new Division(numerator, denominator);
		
	}
	
	/**
	 * The numerator.
	 */
	private final Differentiable numerator;
	
	/**
	 * The denominator.
	 */
	private final Differentiable denominator;

	/**
	 * Constructor.
	 * 
	 * @param numerator The numerator.
	 * @param denominator The denominator.
	 */
	private Division(final Differentiable numerator, final Differentiable denominator)
	{
		super(numerator, denominator);
		
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		double numeratorValue = numerator.value(assignment);
		double denominatorValue = denominator.value(assignment);
		double value = numeratorValue / denominatorValue;
		
		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable numeratorDerivative = numerator.derivative(withRespectTo);
		Differentiable denominatorDerivative = denominator.derivative(withRespectTo);
		
		Differentiable derivativeNumerator = (numeratorDerivative.times(denominator)).minus(denominator.times(denominatorDerivative));
		Differentiable derivativeDenominator = denominator.squared();		
		Differentiable derivative = derivativeNumerator.dividedBy(derivativeDenominator);
		
		return derivative;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		Expression numeratorExpression = codeContext.getExpression(numerator);
		Expression denominatorExpression = codeContext.getExpression(denominator);
		Expression expression = new BinaryOperationExpression(numeratorExpression, BinaryOperator.DIVIDE, denominatorExpression);
		
		return expression;
	}
	
}
