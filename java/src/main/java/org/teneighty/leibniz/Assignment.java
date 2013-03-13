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
 * An assignment of variables to values.
 */
public interface Assignment
{

	/**
	 * Get the value of the specified variable.
	 * 
	 * @param variable The variable.
	 * @return The value.
	 * @throws IllegalArgumentException If no value has been assigned to <code>variable</code>.
	 * @throws NullPointerException If <code>variable</cod> is <code>null</code>.
	 */
	public double get(Variable variable)
		throws IllegalArgumentException, NullPointerException;
	
	/**
	 * Check if the specified variable is set.
	 *  
	 * @param variable The variable to check.
	 * @return <code>true</code> if <code>variable</code> is set; <code>false</code> otherwise.
	 * @throws NullPointerException If <code>variable</cod> is <code>null</code>.
	 */
	public boolean isSet(Variable variable)
		throws NullPointerException;
	
	/**
	 * An assignment builder.
	 * <p>
	 * Here is simple example of how to the the builder (setting <code>a</code>
	 * to 1, <code>b</code> to 2, and <code>c</code> to 3):
	 * <pre>
	 * Variable a = new Variable(&quot;a&quot;);
	 * Variable b = new Variable(&quot;b&quot;);
	 * Variable c = new Variable(&quot;c&quot;);
	 * 
	 * Assignment assignment = Assignment.Build.start().with(a, 1).with(b, 2).with(c, 3).finish();
	 * </pre>
	 */
	public static final class Build
	{
		
		/**
		 * Start a new assignment build.
		 * 
		 * @return A new build.
		 */
		public static Build start()
		{
			return new Build();
		}
		
		/**
		 * The underlying assignment.
		 */
		private final MutableAssignment assignment;
		
		/**
		 * Constructor.
		 */
		private Build()
		{
			assignment = new MutableAssignment();
		}
		
		/**
		 * Assign the value to the specifed variable.
		 * 
		 * @param variable The variable.
		 * @param value The value.
		 * @return This object.
		 */
		public Build with(final Variable variable, final double value)
		{
			assignment.set(variable, value);
			return this;
		}
		
		/**
		 * Finish building an <code>Assignment</code>.
		 * 
		 * @return The <code>Assignment</code>.
		 */
		public Assignment finish()
		{
			return assignment;
		}
		
	}
		
}
