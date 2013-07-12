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
package org.teneighty.leibniz;

import junit.framework.Assert;


/**
 * Some static utility methods for doing numerical derivative tests.
 */
public final class NumericalDerivatives
{
	
	/**
	 * The five point stencil coefficients.
	 */
	private static final double[] fivePointStencil = new double[]{ 1d/12d, -8d/12d, 0, 8d/12d, -1d/12d};
	
	/**
	 * Grid width scale factor (root of machine epsilon).
	 */
	private static final double WIDTH_SCALE = Math.sqrt(Math.pow(2, -52));
	
	/**
	 * Default sauce for double comparison.
	 */
	private static final double DEFAULT_DELTA = 1e-6;

	/**
	 * Performs a numerical derivative test.
	 * 
	 * @param differentiable The differentiable to test.
	 * @param withRespectTo The variable.
	 * @param assignment The assignment.
	 */
	public static void test(final Differentiable differentiable, final Variable withRespectTo, final Assignment assignment)
	{
		test(differentiable, withRespectTo, assignment, DEFAULT_DELTA);
	}
	
	/**
	 * Performs a numerical derivative test.
	 * 
	 * @param differentiable The differentiable to test.
	 * @param withRespectTo The variable.
	 * @param assignment The assignment.
	 * @param delta The delta.
	 */
	public static void test(final Differentiable differentiable, final Variable withRespectTo, final Assignment assignment, final double delta)
	{
		double exact = differentiable.derivative(withRespectTo).value(assignment);
		double numerical = getFivePointStencilDerivative(differentiable, withRespectTo, assignment);
		
		if((Math.abs(exact) < DEFAULT_DELTA) && (Math.abs(numerical) < DEFAULT_DELTA))
		{
			// both are close enough to zero for government work.
			return;
		}
		
		double unit = exact / numerical;
		Assert.assertEquals(1d, unit, delta);		
	}
	
	/**
	 * Compute the numerical partial derivative of the specified function with respect to the specified variable using the given variable assignment.
	 * 
	 * @param differentiable The differentiable in question.
	 * @param withRespectTo The variable with respect to which to take the partial derivative. 
	 * @param assignment The variable assignment.
	 * @return Five point stencil numerical derivative.
	 */
	public static double getFivePointStencilDerivative(final Differentiable differentiable, final Variable withRespectTo, final Assignment assignment)
	{
		NumericalDerivatives derivative = new NumericalDerivatives(differentiable, withRespectTo, assignment, fivePointStencil);
		derivative.createInputGrid();
		derivative.createOutputGrid();		
		double value = derivative.derivative();
		
		return value;
	}
		
	/**
	 * The differentiable.
	 */
	private final Differentiable differentiable;
	
	/**
	 * The variable with respect to which we're differentiating.
	 */
	private final Variable withRespectTo;
	
	/**
	 * The base assignment.
	 */
	private final Assignment assignment;
	
	/**
	 * Stencil coefficients.
	 */
	private final double[] derivativeCoefficients;
	
	/**
	 * The stencil size.
	 */
	private final int stencilSize;
	
	/**
	 * The central value of the input grid.
	 */
	private final double centralValue;
	
	/**
	 * The grid step size.
	 */
	private final double gridSize;
	
	/**
	 * Grid of function inputs.
	 */
	private double[] inputGrid;
	
	/**
	 * Grid of function outputs.
	 */
	private double[] outputGrid;
	
	/**
	 * Constructor.
	 * 
	 * @param differentiable The differentiable.
	 * @param withRespectTo The variable with repsect to which we're taking the derivative.
	 * @param assignment The base assignment.
	 * @param derivativeCoefficients Derivative stencil coefficients.
	 */
	private NumericalDerivatives(final Differentiable differentiable,
			final Variable withRespectTo, 
			final Assignment assignment,
			final double[] derivativeCoefficients)
	{
		this.differentiable = differentiable;
		this.withRespectTo = withRespectTo;
		this.assignment = assignment;
		this.derivativeCoefficients = derivativeCoefficients;
		
		this.stencilSize = derivativeCoefficients.length;		
		this.centralValue = assignment.get(withRespectTo);
		this.gridSize = WIDTH_SCALE + (Math.abs(centralValue) * WIDTH_SCALE);
	}

	/**
	 * Create the input grid.
	 */
	private void createInputGrid()
	{
		inputGrid = new double[stencilSize];
		
		int bound = (stencilSize - 1) / 2;
		for(int index = -bound; index <= bound; index++)
		{
			inputGrid[(index + bound)] = centralValue + (index * gridSize);
		}		
	}
	
	/**
	 * Create the output grid.
	 */
	private void createOutputGrid()
	{
		MutableAssignment mutable = new MutableAssignment();
		CompositeAssignment composite = new CompositeAssignment(mutable, assignment);
		
		outputGrid = new double[stencilSize];
		
		for(int index = 0; index < stencilSize; index++)
		{
			mutable.set(withRespectTo, inputGrid[index]);
			outputGrid[index] = differentiable.value(composite);
		}		
	}
	
	/**
	 * Compute derivative via inner product of output grid and stencil coefficients.
	 * 
	 * @return The derivative.
	 */
	private double derivative()
	{
		double value = 0;
		
		for(int index = 0; index < stencilSize; index++)
		{
			value += (outputGrid[index] * derivativeCoefficients[index]);
		}
		
		return value / gridSize;
	}
	
}
