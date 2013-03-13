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
 * Some static functions useful for testing.
 */
public final class TestUtilities
{
		
	/**
	 * Assign the specified value to all of the given variables.
	 * 
	 * @param value The value.
	 * @param variables The variables.
	 * @return An assignment of the aforementioned nature.
	 */
	public static Assignment constant(final double value, final Variable... variables)
	{
		MutableAssignment assignment = new MutableAssignment();
		for(Variable variable : variables)
		{
			assignment.set(variable, Double.valueOf(value));
		}
		
		return assignment;
	}	
	
	/**
	 * Constructor.
	 * <p>
	 * Here only for access protection.
	 */
	private TestUtilities()
	{
	}
}
