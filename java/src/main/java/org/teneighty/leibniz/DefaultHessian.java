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
 * A simple, default Hessian implementation.
 */
public class DefaultHessian
	extends AbstractHessian
{

	/**
	 * The underlying differentiable.
	 */
	private final Differentiable differentiable;
	
	/**
	 * All hessian components.
	 */
	private final Map<HessianKey, Differentiable> components;

	/**
	 * Constructor.
	 * 
	 * @param differentiable The underlying differentiable.
	 */
	public DefaultHessian(final Differentiable differentiable)
	{
		this.differentiable = differentiable;
		
		components = new HashMap<HessianKey, Differentiable>();
		
		for(HessianKey key : keys())
		{
			// could be done more efficiently if we generated intermediate
			// derivatives... but this is a one-time operation and this code is
			// more elegant.
			Differentiable component = differentiable.derivative(key.first(), key.second());
			components.put(key, component);
		}
	}

	/**
	 * @see org.teneighty.leibniz.Hessian#differentiable()
	 */
	@Override
	public Differentiable differentiable()
	{
		return differentiable;
	}

	
	/**
	 * @see org.teneighty.leibniz.Hessian#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public HessianValue value(final Assignment assignment)
	{
		// pretty easy... just evaluate each component.
		MutableHessianValue hessianValue = new MutableHessianValue();
		for(Map.Entry<HessianKey, Differentiable> entry : components.entrySet())
		{
			HessianKey key = entry.getKey();
			Differentiable component = entry.getValue();
			double value = component.value(assignment);
			
			hessianValue.set(key, value);
		}
				
		return hessianValue;
	}

	/**
	 * @see org.teneighty.leibniz.Hessian#component(org.teneighty.leibniz.HessianKey)
	 */
	@Override
	public Differentiable component(final HessianKey key)
	{
		Differentiable component = components.get(key);
		if(component == null)
		{
			String message = String.format("No component for %1$s", key);
			throw new IllegalArgumentException(message);
		}
		
		return component;
	}
	
}
