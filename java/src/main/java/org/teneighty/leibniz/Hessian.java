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


/**
 * The Hessian of a scalar function.
 */
public interface Hessian
{
	
	/**
	 * Get the value of this Hessian given the specified assignment.
	 * 
	 * @param assignment The assignment.
	 * @return The value.
	 */
	public HessianValue value(Assignment assignment);
	
	/**
	 * Get the differentiable of which this is the Hessian.
	 * 
	 * @return Said differentiable.
	 */
	public Differentiable differentiable();
	
	/**
	 * Get the differentiable for the specified components.
	 * 
	 * @param first The first variable.
	 * @param second The second variable.
	 * @return The differentiable in question.
	 */
	public Differentiable component(Variable first, Variable second);
	
	/**
	 * Get the component for the specified key.
	 * 
	 * @param key The key.
	 * @return The component for the specified key.
	 */
	public Differentiable component(HessianKey key);
		
	/**
	 * Compile this Hessian.
	 * 
	 * @return A compiled Hessian.
	 */
	public CompiledHessian compile();
	
	// variables accessors.
	
	/**
	 * Get the variables.
	 * 
	 * @return The variables.
	 */
	public Set<Variable> variables();
	
	/**
	 * Get the key set.
	 * 
	 * @return The key set.
	 */
	public Set<HessianKey> keys();	

}
