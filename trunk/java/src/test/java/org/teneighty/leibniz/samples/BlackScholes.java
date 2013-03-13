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
package org.teneighty.leibniz.samples;

import static org.teneighty.leibniz.Differentiables.exp;
import static org.teneighty.leibniz.Differentiables.ln;
import static org.teneighty.leibniz.Differentiables.normCdf;
import static org.teneighty.leibniz.Differentiables.sqrt;

import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.MutableAssignment;
import org.teneighty.leibniz.Variable;

/**
 * An example of the Leibniz automatic differentiation package: Computing the
 * partials (a.k.a. the Greeks or sensitivities) of the Black Scholes formula.
 * <p>
 * Usually, when implementing a Black-Scholes formula repository, you have to
 * manually write out methods for all of the derivatives that you want; this
 * could even involve taking some partial derivatives. With Leibniz, you need
 * only to define the option value function - we then use automatic
 * differentiation to compute partials of arbitrary order (to the machine
 * precision) with very little code.
 */
public final class BlackScholes
{

	/**
	 * The option types.
	 */
	public static enum OptionType
	{

		/**
		 * Call.
		 */
		CALL,

		/**
		 * Put.
		 */
		PUT;

	}

	/**
	 * Main method.
	 * 
	 * @param args Ignored.
	 */
	public static void main(final String[] args)
	{
		double underlying = 30;
		double strike = 25;
		double riskFreeRate = 5d / 100d;
		double timeToExpiry = 1;
		double volatility = 20d / 100d;

		BlackScholes blackScholes = new BlackScholes();
		double value = blackScholes.value(OptionType.CALL, underlying, strike, riskFreeRate, timeToExpiry, volatility);
		double delta = blackScholes.delta(OptionType.CALL, underlying, strike, riskFreeRate, timeToExpiry, volatility);
		double gamma = blackScholes.gamma(OptionType.CALL, underlying, strike, riskFreeRate, timeToExpiry, volatility);

		System.out.println(value);
		System.out.println(delta);
		System.out.println(gamma);
		
		System.out.println(blackScholes.callValue.derivative(blackScholes.s, blackScholes.s).compile().source());
		
	}

	/**
	 * Stock price.
	 */
	private final Variable s = new Variable("s");

	/**
	 * Strike.
	 */
	private final Variable k = new Variable("k");

	/**
	 * Risk-free rate.
	 */
	private final Variable r = new Variable("r");

	/**
	 * Time to expiry.
	 */
	private final Variable t = new Variable("t");

	/**
	 * Volatility.
	 */
	private final Variable sigma = new Variable("sigma");

	/**
	 * Call value formula.
	 */
	private final Differentiable callValue;

	/**
	 * Put value formual.
	 */
	private final Differentiable putValue;

	/**
	 * Constructor.
	 */
	public BlackScholes()
	{
		callValue = callFunction();
		putValue = putFunction();
	}

	/**
	 * Get the option value formula for the specified type.
	 * 
	 * @param type The option type.
	 * @return Value formula.
	 */
	private Differentiable getValueFormula(final OptionType type)
	{
		switch(type)
		{
			case CALL:
				return callValue;
			case PUT:
				return putValue;
			default:
				throw new IllegalArgumentException("type");
		}

	}

	/**
	 * Get the option value.
	 * 
	 * @param type The option type.
	 * @param underlying The underlying price.
	 * @param strike The strike price.
	 * @param riskFreeRate Risk free interest rate.
	 * @param timeToExpiry Time to expiry in years.
	 * @param volatility The volatility.
	 * @return The option value.
	 */
	public double value(final OptionType type, final double underlying,
			final double strike, final double riskFreeRate,
			final double timeToExpiry, final double volatility)
	{
		Differentiable valueFormula = getValueFormula(type);
		double value = evaluate(valueFormula, underlying, strike, riskFreeRate, timeToExpiry, volatility);

		return value;
	}

	/**
	 * Get the delta.
	 * 
	 * @param type The option type.
	 * @param underlying The underlying price.
	 * @param strike The strike price.
	 * @param riskFreeRate Risk free interest rate.
	 * @param timeToExpiry Time to expiry in years.
	 * @param volatility The volatility.
	 * @return The delta.
	 */
	public double delta(final OptionType type, final double underlying,
			final double strike, final double riskFreeRate,
			final double timeToExpiry, final double volatility)
	{
		Differentiable deltaFormula = getValueFormula(type).derivative(s);
		double delta = evaluate(deltaFormula, underlying, strike, riskFreeRate, timeToExpiry, volatility);

		return delta;
	}

	/**
	 * Get the gamma.
	 * 
	 * @param type The option type.
	 * @param underlying The underlying price.
	 * @param strike The strike price.
	 * @param riskFreeRate Risk free interest rate.
	 * @param timeToExpiry Time to expiry in years.
	 * @param volatility The volatility.
	 * @return The gamma.
	 */
	public double gamma(final OptionType type, final double underlying,
			final double strike, final double riskFreeRate,
			final double timeToExpiry, final double volatility)
	{
		Differentiable gammaFormula = getValueFormula(type).derivative(s, s);
		double gamma = evaluate(gammaFormula, underlying, strike, riskFreeRate, timeToExpiry, volatility);

		return gamma;
	}

	/**
	 * Evaluate the specified option pricing function given the specified
	 * inputs.
	 * 
	 * @param function The function to evaluate.
	 * @param underlying The underlying price.
	 * @param strike The strike price.
	 * @param riskFreeRate Risk free interest rate.
	 * @param timeToExpiry Time to expiry in years.
	 * @param volatility The volatility.
	 * @return The value of the function at the specified point.
	 */
	private double evaluate(final Differentiable function,
			final double underlying, final double strike,
			final double riskFreeRate, final double timeToExpiry,
			final double volatility)
	{
		Assignment assignment = assignment(underlying, strike, riskFreeRate, timeToExpiry, volatility);
		double value = function.value(assignment);

		return value;
	}

	/**
	 * Convert the variables to a Leibniz assignment.
	 * 
	 * @param underlying The underlying price.
	 * @param strike The strike price.
	 * @param riskFreeRate The risk free rate.
	 * @param timeToExpiry The time to expiry.
	 * @param volatility The volatility.
	 * @return An assignment.
	 */
	private Assignment assignment(final double underlying, final double strike,
			final double riskFreeRate, final double timeToExpiry,
			final double volatility)
	{
		MutableAssignment assignment = new MutableAssignment();
		assignment.set(s, underlying);
		assignment.set(k, strike);
		assignment.set(r, riskFreeRate);
		assignment.set(t, timeToExpiry);
		assignment.set(sigma, volatility);

		return assignment;
	}

	/**
	 * Get the Black-Scholes call value formula.
	 * 
	 * @return Black-Scholes call value formula.
	 */
	private Differentiable callFunction()
	{
		Differentiable d1 = d1();
		Differentiable d2 = d2();

		Differentiable ns = normCdf(d1).times(s);
		Differentiable kert = k.times(exp(r.times(t).negate()));
		Differentiable nk = normCdf(d2).times(kert);
		Differentiable value = ns.minus(nk);

		return value;
	}

	/**
	 * Get the Black-Scholes put value formula.
	 * 
	 * @return Black-Scholes put value function.
	 */
	private Differentiable putFunction()
	{
		Differentiable d1 = d1().negate();
		Differentiable d2 = d2().negate();

		Differentiable kert = k.times(exp(r.times(t).negate()));
		Differentiable nk = normCdf(d2).times(kert);
		Differentiable ns = normCdf(d1).times(s);
		Differentiable value = nk.minus(ns);

		return value;
	}

	/**
	 * Get the d1 function for Black-Scholes.
	 * 
	 * @return d1 function.
	 */
	private Differentiable d1()
	{
		Differentiable log = ln(s.over(k));
		Differentiable rsigt = (r.plus(sigma.squared().over(2))).times(t);
		Differentiable d1 = (log.plus(rsigt)).over(sigma.times(sqrt(t)));

		return d1;
	}

	/**
	 * Get the d2 function for Black-Scholes.
	 * 
	 * @return d2 function.
	 */
	private Differentiable d2()
	{
		Differentiable d1 = d1();
		Differentiable d2 = d1.minus(sigma.times(sqrt(t)));

		return d2;
	}

}
