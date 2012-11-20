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
 * The gradient.
 */
public interface Gradient
{
			
	/**
	 * Evaluate the gradient given the specified assignment.
	 * <p>
	 * The order is given by the order of the variables of the underlying differentiable.
	 * 
	 * @param assignment The assignment.
	 * @return The value.
	 */
	public double[] value(Assignment assignment);
	
	/**
	 * Evaluate the gradient given the specified assignment, returning the variables
	 * in the specified order. 
	 * 
	 * @param assignment The assignment.
	 * @param order The order.
	 * @return The value.
	 */
	public double[] value(Assignment assignment, Variable... order);

	/**
	 * Get the underlying differentiable.
	 * 
	 * @return The underlying differentiable.
	 */
	public Differentiable differentiable();
	
	/**
	 * Get the partial derivative coordinate function for the specified variable. 
	 * 
	 * @param variable The variable.
	 * @return The partial derivative with respect to the specified variable.
	 */
	public Differentiable coordinate(Variable variable);

}
