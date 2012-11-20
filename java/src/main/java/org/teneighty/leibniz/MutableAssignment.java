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

import java.util.HashMap;
import java.util.Map;


/**
 * Simple, mutable assignment.
 */
public final class MutableAssignment
	implements Assignment
{
	
	/**
	 * The values.
	 */
	private final Map<Variable, Double> values;
	
	/**
	 * Constructor.
	 */
	public MutableAssignment()
	{
		values = new HashMap<Variable, Double>();
	}
	
	/**
	 * @see org.teneighty.leibniz.Assignment#get(org.teneighty.leibniz.Variable)
	 */
	@Override
	public double get(final Variable variable)
		throws IllegalArgumentException
	{
		Double value = values.get(variable);
		if(value == null)
		{
			String message = String.format("No value for %1$s has been set", variable.name());
			throw new IllegalArgumentException(message);
		}
		
		return value.doubleValue();
	}
	
	/**
	 * Set the value of the specified variable.
	 * 
	 * @param variable The variable.
	 * @param value The value.
	 */
	public void set(final Variable variable, final double value)
	{
		values.put(variable, value);
	}

}
