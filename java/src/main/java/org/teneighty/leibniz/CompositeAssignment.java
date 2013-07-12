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


/**
 * A composite assignment.
 */
public class CompositeAssignment
	implements Assignment
{

	/**
	 * Underlying assignments.
	 */
	private final Assignment[] assignments;
	
	/**
	 * Constructor.
	 * 
	 * @param assignments Assignments to compose.
	 */
	public CompositeAssignment(final Assignment... assignments)
	{
		this.assignments = assignments;
	}
	
	/**
	 * @see org.teneighty.leibniz.Assignment#get(org.teneighty.leibniz.Variable)
	 */
	@Override
	public double get(final Variable variable)
		throws IllegalArgumentException, NullPointerException
	{
		for(Assignment assignment : assignments)
		{
			if(assignment.isSet(variable))
			{
				return assignment.get(variable);
			}
		}

		throw new IllegalArgumentException(variable.name());
	}
	
	/**
	 * @see org.teneighty.leibniz.Assignment#isSet(org.teneighty.leibniz.Variable)
	 */
	@Override
	public boolean isSet(final Variable variable)
		throws NullPointerException
	{
		for(Assignment assignment : assignments)
		{
			if(assignment.isSet(variable))
			{
				return true;
			}
		}
		
		return false;
	}

}
