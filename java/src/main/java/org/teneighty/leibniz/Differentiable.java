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

import java.util.Set;

import org.teneighty.leibniz.compilation.expression.Expression;


/**
 * A differentiable function with a 
 * <p>
 * This is the core interface of the Leibniz package.
 */
public interface Differentiable
{
	
	// core differentiable interface.
	
	/**
	 * Get the value of this differentiable given the specified variable assignment.
	 * 
	 * @param assignment The variable assignment.
	 * @return The value.
	 */
	public double value(Assignment assignment);
	
	/**
	 * Take the partial derivative with respect to specified variable.
	 * 
	 * @param withRespectTo The variable with respect to which to derive.
	 * @return The partial derivative with respect to the specified variable.
	 */
	public Differentiable derivative(Variable withRespectTo);
		
	/**
	 * Take the partial derivative with respect to the specified variables.
	 * 
	 * @param withRespectTo The variable with respect to which to derive.
	 * @return The partial derivative.
	 */
	public Differentiable derivative(Variable... withRespectTo);
	
	/**
	 * Compute the gradient of this differentiable.
	 * 
	 * @return The gradient.
	 */
	public Gradient gradient();
	
	/**
	 * Compute the Hessian of this differentiable.
	 * 
	 * @return The hessian.
	 */
	public Hessian hessian();
	
	/**
	 * Get all variables of this differentiable.
	 * 
	 * @return The set of variables.
	 */
	public Set<Variable> getVariables();
	
	// simplification helpers.
	
	/**
	 * Is this is differentiable constant?
	 * 
	 * @return <code>true</code> if constant; <code>false</code> otherwise.
	 */
	public boolean isConstant();
	
	/**
	 * Is this differentiable zero?
	 * 
	 * @return <code>true</code> if zero; <code>false</code> otherwise.
	 */
	public boolean isZero();
	
	/**
	 * Is this differentiable one?
	 * 
	 * @return <code>true</code> if one; <code>false</code> otherwise.
	 */
	public boolean isOne();
	
	// differentiable operators.
	
	/**
	 * Negate this differentiable.
	 * 
	 * @return <code>-this</code>
	 */
	public Differentiable negate();
	
	/**
	 * Add the specified differentiable to this one.
	 * 
	 * @param that The differentiable to add. 
	 * @return <code>this + that</code>
	 */
	public Differentiable plus(Differentiable that);
		
	/**
	 * Add the specified value to this differentiable.
	 * 
	 * @param that The value to add. 
	 * @return <code>this + that</code>
	 */	
	public Differentiable plus(double that);
		
	/**
	 * Subtract the specified differentiable from this one.
	 * 
	 * @param that The differentiable to subtract.
	 * @return <code>this - that</code>
	 */
	public Differentiable minus(Differentiable that);

	/**
	 * Subtract the specified value from this differentiable.
	 * 
	 * @param that The value to subtract.
	 * @return <code>this - that</code>
	 */
	public Differentiable minus(double that);
	
	/**
	 * Multiply this differentiable by the specified one.
	 * 
	 * @param that The differentiable by which to multiply.
	 * @return <code>this * that</code>
	 */
	public Differentiable times(Differentiable that);
	
	/**
	 * Multiply this differentiable by the specified value.
	 * 
	 * @param that The value by which to multiply.
	 * @return <code>this * that</code>
	 */	
	public Differentiable times(double that);
	
	/**
	 * Divide this differentiable by the specified one.
	 * 
	 * @param that The differentiable by which to divide.
	 * @return <code>this / that</code>
	 */
	public Differentiable over(Differentiable that);

	/**
	 * Divide this differentiable by the specified value.
	 * 
	 * @param that The differentiable by which to divide.
	 * @return <code>this / that</code>
	 */
	public Differentiable over(double that);
	
	/**
	 * Divide this differentiable by the specified one.
	 * 
	 * @param that The differentiable by which to divide.
	 * @return <code>this / that</code>
	 */	
	public Differentiable dividedBy(Differentiable that);
	
	/**
	 * Divide this differentiable by the specified value.
	 * 
	 * @param that The value by which to divide.
	 * @return <code>this / that</code>
	 */		
	public Differentiable dividedBy(double that);
	
	/**
	 * Square this differentiable.
	 * 
	 * @return <code>this<sup>2</sup></code>
	 */
	public Differentiable squared();
	
	/**
	 * Raise this differentiable to the index.
	 * 
	 * @param index The index.
	 * @return <code>this<sup>index</sup></code>
	 */
	public Differentiable power(Differentiable index);

	/**
	 * Raise this differentiable to the specified value.
	 * 
	 * @param index The index.
	 * @return <code>this<sup>index</sup></code>
	 */
	public Differentiable power(double index);

	/**
	 * Raise this differentiable to the index.
	 * 
	 * @param index The index.
	 * @return <code>this<sup>index</sup></code>
	 */
	public Differentiable toThe(Differentiable index);

	/**
	 * Raise this differentiable to the specified value.
	 * 
	 * @param index The index.
	 * @return <code>this<sup>index</sup></code>
	 */
	public Differentiable toThe(double index);
	
	// user-facing compilation interface.
	
	/**
	 * Get a compiled version of this function.
	 * 
	 * @return A compiled function.
	 */
	public CompiledDifferentiable compile();
	
	// internal compilation interface.
	
	/**
	 * Get a Java expression for the value of this differentiable.
	 * 
	 * @param codeContext The code context.
	 * @return A Java expression for the value of this differentiable.
	 */
	public Expression expression(Context codeContext);

}
