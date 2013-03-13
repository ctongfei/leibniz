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
import java.util.List;

import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.compilation.statement.Statement;


/**
 * Differentiable code generator.
 */
final class DifferentiableCodeGenerator
	extends AbstractCodeGenerator
{


	/**
	 * The source code.
	 */
	private final String sourceCode;
	
	/**
	 * @param differentiable
	 */
	DifferentiableCodeGenerator(final Differentiable differentiable)
	{
		this.sourceCode = generateSourceCode(differentiable);
	}

	/**
	 * @see org.teneighty.leibniz.compilation.CodeGenerator#getSourceCode()
	 */
	@Override
	public String getSourceCode()
	{
		return sourceCode;
	}
	
	/**
	 * Generate source code the specified differentiable.s
	 *  
	 * @param differentiable The differentiable.
	 * @return The source code.
	 */
	private String generateSourceCode(final Differentiable differentiable)
	{	
		// get all code expressions for our differentiable.
		ExpressionGenerator expressionGenerator = new ExpressionGenerator();
		List<ReferenceExpression> expressions = expressionGenerator.generate(differentiable);
		
		// and convert to statements.
		DifferentiableMethodBodyStatementGenerator statementGenerator = new DifferentiableMethodBodyStatementGenerator(expressions);
		List<Statement> statements = statementGenerator.getStatements();

		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		
		// write all the magic.
		writeClassDefinition(printWriter);
		writeConstructor(printWriter);
		writeValueMethod(printWriter, statements);		
		writeClassTrailer(printWriter);
				
		printWriter.flush();
		writer.flush();
		return writer.getBuffer().toString();
	}
	
	/**
	 * Write the class package, import, etc.
	 * 
	 * @param writer The write to which to write.
	 */
	private void writeClassDefinition(final PrintWriter writer)
	{
		// write class header.
		writer.println("package org.teneighty.leibniz.compilation;");
		writer.println();
		writer.println("import java.io.Serializable;");
		writer.println();
		writer.println("import org.teneighty.leibniz.Assignment;");
		writer.println("import org.teneighty.leibniz.Differentiable;");
		writer.println("import org.teneighty.leibniz.Variable;");
		writer.println();
		writer.println(String.format("public final class %1$s", getSimpleClassName()));
		writer.println("\textends AbstractCompiledDifferentiable");
		writer.println("\timplements Serializable");
		writer.println("{");
		writer.println();
		writer.println("private static final long serialVersionUID = 1L;");
		writer.println();
	}
	
	/**
	 * Write the constructor.
	 * 
	 * @param writer The writer to which to write.
	 */
	private void writeConstructor(final PrintWriter writer)
	{
		// write constructor.
		writer.println(String.format("\tpublic %1$s(final Differentiable differentiable, final String source)", getSimpleClassName()));
		writer.println("\t{");
		writer.println("\t\tsuper(differentiable, source);");
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
		writer.println("\tpublic double value(final Assignment assignment)");
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
