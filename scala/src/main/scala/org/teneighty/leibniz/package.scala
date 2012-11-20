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
package org.teneighty

import org.teneighty.leibniz.Variable;
import org.teneighty.leibniz.Constant;

/**
 * Provides some implicit conversion methods.
 */
package object leibniz {

  /**
   * Convert a string to a variable.
   *
   * @param name The variable name.
   * @return A new variable with the specified name.
   */
  implicit def stringConverion(name: String): Variable = new Variable(name);

  /**
   * Convert a double to constant.
   *
   * @param value The value.
   * @return A new constant with the specified value.
   */
  implicit def doubleConversion(value: Double): Constant = new Constant(value);
  
  /**
   * Leibniz differentiable enriched with operator overloads and other 
   * Scala-centric features.
   */
  implicit def EnrichedDifferentiable(differentiable: Differentiable) = new {

    /**
     * Compute the value of this differentiable given the specified
     * variable assignment.
     *
     * @param assignment The variable value assignment.
     * @return The value.
     */
    def value(assignment: Map[Variable, Double]): Double = {
      val mutable: MutableAssignment = new MutableAssignment();
      assignment.foreach(entry => mutable.set(entry._1, entry._2));
      return differentiable.value(mutable);
    }
    
    /**
     * Negate this differentiable.
     *
     * @return <code>-this</code>
     */
    def -(): Differentiable = differentiable.negate();

    /**
     * Add the specified differentiable.
     *
     * @param that The differentiable to add.
     */
    def +(that: Differentiable): Differentiable = differentiable.plus(that);

    /**
     * Add the specified differentiable.
     *
     * @param that The differentiable to subtract
     */
    def -(that: Differentiable): Differentiable = differentiable.minus(that);

    /**
     * Multiply by the specified differentiable.
     *
     * @param that The differentiable by which to multiply.
     */
    def *(that: Differentiable): Differentiable = differentiable.times(that);

    /**
     * Multiply by the specified differentiable.
     *
     * @param that The differentiable by which to divide.
     */
    def /(that: Differentiable): Differentiable = differentiable.over(that);

    /**
     * Multiply by the specified differentiable.
     *
     * @param that The differentiable by which to divide.
     */
    def ^(that: Differentiable): Differentiable = differentiable.toThe(that);

  }

}