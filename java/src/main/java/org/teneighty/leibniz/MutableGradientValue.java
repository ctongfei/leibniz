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
 * Mutable gradient value implementation.
 */
public class MutableGradientValue
	implements GradientValue
{

	/**
	 * Map of values.
	 */
	private final Map<Variable, Double> values;
	
	/**
	 * Constructor.
	 */
	public MutableGradientValue()
	{
		values = new HashMap<Variable, Double>();
	}
	
	/**
	 * @see org.teneighty.leibniz.GradientValue#value(org.teneighty.leibniz.Variable)
	 */
	@Override
	public double value(Variable component)
	{
		Double value = values.get(component);
		if(value == null)
		{
			throw new IllegalArgumentException("component");
		}
		
		return value;
	}
	
	/**
	 * Set the value for the specified component.
	 *  
	 * @param component The component.
	 * @param value The value.
	 */
	public void set(final Variable component, final double value)
	{
		if(component == null)
		{
			throw new NullPointerException("component");
		}
		
		values.put(component, value);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MutableGradientValue [values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}
		
}
