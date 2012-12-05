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
import org.teneighty.leibniz.TestUtilities;
import org.teneighty.leibniz.Variable;


/**
 * Unit tests for multiplication.
 */
public final class MultiplicationTest
{
	
	private Variable x = new Variable("x");

	private Variable y = new Variable("y");
	
	private Differentiable x2 = x.times(x);
	
	@Test
	public void value1()
	{
		Assignment assignment = TestUtilities.constant(5, x);		
		Assert.assertEquals(25, x2.value(assignment));
	}
	
	@Test
	public void derivative1()
	{
		Differentiable derivative = x2.derivative(x);
		Assignment assignment = TestUtilities.constant(5, x);

		Assert.assertEquals(10.0, derivative.value(assignment));
	}

	@Test
	public void derivative2()
	{
		Differentiable times = x2;
		Differentiable derivative = times.derivative(x, x);
		Assignment assignment = TestUtilities.constant(5, x);

		Assert.assertEquals(2.0, derivative.value(assignment));
		Assert.assertTrue(derivative.isConstant());
	}
	
	@Test
	public void numerical1()
	{
		
		
	}


}
