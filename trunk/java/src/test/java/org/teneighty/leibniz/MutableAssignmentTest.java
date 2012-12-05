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

import org.junit.Test;


/**
 * Unit tests for MutableAssignment.
 */
public final class MutableAssignmentTest
{
	
	/**
	 * Variable "x".
	 */
	private final Variable x = new Variable("x");

	/**
	 * Variable "y".
	 */
	private final Variable y = new Variable("y");

	/**
	 * Check that an empty assignment throws an exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void emptyThrowsException()
	{
		MutableAssignment mutable = new MutableAssignment();
		mutable.get(x);
	}

	/**
	 * Check getting an unassigned variable fails.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void unassignedThrowsException()
	{
		MutableAssignment mutable = new MutableAssignment();
		mutable.set(y, 1);
		mutable.get(x);	
	}

	/**
	 * Simple assigned variable test.
	 */
	@Test
	public void assigned()
	{
		MutableAssignment mutable = new MutableAssignment();
		mutable.set(y, 1.0);
		
		Assert.assertEquals(1.0, mutable.get(y));
	}
	
	/**
	 * Simple assigned variable test.
	 */
	@Test
	public void assignedTwice()
	{
		MutableAssignment mutable = new MutableAssignment();
		mutable.set(y, 1.0);
		mutable.set(y, 2.0);
		
		Assert.assertEquals(2.0, mutable.get(y));
	}

}
