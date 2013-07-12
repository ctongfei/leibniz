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
		if(isSet(variable) == false)
		{
			String message = String.format("No value for %1$s has been set", variable.name());
			throw new IllegalArgumentException(message);
		}

		Double value = values.get(variable);
		return value.doubleValue();
	}
		
	/**
	 * @see org.teneighty.leibniz.Assignment#isSet(org.teneighty.leibniz.Variable)
	 */
	@Override
	public boolean isSet(final Variable variable)
		throws NullPointerException
	{
		if(variable == null)
		{
			throw new NullPointerException("variable");
		}
		
		return values.containsKey(variable);
	}

	/**
	 * Set the value of the specified variable.
	 * <p>
	 * This method is not part of the {@link Assignment} interface.
	 * 
	 * @param variable The variable.
	 * @param value The value.
	 * @throws NullPointerException If <code>variable</code> is
	 *             <code>null</code>.
	 */
	public void set(final Variable variable, final double value)
		throws NullPointerException
	{
		if(variable == null)
		{
			throw new NullPointerException("variable");
		}

		values.put(variable, value);
	}

	/**
	 * Clear the assignment for the specified variable.
	 * <p>
	 * This method is not part of the {@link Assignment} interface.
	 * 
	 * @param variable The variable for which to clear the assignment.
	 * @throws NullPointerException If <code>variable</code> is
	 *             <code>null</code>.
	 */
	public void clear(final Variable variable)
		throws NullPointerException
	{
		if(variable == null)
		{
			throw new NullPointerException("variable");
		}

		values.remove(variable);
	}

	/**
	 * Clear all variable assignments.
	 * <p>
	 * This method is not part of the {@link Assignment} interface.
	 */
	public void clear()
	{
		values.clear();
	}

}
