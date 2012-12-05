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

import java.lang.reflect.Constructor;

import org.teneighty.leibniz.CompiledDifferentiable;
import org.teneighty.leibniz.Differentiable;


/**
 * Compiler framework facade class.
 * <p>
 * This class is stateless (and hence safe for use by multiple threads) and
 * cannot be instantiated.
 */
public final class Compiler
{
	
	/**
	 * Compile the specified differentiable.
	 * 
	 * @param differentiable The differentiable.
	 * @return A compiled version of the specified differentiable.
	 */
	public static CompiledDifferentiable compile(final Differentiable differentiable)
	{
		// generate the code...
		DifferentiableCodeGenerator generator = new DifferentiableCodeGenerator(differentiable);
		String name = generator.getFullyQualifiedClassName();
		String source = generator.getSourceCode();
						
		// compile the code...
		CodeCompiler<CompiledDifferentiable> compiler = new CodeCompiler<CompiledDifferentiable>(name, source);
		Class<CompiledDifferentiable> klass = compiler.getGeneratedClass();
		
		try
		{
			// and, finally, instantiate!
			Constructor<CompiledDifferentiable> constructor = klass.getConstructor(Differentiable.class, String.class);			
			CompiledDifferentiable compiled = constructor.newInstance(differentiable, source);
			
			return compiled;			
		}
		catch(final Exception e)
		{
			throw new IllegalArgumentException("Compilation failed", e);
		}
	}
	
	/**
	 * Constructor.
	 * <p>
	 * Here only for access protection.
	 */
	private Compiler()
	{		
	}

}
