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


/**
 * Hessian key.
 */
public final class HessianKey
{

	/**
	 * First variable.
	 */
	private final Variable first;

	/**
	 * Second variable.
	 */
	private final Variable second;

	/**
	 * Constructor.
	 * 
	 * @param first First variable.
	 * @param second Second variable.
	 */
	public HessianKey(final Variable first, final Variable second)
	{
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Get the first variable.
	 * 
	 * @return The first variable.
	 */
	public Variable first()
	{
		return first;
	}

	/**
	 * Get the second variable.
	 * 
	 * @return The second variable.
	 */
	public Variable second()
	{
		return second;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return first.hashCode() ^ second.hashCode();
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
		
		if(other instanceof HessianKey)
		{
			HessianKey that = (HessianKey)other;
			
			boolean forward = first.equals(that.first) && second.equals(that.second);
			boolean backward = first.equals(that.second) && second.equals(that.first);
			
			return forward || backward;			
		}

		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if(first.compareTo(second) < 0)
		{
			return String.format("HessianKey[%1$s, %2$s]", first, second);
		}
		
		return String.format("HessianKey[%1$s, %2$s]", second, first);
	}
	
}
