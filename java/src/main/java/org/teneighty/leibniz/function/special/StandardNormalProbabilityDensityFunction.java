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
package org.teneighty.leibniz.function.special;

import java.io.Serializable;

import org.teneighty.leibniz.AbstractComposedDifferentiable;
import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.Expression;
import org.teneighty.leibniz.compilation.expression.StaticMethodCallExpression;


/**
 * The standard normal PDF.
 */
public final class StandardNormalProbabilityDensityFunction
	extends AbstractComposedDifferentiable
	implements Serializable
{

	/**
	 * A useful constant.
	 */
	private static final double SQRT_TWO_PI = Math.sqrt(2d * Math.PI);
	
	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Take the standard normal PDF of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return The standard normal PDF of the specified argument.
	 */
	public static Differentiable pdf(final Differentiable argument)
	{
		return new StandardNormalProbabilityDensityFunction(argument);
	}

	/**
	 * Take the standard normal PDF of the specified value.
	 * 
	 * @param x The value.
	 * @return The standard normal PDF of <code>x</code>
	 */
	public static double pdf(final double x)
	{
		double value = Math.exp(-x * x / 2d) / SQRT_TWO_PI;
		
		return value;		
	}

	/**
	 * The argument.
	 */
	private final Differentiable argument;

	/**
	 * Constructor.
	 * 
	 * @param argument The argument.
	 */
	private StandardNormalProbabilityDensityFunction(final Differentiable argument)
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
		double value = pdf(argumentValue);

		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable argumentDerivative = argument.derivative(withRespectTo);
		Differentiable derivative = this.times(argument.negate()).times(argumentDerivative);

		return derivative;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		Expression argumentExpression = codeContext.getExpression(argument);
		Expression expression = new StaticMethodCallExpression(StandardNormalProbabilityDensityFunction.class, "pdf", argumentExpression);

		return expression;
	}

}
