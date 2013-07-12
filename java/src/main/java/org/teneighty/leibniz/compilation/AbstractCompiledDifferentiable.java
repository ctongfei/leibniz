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

import java.util.Set;

import org.teneighty.leibniz.AbstractDifferentiable;
import org.teneighty.leibniz.CompiledDifferentiable;
import org.teneighty.leibniz.Context;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * Base class from which all compiled differentiables shall extend.
 * <p>
 * User code should <b>not</b> extend or otherwise reference this class.
 */
public abstract class AbstractCompiledDifferentiable
	extends AbstractDifferentiable
	implements CompiledDifferentiable
{
	
	/**
	 * The uncompiled function.
	 */
	private final Differentiable uncompiledFunction;
	
	/**
	 * The source code.
	 */
	private final String soureCode;
	
	/**
	 * Constructor.
	 * 
	 * @param differentiable The function from which this compiled function was generated.
	 * @param sourceCode The source code.
	 */
	protected AbstractCompiledDifferentiable(final Differentiable differentiable, final String sourceCode)
	{
		this.uncompiledFunction = differentiable;
		this.soureCode = sourceCode;
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#derivativeCore(org.teneighty.leibniz.Variable)
	 */
	@Override
	protected Differentiable derivativeCore(final Variable withRespectTo)
	{
		return uncompiled().derivative(withRespectTo);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#variables()
	 */
	@Override
	public Set<Variable> variables()
	{
		return uncompiled().variables();
	}
	
	// simpler to let uncompiled function handle the simplification methods.

	/**
	 * @see org.teneighty.leibniz.Differentiable#isConstant()
	 */
	@Override
	public boolean isConstant()
	{
		return uncompiled().isConstant();
	}

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#isZero()
	 */
	@Override
	public boolean isZero()
	{
		return uncompiled().isZero();
	}
	
	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#isOne()
	 */
	@Override
	public boolean isOne()
	{
		return uncompiled().isOne();
	}
	
	// and a few hacks for the compilation framework.

	/**
	 * @see org.teneighty.leibniz.AbstractDifferentiable#compile()
	 */
	@Override
	public CompiledDifferentiable compile()
	{
		// already compiled...
		return this;
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#expression(org.teneighty.leibniz.Context)
	 */
	@Override
	public Expression expression(final Context codeContext)
	{
		return uncompiled().expression(codeContext);
	}

	/**
	 * @see org.teneighty.leibniz.CompiledDifferentiable#uncompiled()
	 */
	@Override
	public Differentiable uncompiled()
	{
		return uncompiledFunction;
	}
	
	/**
	 * @see org.teneighty.leibniz.CompiledDifferentiable#source()
	 */
	@Override
	public String source()
	{
		return soureCode;
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
		
		if(other instanceof CompiledDifferentiable)
		{
			CompiledDifferentiable that = (CompiledDifferentiable)other;
			return that.uncompiled().equals(uncompiled());
		}
		
		return false;
	}		
	
}
