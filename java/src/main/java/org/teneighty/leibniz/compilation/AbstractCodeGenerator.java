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
package org.teneighty.leibniz.compilation;

import java.util.UUID;


/**
 * Base class for code generators.
 */
abstract class AbstractCodeGenerator
	implements CodeGenerator
{
	
	/**
	 * Get a unique class name.
	 * 
	 * @return A unique class name.
	 */
	private static String getUniqueClassName()
	{
		UUID id = UUID.randomUUID();
		return String.format("Compiled_%1$s", id.toString().replace('-', '_'));
	}
			
	/**
	 * Generated class name.
	 */
	private final String simpleName;
		
	/**
	 * Constructor.
	 */
	protected AbstractCodeGenerator()
	{
		simpleName = getUniqueClassName();		
	}
	
	/**
	 * Get the simple class name.
	 * 
	 * @return The simple class name.
	 */
	@Override
	public String getSimpleClassName()
	{
		return simpleName;
	}
	
	/**
	 * Get the fully qualified class name.
	 * 
	 * @return The fully qualified class name.
	 */
	@Override
	public String getFullyQualifiedClassName()
	{
		return String.format("org.teneighty.leibniz.compilation.%1$s", simpleName);
	}
	
}
