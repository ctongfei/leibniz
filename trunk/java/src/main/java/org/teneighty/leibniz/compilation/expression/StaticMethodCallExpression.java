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
package org.teneighty.leibniz.compilation.expression;


/**
 * A static method class expression.
 */
public final class StaticMethodCallExpression
	extends AbstractExpression
{
	
	/**
	 * The fully qualified class name.
	 */
	private final String fullyQualfiedClassName;
	
	/**
	 * The method name.
	 */
	private final String methodName;
	
	/**
	 * The method arguments.
	 */
	private final Expression[] arguments;

	/**
	 * Constructor.
	 * 
	 * @param fullyQualfiedClassName The fully-qualified class name.
	 * @param methodName The method name.
	 * @param arguments Method arguments.
	 */
	public StaticMethodCallExpression(final String fullyQualfiedClassName,
			final String methodName, final Expression... arguments)
	{
		this.fullyQualfiedClassName = fullyQualfiedClassName;
		this.methodName = methodName;
		this.arguments = arguments;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param clazz The class.
	 * @param methodName The method name.
	 * @param arguments Method arguments.
	 */
	public StaticMethodCallExpression(final Class<?> clazz,
			final String methodName, final Expression... arguments)
	{
		this.fullyQualfiedClassName = clazz.getCanonicalName();
		this.methodName = methodName;
		this.arguments = arguments;
	}

	/**
	 * @see org.teneighty.leibniz.compilation.expression.Expression#code()
	 */
	@Override
	public String code()
	{
		StringBuilder code = new StringBuilder();
		code.append(fullyQualfiedClassName);
		code.append(".");
		code.append(methodName);
		code.append("(");
		
		String sep = "";
		for(Expression argument : arguments)
		{
			code.append(sep);
			code.append(argument.code());
			sep = ", ";
		}
		
		code.append(")");
		
		return code.toString();
	}
	
}
