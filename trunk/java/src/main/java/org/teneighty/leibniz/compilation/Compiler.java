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
import org.teneighty.leibniz.CompiledGradient;
import org.teneighty.leibniz.CompiledHessian;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Gradient;
import org.teneighty.leibniz.Hessian;


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
	 * @param differentiable The differentiable to compile.
	 * @return A compiled differentiable.
	 */
	public static CompiledDifferentiable compile(final Differentiable differentiable)
	{
		DifferentiableCodeGenerator codeGenerator = new DifferentiableCodeGenerator();
		CompiledDifferentiable compiled = compile(Differentiable.class, differentiable, codeGenerator);
		
		return compiled;
	}
	
	/**
	 * Compile the specified gradient.
	 * 
	 * @param gradient The gradient to compile.
	 * @return A compiled gradient.
	 */
	public static CompiledGradient compile(final Gradient gradient)
	{
		GradientCodeGenerator codeGenerator = new GradientCodeGenerator();
		CompiledGradient compiled = compile(Gradient.class, gradient, codeGenerator);
		
		return compiled;
	}

	/**
	 * Compile the specified Hessian.
	 * 
	 * @param hessian The hessian to compile.
	 * @return A compiled Hessian.
	 */
	public static CompiledHessian compile(final Hessian hessian)
	{
		HessianCodeGenerator codeGenerator = new HessianCodeGenerator();
		CompiledHessian compiled = compile(Hessian.class, hessian, codeGenerator);
		
		return compiled;
	}
	
	/**
	 * Core compile method.
	 * 
	 * @param <TUncompiled> The uncompiled type.
	 * @param <TCompiled> The compiled type.
	 * @param uncompiledType The class of the uncompiled object.
	 * @param uncompiled The uncompiled object.
	 * @param generator A code generator.
	 * @return A compiled version of <code>uncompiled</code>.
	 */
	private static <TUncompiled, TCompiled> TCompiled compile(
			final Class<TUncompiled> uncompiledType, 
			final TUncompiled uncompiled, 
			final CodeGenerator<TUncompiled> generator)
	{
		// generate the source.
		SourceCodeUnit sourceCode = generator.getSourceCodeUnit(uncompiled);
		String source = sourceCode.getSourceCode();
		
		// compile the code.
		CodeCompiler<TCompiled> compiler = new CodeCompiler<TCompiled>();
		Class<TCompiled> compiledClass = compiler.compile(sourceCode);
		
		try
		{
			// and, finally, instantiate!
			Constructor<TCompiled> constructor = compiledClass.getConstructor(uncompiledType, String.class);			
			TCompiled compiled = constructor.newInstance(uncompiled, source);
			
			return compiled;			
		}
		catch(final Exception e)
		{
			// whatever... checked exceptions can be annoying.
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
