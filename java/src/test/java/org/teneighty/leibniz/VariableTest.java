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

import org.junit.Assert;
import org.junit.Test;

/**
 * Variable tests.
 */
public final class VariableTest
{
	
	private Variable x = new Variable("x");
	private Variable y = new Variable("y");
	
	@Test
	public void derivativeWithRespectToSelf()
	{
		Differentiable dx = x.derivative(x);		
		Assert.assertTrue(dx.isOne());		
	}
	
	@Test
	public void derivativeWithRespectToOther()
	{
		Differentiable dy = x.derivative(y);		
		Assert.assertTrue(dy.isZero());		
	}
	
	@Test
	public void equality()
	{		
		Variable x2 = new Variable("x");
		Assert.assertTrue(x.equals(x));
		Assert.assertTrue(x.equals(x2));
		Assert.assertTrue(x2.equals(x));
		
		Assert.assertFalse(y.equals(x));
		Assert.assertFalse(y.equals(x2));
		Assert.assertFalse(x2.equals(y));				
	}
	
}
