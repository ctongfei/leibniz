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

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.teneighty.leibniz.compilation.expression.Expression;
import org.teneighty.leibniz.compilation.expression.VariableValueFromAssignmentExpression;


/**
 * A variable or free parameter.
 */
public final class Variable
	extends AbstractDifferentiable
	implements Comparable<Variable>, Serializable
{

	/**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of this variable.
	 */
	private final String name;

	/**
	 * @param name
	 */
	public Variable(final String name)
	{
		this.name = name;
	}
	
	/**
	 * Get the name of this variable.
	 * 
	 * @return The variable name.
	 */
	public String name()
	{
		return name;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		return assignment.get(this);
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		return equals(withRespectTo) ? Constant.ONE : Constant.ZERO;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#getVariables()
	 */
	@Override
	public Set<Variable> getVariables()
	{
		return Collections.singleton(this);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		return new VariableValueFromAssignmentExpression(name);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#isConstant()
	 */
	@Override
	public boolean isConstant()
	{
		return false;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		
		if(other instanceof Variable)
		{
			Variable that = (Variable)other;
			return name.equals(that.name);
		}
		
		return false;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Variable other)
	{
		if(other == null)
		{
			throw new NullPointerException("other");
		}
		
		if(other == this)
		{
			return 0;
		}
		
		return name.compareTo(other.name);
	}	

}
