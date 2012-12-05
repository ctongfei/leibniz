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


/**
 * A differentiable that takes other differentiables as arguments.
 */
public abstract class AbstractComposedDifferentiable
	extends AbstractDifferentiable
{
	
	/**
	 * The arguments.
	 */
	private final Differentiable[] arguments;
	
	/**
	 * Constructor.
	 * 
	 * @param arguments The arguments.
	 */
	protected AbstractComposedDifferentiable(final Differentiable... arguments)
	{
		this.arguments = arguments;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#getVariables()
	 */
	@Override
	public Set<Variable> getVariables()
	{
		Set<Variable> variables = new HashSet<Variable>();
		for(Differentiable argument : arguments)
		{
			variables.addAll(argument.getVariables());
		}
		
		return variables;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#isConstant()
	 */
	@Override
	public boolean isConstant()
	{
		for(Differentiable argument : arguments)
		{
			if(argument.isConstant() == false)
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int hashCode = getClass().hashCode();
		for(Differentiable argument : arguments)
		{
			hashCode ^= argument.hashCode();
		}
		
		return hashCode;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#equals(java.lang.Object)
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
				
		if(other.getClass().equals(getClass()))
		{
			AbstractComposedDifferentiable that = (AbstractComposedDifferentiable)other;
			
			for(int index = 0; index < arguments.length; index++)
			{
				if(that.arguments[index].equals(arguments[index]) == false)
				{
					return false;
				}				
			}
			
			return true;
		}
		
		return false;
	}

}
