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

import org.teneighty.leibniz.Assignment;
import org.teneighty.leibniz.CompiledDifferentiable;
import org.teneighty.leibniz.CompiledGradient;
import org.teneighty.leibniz.CompiledHessian;
import org.teneighty.leibniz.Differentiable;
import org.teneighty.leibniz.Gradient;
import org.teneighty.leibniz.GradientValue;
import org.teneighty.leibniz.Hessian;
import org.teneighty.leibniz.HessianValue;
import org.teneighty.leibniz.Variable;


/**
 * Parabola test.
 */
public class Parabola
{
	
	private static double value(double x)
	{
		return 2 * x * x + 3 *x + 4;
	}
	
	public static void main(final String[] args)
	{
		Variable a = new Variable("a");
		Variable b = new Variable("b");
		Variable c = new Variable("c");
		
		Variable x = new Variable("x");
		
		Differentiable parabola = a.times(x.squared()).plus(b.times(x)).plus(c);
		Assignment assignment = Assignment.Build.start().with(a, 2).with(b, 3).with(c, 4).with(x, 5).finish();
		
		CompiledDifferentiable compiledDiff = parabola.compile();
		System.out.println(parabola.value(assignment));
		System.out.println(value(5));
		
		Gradient gradient = parabola.gradient();
		CompiledGradient compiled = gradient.compile();
		
		GradientValue value = gradient.value(assignment);
		//System.out.println(value.value(a));
		//System.out.println(value.value(b));
		//System.out.println(value.value(c));
		//System.out.println(value.value(x));	
		
		//System.out.println(compiled.source());		
		
		Hessian hessian = parabola.hessian();
		CompiledHessian compiledHessian = hessian.compile();
		//System.out.println(compiledHessian.source());
		hessian = compiledHessian;
		
		
		HessianValue hessianValue = hessian.value(assignment);
		System.out.println(hessianValue.value(a, a));
		System.out.println(hessianValue.value(a, b));
		System.out.println(hessianValue.value(a, c));
		System.out.println(hessianValue.value(a, x));

		System.out.println(hessianValue.value(b, b));
		System.out.println(hessianValue.value(b, c));
		System.out.println(hessianValue.value(b, x));

		System.out.println(hessianValue.value(c, c));
		System.out.println(hessianValue.value(c, x));
		
		System.out.println(hessianValue.value(x, x));
	}

}
