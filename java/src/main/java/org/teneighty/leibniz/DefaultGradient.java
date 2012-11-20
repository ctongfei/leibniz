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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * A simple and default gradient implementation.
 */
public class DefaultGradient
	implements Gradient
{

	/**
	 * The underlying function.
	 */
	private final Differentiable underlying;
	
	/**
	 * Coordinates.
	 */
	private final Map<Variable, Differentiable> coordinates;
	
	/**
	 * The default order.
	 */
	private transient volatile Variable[] defaultOrder;
	
	/**
	 * Constructor.
	 * 
	 * @param differentiable The differentiable.
	 */
	public DefaultGradient(final Differentiable differentiable)
	{			
		this.underlying = differentiable;
		this.coordinates = new HashMap<Variable, Differentiable>();
		
		// create all first-order derivatives.
		Set<Variable> variables = underlying.getVariables();
		for(Variable variable : variables)
		{
			coordinates.put(variable, differentiable.derivative(variable));
		}
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double[] value(final Assignment assignment)
	{
		if(defaultOrder == null)
		{
			Set<Variable> variables = underlying.getVariables();
			defaultOrder = variables.toArray(new Variable[variables.size()]);
			Arrays.sort(defaultOrder);
		}
		
		return value(assignment, defaultOrder);
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#value(org.teneighty.leibniz.Assignment, org.teneighty.leibniz.Variable[])
	 */
	@Override
	public double[] value(final Assignment assignment, final Variable... order)
	{
		double[] values = new double[order.length];
		
		for(int index = 0; index < order.length; index++)
		{
			values[index] = coordinate(order[index]).value(assignment);
		}
		
		return values;
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#differentiable()
	 */
	@Override
	public Differentiable differentiable()
	{
		return underlying;
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#coordinate(org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable coordinate(final Variable variable)
	{
		Differentiable partial = coordinates.get(variable);
		if(partial == null)
		{
			return Constant.ZERO;
		}
				
		return partial;
	}

}
