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
 * Simple, mutable result of evaluating a Hessian.
 */
public class MutableHessianValue
	extends AbstractHessianValue
{

	/**
	 * The values.
	 */
	private final Map<HessianKey, Double> values;
	
	/**
	 * Constructor.
	 */
	public MutableHessianValue()
	{
		values = new HashMap<HessianKey, Double>();
	}
	
	/**
	 * @see org.teneighty.leibniz.HessianValue#value(org.teneighty.leibniz.HessianKey)
	 */
	@Override
	public double value(final HessianKey key)
	{
		Double value = values.get(key);
		if(value == null)
		{
			String message = String.format("No value for %1$s", key);
			throw new IllegalArgumentException(message);
		}
		
		return value.doubleValue();
	}
		
	/**
	 * Set the value for the specified variables.
	 * 
	 * @param first The first variable.
	 * @param second The second variable.
	 * @param value The value.
	 */
	public void set(final Variable first, final Variable second, final double value)
	{
		HessianKey key = new HessianKey(first, second);
		set(key, value);
	}
		
	/**
	 * Set the value for the specified key.
	 * 
	 * @param key The key.
	 * @param value The value.
	 */
	public void set(final HessianKey key, final double value)
	{
		values.put(key, value);		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MutableHessianValue [values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}
	
}
