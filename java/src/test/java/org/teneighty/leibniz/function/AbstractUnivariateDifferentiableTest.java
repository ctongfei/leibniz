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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.MutableAssignment;
import org.teneighty.leibniz.NumericalDerivatives;
import org.teneighty.leibniz.Variable;

/**
 * Base class for automated tests for univariate differentiable functions.
 */
public abstract class AbstractUnivariateDifferentiableTest
{

	/**
	 * Double tolerance.
	 */
	private static final double TOLERANCE = 1e-8;

	/**
	 * Main variable.
	 */
	private Variable x;

	/**
	 * x<sup>2</sup>.
	 */
	private Differentiable x2;

	/**
	 * f(x)
	 */
	private Differentiable funcX;

	/**
	 * f(x<sup>2</sup>)
	 */
	private Differentiable funcX2;

	/**
	 * Apply the function to test to the specified differentiable.
	 * 
	 * @param argument The argument.
	 * @return The function applied to the specified argument.
	 */
	protected abstract Differentiable apply(Differentiable argument);

	/**
	 * Get the lower bound.
	 * 
	 * @return The lower bound.
	 */
	protected double getLowerBound()
	{
		return 1;
	}

	/**
	 * Get the upper bound.
	 * 
	 * @return The upper bound.
	 */
	protected double getUpperBound()
	{
		return 25;
	}

	/**
	 * Get the step count.
	 * 
	 * @return Step count.
	 */
	protected int getStepCount()
	{
		return 1000;
	}

	/**
	 * Get the step size.
	 * 
	 * @return The step size.
	 */
	private double getStepSize()
	{
		double upper = getUpperBound();
		double lower = getLowerBound();
		int count = getStepCount();
		double stepSize = (upper - lower) / count;

		return stepSize;
	}

	/**
	 * Test initializer.
	 */
	@Before
	public void testInitialize()
	{
		x = new Variable("x");
		x2 = x.times(x);

		funcX = apply(x);
		funcX2 = apply(x2);
	}

	/**
	 * Conduct a simple numerical derivatives test.
	 */
	@Test
	public void numerical()
	{
		MutableAssignment assignment = new MutableAssignment();

		double value = getLowerBound();
		for(int index = 0; (index < getStepCount()) && (value < getUpperBound()); index++, value += getStepSize())
		{
			assignment.set(x, value);
			NumericalDerivatives.test(funcX, x, assignment);
			NumericalDerivatives.test(funcX2, x, assignment);
		}
	}

	/**
	 * Simple tests to make sure that functions are properly implementing the
	 * chain rule.
	 */
	@Test
	public void chainRule()
	{
		Differentiable dxFuncX = funcX.derivative(x);
		Differentiable dxFuncX2 = funcX2.derivative(x);

		MutableAssignment assignment = new MutableAssignment();
		double value = getLowerBound();
		for(int index = 0; (index < getStepCount()) && (value < getUpperBound()); index++, value += getStepSize())
		{
			if(value == 0)
			{
				continue;
			}

			double squared = value * value;
			assignment.set(x, squared);

			double dxFuncXValue = dxFuncX.value(assignment);
			assignment.set(x, value);

			double dxFuncX2Value = dxFuncX2.value(assignment);
			if(Double.isInfinite(dxFuncX2Value))
			{
				break;
			}

			double chainRule = dxFuncX2Value / dxFuncXValue;
			double one = 2d * value / chainRule;

			Assert.assertEquals(1d, one, TOLERANCE);
		}
	}

}
