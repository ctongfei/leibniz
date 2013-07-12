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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;

import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Hessian;
import org.teneighty.leibniz.HessianKey;
import org.teneighty.leibniz.compilation.statement.Statement;


/**
 * Hessian code generator.
 */
final class HessianCodeGenerator
	extends AbstractCodeGenerator<Hessian>
{
	
	/**
	 * @see org.teneighty.leibniz.compilation.CodeGenerator#getSourceCodeUnit(java.lang.Object)
	 */
	@Override
	public SourceCodeUnit getSourceCodeUnit(final Hessian uncompiled)
	{
		SourceCodeUnit unit = new SourceCodeUnit();
		unit.setSimpleClassName(getUniqueClassName());
		
		ExpressionGenerator expressionGenerator = new ExpressionGenerator();
		
		LinkedHashMap<HessianKey, List<ReferenceExpression>> hessianComponents = new LinkedHashMap<HessianKey, List<ReferenceExpression>>();
		for(HessianKey key : uncompiled.keys())
		{
			Differentiable component = uncompiled.component(key);
			List<ReferenceExpression> expressions = expressionGenerator.generate(component);
			hessianComponents.put(key, expressions);
		}
				
		HessianMethodBodyStatementGenerator generator = new HessianMethodBodyStatementGenerator(hessianComponents);		
		List<Statement> statements = generator.getStatements();

		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		
		// write all the magic.
		writeClassDefinition(printWriter, unit.getSimpleClassName());
		writeConstructor(printWriter, unit.getSimpleClassName());
		writeValueMethod(printWriter, statements);		
		writeClassTrailer(printWriter);
				
		printWriter.flush();
		writer.flush();
		
		// store the source code, and we're done here.
		String sourceCode = writer.toString();
		unit.setSourceCode(sourceCode);
		
		return unit;
	}
	
	/**
	 * Write the class package, import, etc.
	 * 
	 * @param simpleClassName The simple class name.
	 * @param writer The write to which to write.
	 */
	private void writeClassDefinition(final PrintWriter writer, final String simpleClassName)
	{
		// write class header.
		writer.println("package org.teneighty.leibniz.compilation;");
		writer.println();
		writer.println("import java.io.Serializable;");
		writer.println();
		writer.println("import org.teneighty.leibniz.Assignment;");
		writer.println("import org.teneighty.leibniz.Hessian;");
		writer.println("import org.teneighty.leibniz.HessianValue;");
		writer.println("import org.teneighty.leibniz.MutableHessianValue;");
		writer.println("import org.teneighty.leibniz.Variable;");
		writer.println();
		writer.println(String.format("public final class %1$s", simpleClassName));
		writer.println("\textends AbstractCompiledHessian");
		writer.println("\timplements Serializable");
		writer.println("{");
		writer.println();
		writer.println("private static final long serialVersionUID = 1L;");
		writer.println();
	}
	
	/**
	 * Write the constructor.
	 * 
	 * @param simpleClassName The simple class name.
	 * @param writer The writer to which to write.
	 */
	private void writeConstructor(final PrintWriter writer, final String simpleClassName)
	{
		// write constructor.
		writer.println(String.format("\tpublic %1$s(final Hessian hessian, final String source)", simpleClassName));
		writer.println("\t{");
		writer.println("\t\tsuper(hessian, source);");
		writer.println("\t}");
		writer.println();
	}
	
	/**
	 * Write the value method, given the specified code statements.
	 * 
	 * @param writer The writer.
	 * @param statements The method body code statements.
	 */
	private void writeValueMethod(final PrintWriter writer, final List<Statement> statements)
	{
		writer.println("\tpublic HessianValue value(final Assignment assignment)");
		writer.println("\t{");
		
		for(Statement statement : statements)
		{
			writer.println(String.format("\t\t%1$s", statement.code()));
		}
		
		writer.println("\t}");
		writer.println();
	}
	
	/**
	 * Write the class trailer.
	 * 
	 * @param writer The writer to which to write.
	 */
	private void writeClassTrailer(final PrintWriter writer)
	{
		writer.println("}");
	}

	
}