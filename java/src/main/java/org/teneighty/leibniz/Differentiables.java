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

import org.teneighty.leibniz.function.Exponential;
import org.teneighty.leibniz.function.Exponentiation;
import org.teneighty.leibniz.function.NaturalLogarithm;
import org.teneighty.leibniz.function.Power;
import org.teneighty.leibniz.function.hyperbolic.ArcHyperbolicCosine;
import org.teneighty.leibniz.function.hyperbolic.ArcHyperbolicSine;
import org.teneighty.leibniz.function.hyperbolic.ArcHyperbolicTangent;
import org.teneighty.leibniz.function.hyperbolic.HyperbolicCosine;
import org.teneighty.leibniz.function.hyperbolic.HyperbolicSine;
import org.teneighty.leibniz.function.hyperbolic.HyperbolicTangent;
import org.teneighty.leibniz.function.special.StandardNormalCumulativeDensityFunction;
import org.teneighty.leibniz.function.special.StandardNormalProbabilityDensityFunction;
import org.teneighty.leibniz.function.trigonometric.ArcCosine;
import org.teneighty.leibniz.function.trigonometric.ArcSine;
import org.teneighty.leibniz.function.trigonometric.ArcTangent;
import org.teneighty.leibniz.function.trigonometric.Cosine;
import org.teneighty.leibniz.function.trigonometric.Sine;
import org.teneighty.leibniz.function.trigonometric.Tangent;

/**
 * The doppleganger of {@link java.lang.Math} for differentiables.
 * <p>
 * This class is stateless (and hence safe for use by multiple threads) and
 * cannot be instantiated.
 */
public final class Differentiables
{

	/**
	 * <i>e</i>
	 */
	public static final Constant E = new Constant(Math.E);

	/**
	 * &pi;
	 */
	public static final Constant PI = new Constant(Math.PI);

	/**
	 * Take the exponential of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code><i>e</i><sup>argument</sup></code>
	 */
	public static Differentiable exp(final Differentiable argument)
	{
		return Exponential.exp(argument);
	}

	/**
	 * Take the natural log the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>ln(argument)</code>
	 */
	public static Differentiable ln(final Differentiable argument)
	{
		return NaturalLogarithm.ln(argument);
	}

	/**
	 * Take the base 10 log of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>log10(argument)</code>
	 */
	public static Differentiable log10(final Differentiable argument)
	{
		Constant log10 = new Constant(Math.log(10));
		return ln(argument).over(log10);
	}

	/**
	 * Raise the specified base to the specified index.
	 * 
	 * @param base The base.
	 * @param index The index.
	 * @return <code>base<sup>index</sup></code>
	 */
	public static Differentiable pow(final Differentiable base,
			final Differentiable index)
	{
		return Exponentiation.power(base, index);
	}

	/**
	 * Raise the specified base to the specified index.
	 * 
	 * @param base The base.
	 * @param index The index.
	 * @return <code>base<sup>index</sup></code>
	 */
	public static Differentiable pow(final Differentiable base,
			final double index)
	{
		return Power.power(base, index);
	}

	/**
	 * Take the square root of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>sqrt(argument)</code>
	 */
	public static Differentiable sqrt(final Differentiable argument)
	{
		return pow(argument, 1d / 2d);
	}

	/**
	 * Take the cube root of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>argument<sup>1/3</sup></code>
	 */
	public static Differentiable cbrt(final Differentiable argument)
	{
		return pow(argument, 1d / 3d);
	}

	/**
	 * Take the sin of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>sin(argument)</code>
	 */
	public static Differentiable sin(final Differentiable argument)
	{
		return Sine.sin(argument);
	}

	/**
	 * Take the cosine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>cos(argument)</code>
	 */
	public static Differentiable cos(final Differentiable argument)
	{
		return Cosine.cos(argument);
	}

	/**
	 * Take the tangent of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>tan(argument)</code>
	 */
	public static Differentiable tan(final Differentiable argument)
	{
		return Tangent.tan(argument);
	}

	/**
	 * Take the arcsine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>asin(argument)</code>
	 */
	public static Differentiable asin(final Differentiable argument)
	{
		return ArcSine.arcsin(argument);
	}

	/**
	 * Take the arccosine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>acos(argument)</code>
	 */
	public static Differentiable acos(final Differentiable argument)
	{
		return ArcCosine.arccos(argument);
	}

	/**
	 * Take the arctangent of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>atan(argument)</code>
	 */
	public static Differentiable atan(final Differentiable argument)
	{
		return ArcTangent.arctan(argument);
	}

	/**
	 * Compute angle in radians between the positive x-axis of a plane and the
	 * point given by the coordinates <code>(x, y)</code> on it.
	 * <p>
	 * The angle is positive for counter-clockwise angles (upper half-plane, y &gt;
	 * 0), and negative for clockwise angles (lower half-plane, y &lt; 0).
	 * 
	 * @param y <code>y</code>
	 * @param x <code>x</code>
	 * @return The aformentioned angle.
	 */
	public static Differentiable atan2(final Differentiable y,
			final Differentiable x)
	{
		Differentiable argument = (sqrt((y.squared()).plus(x.squared())).minus(x)).over(y);
		Differentiable atan2 = atan(argument).times(2);

		return atan2;
	}

	/**
	 * Take the hyperbolic sine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>sinh(argument)</code>
	 */
	public static Differentiable sinh(final Differentiable argument)
	{
		return HyperbolicSine.sinh(argument);
	}

	/**
	 * Take the hyperbolic cosine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>cosh(argument)</code>
	 */
	public static Differentiable cosh(final Differentiable argument)
	{
		return HyperbolicCosine.cosh(argument);
	}

	/**
	 * Take the hyperbolic tangent of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>tanh(argument)</code>
	 */
	public static Differentiable tanh(final Differentiable argument)
	{
		return HyperbolicTangent.tanh(argument);
	}

	/**
	 * Take the arc hyperbolic sin of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>asinh(argument)</code>
	 */
	public static Differentiable asinh(final Differentiable argument)
	{
		return ArcHyperbolicSine.arcsinh(argument);
	}

	/**
	 * Take the arc hyperbolic cosine of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>acosh(argument)</code>
	 */
	public static Differentiable acosh(final Differentiable argument)
	{
		return ArcHyperbolicCosine.arccosh(argument);
	}

	/**
	 * Take the arc hyperbolic tangent of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return <code>atanh(argument)</code>
	 */
	public static Differentiable atanh(final Differentiable argument)
	{
		return ArcHyperbolicTangent.arctanh(argument);
	}

	/**
	 * Return the hypotenuse of the triangle with side <code>x</code> and
	 * <code>y</code>.
	 * 
	 * @param x x.
	 * @param y y.
	 * @return The hypotenuse of the aforementioned nature.
	 */
	public static Differentiable hypot(final Differentiable x,
			final Differentiable y)
	{
		Differentiable x2 = x.squared();
		Differentiable y2 = x.squared();

		return sqrt(x2.plus(y2));
	}

	/**
	 * Take the normal cumulative density function of the specified arugment.
	 * 
	 * @param argument The argument.
	 * @return The normal CDF.
	 */
	public static Differentiable normCdf(final Differentiable argument)
	{
		return StandardNormalCumulativeDensityFunction.cdf(argument);
	}

	/**
	 * Take the normal PDF of the specified argument.
	 * 
	 * @param argument The argument.
	 * @return The normal PDF.
	 */
	public static Differentiable normPdf(final Differentiable argument)
	{
		return StandardNormalProbabilityDensityFunction.pdf(argument);
	}

	/**
	 * Constructor.
	 * <p>
	 * Here only for access protection.
	 */
	private Differentiables()
	{
	}

}
