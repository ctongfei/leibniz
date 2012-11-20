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

import org.teneighty.leibniz.compilation.Compiler;
import org.teneighty.leibniz.function.Addition;
import org.teneighty.leibniz.function.Division;
import org.teneighty.leibniz.function.Exponentiation;
import org.teneighty.leibniz.function.Multiplication;
import org.teneighty.leibniz.function.Negation;
import org.teneighty.leibniz.function.Power;
import org.teneighty.leibniz.function.Subtraction;


/**
 * Base class from which all actual differentiables can (and probably should) extend.
 */
public abstract class AbstractDifferentiable
	implements Differentiable
{
	
	/**
	 * Cache of first order derivative by variable.
	 */
	private final Map<Variable, Differentiable> derivativeCache;
		
	/**
	 * Constructor.
	 */
	protected AbstractDifferentiable()
	{
		this.derivativeCache = new HashMap<Variable, Differentiable>();
	}
	
	// partial derivative business.
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#derivative(org.teneighty.leibniz.Variable)
	 */
	@Override
	public Differentiable derivative(final Variable withRespectTo)
	{
		Differentiable derivative = derivativeCache.get(withRespectTo);
		if(derivative == null)
		{
			if(isConstant())
			{
				derivative = Constant.ZERO;
			}
			else
			{			
				derivative = derivativeCore(withRespectTo);
			}
			
			// store in cache.
			derivativeCache.put(withRespectTo, derivative);
		}
		
		return derivative;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#derivative(org.teneighty.leibniz.Variable[])
	 */
	@Override
	public Differentiable derivative(final Variable... withRespectTo)
	{
		Differentiable result = this;
		for(Variable variable : withRespectTo)
		{
			result = result.derivative(variable);
		}
		
		return result;
	}
	
	/**
	 * Take the derivative with respect to the specified variable.
	 *  
	 * @param withRespectTo The variable in question.
	 * @return Partial with respect to the specified variable.
	 */
	protected abstract Differentiable derivativeCore(Variable withRespectTo);

	// "operator" implementations.
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#negate()
	 */
	@Override
	public Differentiable negate()
	{
		return Negation.negate(this);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#plus(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable plus(Differentiable that)
	{
		return Addition.add(this, that);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#plus(double)
	 */
	@Override
	public Differentiable plus(final double that)
	{
		return plus(new Constant(that));
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#minus(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable minus(final Differentiable that)
	{
		return Subtraction.subtract(this, that);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#minus(double)
	 */
	@Override
	public Differentiable minus(final double that)
	{
		return minus(new Constant(that));
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#times(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable times(final Differentiable that)
	{
		return Multiplication.multiply(this, that);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#times(double)
	 */
	@Override
	public Differentiable times(final double that)
	{
		return times(new Constant(that));
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#over(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable over(final Differentiable that)
	{
		return dividedBy(that);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#over(double)
	 */
	@Override
	public Differentiable over(final double that)
	{
		return over(new Constant(that));
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#dividedBy(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable dividedBy(final Differentiable that)
	{
		return Division.divide(this, that);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#dividedBy(double)
	 */
	@Override
	public Differentiable dividedBy(final double that)
	{
		return dividedBy(new Constant(that));
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#squared()
	 */
	@Override
	public Differentiable squared()
	{
		return this.times(this);
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#power(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable power(final Differentiable index)
	{
		return Exponentiation.power(this, index);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#power(double)
	 */
	@Override
	public Differentiable power(final double index)
	{
		return Power.power(this, index);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#toThe(org.teneighty.leibniz.Differentiable)
	 */
	@Override
	public Differentiable toThe(final Differentiable index)
	{
		return Exponentiation.power(this, index);
	}

	/**
	 * @see org.teneighty.leibniz.Differentiable#toThe(double)
	 */
	@Override
	public Differentiable toThe(final double index)
	{
		return Power.power(this, index);
	}
		
	// simplifcation methods.
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#isZero()
	 */
	@Override
	public boolean isZero()
	{
		return false;
	}
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#isOne()
	 */
	@Override
	public boolean isOne()
	{
		return false;
	}
	
	// compilation magic.
	
	/**
	 * @see org.teneighty.leibniz.Differentiable#compile()
	 */
	@Override
	public CompiledDifferentiable compile()
	{
		return Compiler.compile(this);
	}
	
	// re-abstraction of equals and hashcode.
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public abstract boolean equals(final Object other);

}
