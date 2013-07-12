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
package org.teneighty.leibniz.compilation;

import org.teneighty.leibniz.AbstractHessian;
import org.teneighty.leibniz.CompiledHessian;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Hessian;
import org.teneighty.leibniz.HessianKey;


/**
 * Base class for compiled Hessians.
 * <p>
 * User code should <b>not</b> extend or otherwise reference this class.
 */
public abstract class AbstractCompiledHessian
	extends AbstractHessian
	implements CompiledHessian
{

	/**
	 * Uncompiled Hessian.
	 */
	private final Hessian uncompiledHessian;
	
	/**
	 * Source code.
	 */
	private final String sourceCode;

	/**
	 * Constructor.
	 * 
	 * @param uncompiledHessian The uncompiled Hessian.
	 * @param sourceCode The source code.
	 */
	protected AbstractCompiledHessian(final Hessian uncompiledHessian, final String sourceCode)
	{
		this.uncompiledHessian = uncompiledHessian;
		this.sourceCode = sourceCode;
	}

	/**
	 * @see org.teneighty.leibniz.Hessian#differentiable()
	 */
	@Override
	public Differentiable differentiable()
	{
		return uncompiledHessian.differentiable();
	}
	
	/**
	 * @see org.teneighty.leibniz.Hessian#component(org.teneighty.leibniz.HessianKey)
	 */
	@Override
	public Differentiable component(final HessianKey key)
	{
		return uncompiledHessian.component(key);
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractHessian#compile()
	 */
	@Override
	public CompiledHessian compile()
	{
		return this;
	}

	/**
	 * @see org.teneighty.leibniz.Compiled#uncompiled()
	 */
	@Override
	public Hessian uncompiled()
	{
		return uncompiledHessian;
	}

	/**
	 * @see org.teneighty.leibniz.Compiled#source()
	 */
	@Override
	public String source()
	{
		return sourceCode;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return uncompiled().hashCode();
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
		
		if(other instanceof CompiledHessian)
		{
			CompiledHessian that = (CompiledHessian)other;
			return that.uncompiled().equals(uncompiled());
		}
		
		return false;
	}

}
