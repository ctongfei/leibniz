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
package org.teneighty.leibniz.function.special;


/**
 * Some simple combinatoric utility methods.
 */
public final class Combinatorics
{

	/**
	 * Compute <code>n</code> choose <code>k</code>.
	 * 
	 * @param n n
	 * @param k k
	 * @return <code>C(n, k)</code>
	 */
	public static int combinations(final int n, final int k)
	{
		if(k == 0)
		{
			return 0;
		}
		
		if(k > n)
		{
			return 0;
		}
		
		if(k > (n - k))
		{
			// use factorial symmetry.
			return combinations(n, n - k);
		}

		// try to prevent overflow.
		int combinations = 1;
		for(int i = 1; i < (k + 1); i++)
		{
			combinations *= n - (k - i);
			combinations /= i;
		}
		
		return combinations;
	}
	
	/**
	 * Constructor.
	 */
	private Combinatorics()
	{
	}
	
}
