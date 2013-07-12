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

import java.util.HashSet;
import java.util.Set;

import org.teneighty.leibniz.compilation.Compiler;


/**
 * Base class for Hessian implementations.
 */
public abstract class AbstractHessian
	implements Hessian
{

	/**
	 * @see org.teneighty.leibniz.Hessian#variables()
	 */
	@Override
	public Set<Variable> variables()
	{
		return differentiable().variables();
	}
	
	/**
	 * @see org.teneighty.leibniz.Hessian#keys()
	 */
	@Override
	public Set<HessianKey> keys()
	{
		Set<Variable> outerSet = new HashSet<Variable>(variables());
		Set<Variable> innerSet = new HashSet<Variable>(variables());
		
		Set<HessianKey> keys = new HashSet<HessianKey>();
		for(Variable outer : outerSet)
		{
			for(Variable inner : innerSet)
			{				
				keys.add(new HessianKey(outer, inner));
			}
			
			innerSet.remove(outer);
		}
		
		return keys;
	}
	
	/**
	 * @see org.teneighty.leibniz.Hessian#component(org.teneighty.leibniz.Variable, org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable component(final Variable first, final Variable second)
	{
		HessianKey key = new HessianKey(first, second);
		Differentiable component = component(key);
		
		return component;
	}
	
	/**
	 * @see org.teneighty.leibniz.Hessian#compile()
	 */
	@Override
	public CompiledHessian compile()
	{
		return Compiler.compile(this);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other)
	{
		if(other == null)
		{
			return false;
		}
		
		if(other == this)			
		{
			return true;
		}
		
		if(other instanceof Hessian)
		{
			Hessian that = (Hessian)other;
			return differentiable().equals(that.differentiable());
		}
		
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return differentiable().hashCode();
	}

}
