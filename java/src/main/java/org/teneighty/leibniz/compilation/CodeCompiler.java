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

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Compiles code into a Java class.
 * 
 * @param <TCompile> The type we'll compile.
 */
final class CodeCompiler<TCompile>
{

	/**
	 * The class we generated.
	 */
	private Class<TCompile> generatedClass;

	/**
	 * Constructor.
	 * 
	 * @param fullyQualifiedClassName The fully-qualified class name. 
	 * @param source The Java source code, as a string.
	 */
	CodeCompiler(final String fullyQualifiedClassName, final String source)
	{
		generate(fullyQualifiedClassName, source);
	}

	/**
	 * Get the generated class.
	 * 
	 * @return The generated class.
	 */
	Class<TCompile> getGeneratedClass()
	{
		return generatedClass;
	}

	/**
	 * Generate the class file.
	 * 
	 * @param fullyQualifiedClassName Class name.
	 * @param sourceCode The source.
	 */
	@SuppressWarnings("unchecked")
	private void generate(final String fullyQualifiedClassName,
			final String sourceCode)
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// create string-based "files".
		List<JavaFileObject> files = new ArrayList<JavaFileObject>();
		files.add(new StringJavaFileObject(fullyQualifiedClassName, sourceCode));

		// create manager to capture the compiler output.
		StandardJavaFileManager standardManager = compiler.getStandardFileManager(null, null, null);
		ByteArrayOutputStreamJavaFileManager streamManager = new ByteArrayOutputStreamJavaFileManager(standardManager);

		// invoke the compiler.
		compiler.getTask(null, streamManager, null, null, null, files).call();

		try
		{
			// extract the generated class.
			generatedClass = (Class<TCompile>)streamManager.getClassLoader(null).loadClass(fullyQualifiedClassName);
		}
		catch(final ClassNotFoundException cnfe)
		{
			String message = String.format("Unable to compile %1$s", fullyQualifiedClassName);
			throw new IllegalStateException(message, cnfe);
		}
	}

}
