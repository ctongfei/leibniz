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


/**
 * Source code compilation unit.
 */
class SourceCodeUnit
{
	
	/**
	 * Simple class name.
	 */
	private String simpleClassName;
	
	/**
	 * Source code.
	 */
	private String sourceCode;
	
	/**
	 * Get the simple class name.
	 * 
	 * @return The simple name.
	 */
	public String getSimpleClassName()
	{
		return simpleClassName;
	}
	
	/**
	 * Set the simple class name.
	 * 
	 * @param simpleClassName The simple name.
	 */
	public void setSimpleClassName(final String simpleClassName)
	{
		this.simpleClassName = simpleClassName;
	}
	
	/**
	 * Get the fully qualified class name.
	 * 
	 * @return The fully qualified class name.
	 */
	public String getFullyQualifiedClassName()
	{
		return String.format("org.teneighty.leibniz.compilation.%1$s", simpleClassName);
	}

	/**
	 * Get the source code.
	 * 
	 * @return The source code.
	 */
	public String getSourceCode()
	{
		return sourceCode;
	}

	/**
	 * Set the source code.
	 * 
	 * @param sourceCode The source code.
	 */
	public void setSourceCode(final String sourceCode)
	{
		this.sourceCode = sourceCode;
	}

}
