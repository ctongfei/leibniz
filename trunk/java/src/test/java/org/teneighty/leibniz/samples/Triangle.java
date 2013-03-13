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
package org.teneighty.leibniz.samples;

import static org.teneighty.leibniz.Differentiables.sin;
import static org.teneighty.leibniz.Differentiables.rad;

import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Variable;


/**
 * 
 */
public class Triangle
{

	
	void foo()
	{
		Variable a = new Variable("a");
		Variable b = new Variable("b");
		Variable c = new Variable("c");
		
		Assignment assignment = Assignment.Build.start().with(a, 1).with(b, 2).with(c, 3).finish();

	}
	
	public static void main(String[] args)
	{
		Variable a = new Variable("a");
		Variable b = new Variable("b");
		Variable beta = new Variable("beta");
		
		Differentiable triangleArea = a.times(b).times(sin(rad(beta))).over(2);
		
		// a 3-4-5 Pythagorean triangle.
		Assignment pythag = Assignment.Build.start().with(a, 3).with(b, 4).with(beta, 90).finish();
		
		// the area.
		System.out.println(triangleArea.value(pythag));
		
		Differentiable da = triangleArea.derivative(a);
		System.out.println(da.value(pythag));
		
		Differentiable db = triangleArea.derivative(b);
		System.out.println(db.value(pythag));
		
		Differentiable dbeta = triangleArea.derivative(beta);
		System.out.println(dbeta.value(pythag));
	}
	

}
