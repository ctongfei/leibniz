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
 * Simple and default Hessian matrix implementation.
 */
public class DefaultHessian
	implements Hessian
{

	/**
	 * The differentiable.
	 */
	private final Differentiable differentiable;
	
	/**
	 * The entry cache.
	 */
	private final Map<EntryKey, Differentiable> entryCache;
	
	/**
	 * Constructor.
	 * 
	 * @param differentiable The underlying differentiable.
	 */
	public DefaultHessian(final Differentiable differentiable)
	{
		this.differentiable = differentiable;
		entryCache = new HashMap<DefaultHessian.EntryKey, Differentiable>();
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
	public double[][] value(Assignment assignment)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.teneighty.leibniz.Hessian#value(org.teneighty.leibniz.Assignment, org.teneighty.leibniz.Variable[])
	 */
	@Override
	public double[][] value(Assignment assignment, Variable... order)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.teneighty.leibniz.Hessian#entry(org.teneighty.leibniz.Variable, org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable entry(final Variable one, final Variable two)
	{
		// TODO Auto-generated method stub
		return null;
	}	
	
	private static final class EntryKey
	{
		
		private final Variable first;
		
		private final Variable second;

		/**
		 * Constructor.
		 * 
		 * @param first
		 * @param second
		 */
		EntryKey(final Variable first, final Variable second)
		{
			this.first = first;
			this.second = second;
		}
		
		
		
	}

}
