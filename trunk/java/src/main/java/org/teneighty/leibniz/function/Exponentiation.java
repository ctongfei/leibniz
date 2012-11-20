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
import org.teneighty.leibniz.Constant;
import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Differentiables;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.Expression;
import org.teneighty.leibniz.compilation.expression.StaticMethodCallExpression;


/**
 * General exponentiation.
 */
public final class Exponentiation
	extends AbstractComposedDifferentiable
	implements Serializable
{

	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Raise the specified base to the specified power.
	 * 
	 * @param base The base.
	 * @param index The index.
	 * @return <code>base<sup>index</sup></code>
	 */
	public static Differentiable power(final Differentiable base, final Differentiable index)
	{
		if(base.isZero() && index.isZero())
		{
			return Constant.ONE;
		}
		
		if(base.isZero())
		{
			return Constant.ZERO;
		}
		
		if(index.isZero())
		{
			return Constant.ONE;
		}
		
		if(index.isOne())
		{
			return base;
		}
		
		return new Exponentiation(base, index);
	}
	
	/**
	 * The base.
	 */
	private final Differentiable base;
	
	/**
	 * The index.
	 */
	private final Differentiable index;

	/**
	 * Constructor.
	 * 
	 * @param base The base.
	 * @param index The index.
	 */
	private Exponentiation(final Differentiable base, final Differentiable index)
	{
		super(base, index);
		
		this.base = base;
		this.index = index;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		double baseValue = base.value(assignment);
		double indexValue = index.value(assignment);
		double value = Math.pow(baseValue, indexValue);
		
		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable baseDerivative = base.derivative(withRespectTo);
		Differentiable indexDerivative = index.derivative(withRespectTo);
		
		Differentiable left = baseDerivative.times(index).over(base);
		Differentiable right = indexDerivative.times(Differentiables.ln(base));
		Differentiable derivative = this.times(left.plus(right));
		
		return derivative;
	}
		
	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(Context codeContext)
	{
		Expression baseExpression = codeContext.getExpression(base);
		Expression indexExpression = codeContext.getExpression(index);
		Expression expression = new StaticMethodCallExpression(Math.class, "pow", baseExpression, indexExpression);
		
		return expression;
	}

}
