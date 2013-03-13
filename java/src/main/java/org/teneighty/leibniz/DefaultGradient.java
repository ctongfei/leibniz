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
 * Simple gradient implementation.
 */
public class DefaultGradient
	extends AbstractGradient
{

	/**
	 * The parent differentiable.
	 */
	private final Differentiable differentiable;
	
	/**
	 * The gradient components.
	 */
	private final Map<Variable, Differentiable> gradientComponents;
	
	/**
	 * Constructor.
	 * 
	 * @param differentiable The differentiable.
	 */
	public DefaultGradient(final Differentiable differentiable)
	{
		this.differentiable = differentiable;				
		
		// store gradient components.
		gradientComponents = new HashMap<Variable, Differentiable>();
		for(Variable component : differentiable.variables())
		{
			gradientComponents.put(component, differentiable.derivative(component));
		}
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public GradientValue value(final Assignment assignment)
	{
		MutableGradientValue value = new MutableGradientValue();
		for(Map.Entry<Variable, Differentiable> entry : gradientComponents.entrySet())
		{
			value.set(entry.getKey(), entry.getValue().value(assignment));
		}
		
		return value;
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#component(org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable component(final Variable component)
	{
		Differentiable differentiable = gradientComponents.get(component);
		
		if(differentiable == null)
		{
			throw new IllegalArgumentException("component");
		}
		
		return differentiable;
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#differentiable()
	 */
	@Override
	public Differentiable differentiable()
	{
		return differentiable;
	}
	
}
