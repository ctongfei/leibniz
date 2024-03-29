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
 * The gradient of a scalar function.
 */
public interface Gradient
{
	
	/**
	 * Get the value of this gradient given the specified assignment.
	 * 
	 * @param assignment The assignment.
	 * @return The value.
	 */
	public GradientValue value(Assignment assignment);
	
	/**
	 * Get the differentiable of which this is the gradient.
	 * 
	 * @return Said differentiable.
	 */
	public Differentiable differentiable();
	
	/**
	 * Get the differentiable for the specified component.
	 * 
	 * @param component The component.
	 * @return The component.
	 */
	public Differentiable component(Variable component);
		
	/**
	 * Compile this gradient.
	 * 
	 * @return A compiled gradient.
	 */
	public CompiledGradient compile();
	
	/**
	 * Get the variables.
	 * 
	 * @return The variables.
	 */
	public Set<Variable> variables();

}
