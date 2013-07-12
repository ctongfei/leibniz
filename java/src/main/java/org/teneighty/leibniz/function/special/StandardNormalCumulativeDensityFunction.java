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
import org.teneighty.leibniz.Differentiables;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.Expression;
import org.teneighty.leibniz.compilation.expression.StaticMethodCallExpression;


/**
 * Normal cumulative density function.
 */
public final class StandardNormalCumulativeDensityFunction
	extends AbstractComposedDifferentiable
	implements Serializable
{

	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Take the normal cumulative density function of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return The normal CDF of the specified argument.
	 */
	public static Differentiable cdf(final Differentiable argument)
	{
		return new StandardNormalCumulativeDensityFunction(argument);
	}

	/**
	 * Compute the normal CDF of the specified value.
	 * 
	 * @param x The value.
	 * @return The normal CDF of x.
	 */
	public static double cdf(final double x)
	{
		if(x == 0)
		{
			return .5d;
		}
		
		if(x < 0)
		{
			// A & G works only for x > 1, so use symmetry of 
			// normal CDF.
			return (1d - cdf(-x));
		}
		
		final double b0 = 0.2316419;
		final double b1 = 0.319381530;
		final double b2 = -0.356563782;
		final double b3 = 1.781477937;
		final double b4 = -1.821255978;
		final double b5 = 1.330274429;

		double t = 1d / (1d + (b0 * x));
		double t2 = t * t;
		double t3 = t2 * t;
		double t4 = t3 * t;
		double t5 = t4 * t;

		double pdf = StandardNormalProbabilityDensityFunction.pdf(x);
		double value = 1d - (pdf * (t * b1 + t2 * b2 + t3 * b3 + t4 * b4 + t5 * b5));

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
	private StandardNormalCumulativeDensityFunction(
			final Differentiable argument)
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
		double value = cdf(argumentValue);

		return value;
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		Differentiable argumentDerivative = argument.derivative(withRespectTo);
		Differentiable pdf = Differentiables.normPdf(argument);
		Differentiable derivative = argumentDerivative.times(pdf);

		return derivative;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		Expression argumentExpression = codeContext.getExpression(argument);
		Expression expression = new StaticMethodCallExpression(StandardNormalCumulativeDensityFunction.class, "cdf", argumentExpression);

		return expression;
	}

}
