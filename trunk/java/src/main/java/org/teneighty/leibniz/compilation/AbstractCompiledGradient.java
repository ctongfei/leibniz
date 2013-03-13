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

import org.teneighty.leibniz.AbstractGradient;
import org.teneighty.leibniz.CompiledGradient;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Gradient;
import org.teneighty.leibniz.Variable;


/**
 * Base class for compiled gradient implementations.
 */
public abstract class AbstractCompiledGradient
	extends AbstractGradient
	implements CompiledGradient
{

	/**
	 * The uncompiled gradient.
	 */
	private final Gradient uncompiledGradient;
	
	/**
	 * The source code.
	 */
	private final String soureCode;
	
	/**
	 * Constructor.
	 * 
	 * @param uncompiledGradient Uncompiled function.
	 * @param soureCode The source code.
	 */
	public AbstractCompiledGradient(final Gradient uncompiledGradient,
			final String soureCode)
	{
		this.uncompiledGradient = uncompiledGradient;
		this.soureCode = soureCode;
	}

	/**
	 * @see org.teneighty.leibniz.Compiled#uncompiled()
	 */
	@Override
	public Gradient uncompiled()
	{
		return uncompiledGradient;
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#differentiable()
	 */
	@Override
	public Differentiable differentiable()
	{
		return uncompiled().differentiable();
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#component(org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable component(final Variable component)
	{
		return uncompiled().component(component);
	}

	/**
	 * @see org.teneighty.leibniz.Gradient#compile()
	 */
	@Override
	public CompiledGradient compile()
	{
		return this;
	}
	
	/**
	 * @see org.teneighty.leibniz.Compiled#source()
	 */
	@Override
	public String source()
	{
		return soureCode;
	}

}
