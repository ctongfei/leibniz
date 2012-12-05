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
package org.teneighty.leibniz.samples


import org.teneighty.leibniz._
import org.teneighty.leibniz.Differentiable
import org.teneighty.leibniz.Variable
import org.teneighty.leibniz.samples.BlackScholes.OptionType


/**
 * Black-Scholes example, written in Scala.
 */
class BlackScholesScala {
  
  /**
   * Underlying price.
   */
  private val s : Variable = "s";
  
  /**
   * Strike price.
   */
  private val k : Variable = "k";
  
  /**
   * Risk free rate.
   */
  private val r : Variable = "r";
  
  /**
   * Time to expiry.
   */
  private val t : Variable = "t";
  
  /**
   * Sigma/volatility.
   */
  private val sigma : Variable = "sigma";
    
  /**
   * Call price function.
   */
  private val call : Differentiable = callPrice();
  
  def price(optionType : OptionType, underlying : Double, strike : Double, riskFreeRate : Double, timeToExpiry : Double, volatility : Double)
  {
    def assignment : Map[Variable, Double] = assign(underlying, strike, riskFreeRate, timeToExpiry, volatility);
  }
  
  private def assign(underlying : Double, strike : Double, riskFreeRate : Double, timeToExpiry : Double, volatility : Double) : Map[Variable, Double] = 
  {
   def assignment : Map[Variable, Double] = Map[Variable, Double](s -> underlying, k -> strike, r -> riskFreeRate, t -> timeToExpiry, sigma -> volatility);
   return assignment;
  }
  
  /**
   * Get the call price function.
   * 
   * @return Black-Scholes call price function.
   */
  private def callPrice() : Differentiable = 
  {
    return (s * normCdf(d1)) + (k^(-r*t)*normCdf(d2)); 
  }
  
  /**
   * Get the <code>d1</code> function for Black-Scholes.
   * 
   * @return <code>d1</code>
   */
  private def d1() : Differentiable = 
  {
    return (ln(s / k) + ((r + (sigma^2)) * t))/(sigma * sqrt(t))
  }
  
  /**
   * Get the <code>d2</code> function for Black-Scholes.
   * 
   * @return <code>d2</code>
   */
  private def d2() : Differentiable = 
  {
    return d1 - (sigma * sqrt(t));
  }

}