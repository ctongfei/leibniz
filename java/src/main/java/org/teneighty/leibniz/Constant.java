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

import org.teneighty.leibniz.compilation.expression.ConstantDoubleExpression;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * A constant.
 */
public final class Constant
	extends AbstractDifferentiable
	implements Serializable
{
	
	/**
	 * Zero.
	 */
	public static final Constant ZERO = new Constant(0);
	
	/**
	 * One.
	 */
	public static final Constant ONE = new Constant(1);
		
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The value.
	 */
	private final double value;

	/**
	 * Constructor.
	 * 
	 * @param value The value.
	 */
	public Constant(final double value)
	{
		this.value = value;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#value(org.teneighty.leibniz.Assignment)
	 */
	@Override
	public double value(final Assignment assignment)
	{
		return value;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		return Constant.ZERO;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#variables()
	 */
	@Override
	public Set<Variable> variables()
	{
		return Collections.emptySet();
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		return new ConstantDoubleExpression(value);
	}
	
	// proper overrides.
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#isZero()
	 */
	@Override
	public boolean isZero()
	{
		return (value == 0);
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#isOne()
	 */
	@Override
	public boolean isOne()
	{
		return (value == 1);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#isConstant()
	 */
	@Override
	public boolean isConstant()
	{
		return true;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int)(getClass().hashCode() * value);
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
		
		if(other instanceof Constant)
		{
			Constant that = (Constant)other;
			return (value == that.value);
		}
		
		return false;
	}

}
