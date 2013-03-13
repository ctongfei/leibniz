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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class generates Bernoulli numbers.
 * <p>
 * This class is safe for use by multiple threads. This class cannot be
 * instantiated.
 */
public final class BernoulliNumbers
{

	/**
	 * Cache of already computed numbers.
	 */
	private static final Map<Integer, Double> cache;

	/**
	 * Class initializer.
	 */
	static
	{
		cache = new ConcurrentHashMap<Integer, Double>();
	}

	/**
	 * Get the second Bernoulli number B<sub>n</sub>
	 * 
	 * @param n n
	 * @return The second Bernoulli number.
	 */
	public static double bernoulliNumber(final int n)
	{
		Double value = cache.get(n);
		if(value == null)
		{
			value = akiyamaTanigawa(n);
			cache.put(n, value);
		}

		return value;
	}

	/**
	 * Actually compute the n<sup>th</sup> second Bernoulli numbers using the
	 * Akiyama–Tanigawa algorithm.
	 * 
	 * @param n n
	 * @return The specified Bernoulli number.
	 */
	private static double akiyamaTanigawa(final int n)
	{
		double[] a = new double[(n + 1)];
		for(int m = 0; m <= n; m++)
		{
			a[m] = 1d / (m + 1d);
			for(int j = m; m >= 1; j--)
			{
				a[(j - 1)] = j * (a[(j - 1)] - a[j]);
			}

		}

		return a[0];
	}

	/**
	 * Constructor.
	 */
	private BernoulliNumbers()
	{
	}

}
