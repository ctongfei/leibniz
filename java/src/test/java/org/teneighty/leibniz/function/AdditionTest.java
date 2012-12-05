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

import org.junit.Test;
import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.NumericalDerivatives;
import org.teneighty.leibniz.TestUtilities;
import org.teneighty.leibniz.Variable;


/**
 * Tests for addition.
 */
public final class AdditionTest
{
	
	/**
	 * x variable.
	 */
	private Variable x = new Variable("x");
	
	/**
	 * y variables.
	 */
	private Variable y = new Variable("y");
	
	private Assignment xTen = TestUtilities.constant(10, x);
	
	private Differentiable xPlusX = x.plus(x);
	
	private Differentiable xPlusY = x.plus(y);
	
	@Test
	public void value1()
	{
		Assert.assertEquals(20.0, xPlusX.value(xTen));
	}
	
	@Test
	public void derivative1()
	{
		Differentiable derivative = xPlusX.derivative(x);

		Assert.assertEquals(2.0, derivative.value(xTen));
		Assert.assertTrue(derivative.isConstant());
	}
	
	@Test
	public void derivative2()
	{
		Differentiable derivative = xPlusY.derivative(x);
		Assignment assignment = TestUtilities.constant(10, x, y);

		Assert.assertEquals(1.0, derivative.value(assignment));
		Assert.assertTrue(derivative.isConstant());
	}
	
	@Test
	public void derivative3()
	{
		Differentiable derivative = xPlusX.derivative(y);
		Assignment assignment = TestUtilities.constant(10, x);

		Assert.assertEquals(0.0, derivative.value(assignment));
		Assert.assertTrue(derivative.isZero());
	}
	
	/**
	 * Simple derivative test.
	 */
	@Test
	public void derivative4()
	{
		Differentiable add = x.plus(y);
		Differentiable dx = add.derivative(x);
		Differentiable dy = add.derivative(y);
		Assignment assignment = TestUtilities.constant(10, x);

		Assert.assertEquals(1.0, dx.value(assignment));
		Assert.assertEquals(1.0, dy.value(assignment));
	}
	
	/**
	 * Numerical derivative test.
	 */
	@Test
	public void numerical1()
	{
		Differentiable add = x.plus(x);

		Assignment assignment = TestUtilities.constant(10, x);
		NumericalDerivatives.test(add, x, assignment);
	}
	
	/**
	 * Numerical derivative test.
	 */
	@Test
	public void numerical2()
	{
		Differentiable add = x.plus(x);

		Assignment assignment = TestUtilities.constant(10, x);
		NumericalDerivatives.test(add, x, assignment);
	}


}
