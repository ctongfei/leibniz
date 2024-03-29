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
 * Exponential function.
 */
public final class Exponential
	extends AbstractComposedDifferentiable
	implements Serializable
{
	
	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Raise the specified differentiable to <i>e</i>.
	 * 
	 * @param argument The argument.
	 * @return <code><i>e</i><sup>argument</sup></code>
	 */
	public static Differentiable exp(final Differentiable argument)
	{
		if(argument.isZero())
		{
			return Constant.ONE;
		}
		
		if(argument.isOne())
		{
			return Differentiables.E;
		}
		
		return new Exponential(argument);
	}
	
	/**
	 * The argument.
	 */
	private final Differentiable argument;

	/**
	 * @param argument
	 */
	private Exponential(final Differentiable argument)
	{
		super(argument);
		
		this.argument = argument;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		double argumentValue = argument.value(assignment);
		double value = Math.exp(argumentValue);
		
		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable argumentDerivative = argument.derivative(withRespectTo);
		Differentiable derivative = this.times(argumentDerivative);
		
		return derivative;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		Expression argumentExpression = codeContext.getExpression(argument);		
		Expression expression = new StaticMethodCallExpression(Math.class, "exp", argumentExpression);
		
		return expression;
	}
		
}
