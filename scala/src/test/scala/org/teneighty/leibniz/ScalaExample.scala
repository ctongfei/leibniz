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
package org.teneighty.leibniz

import org.teneighty.leibniz._;

/**
 *
 */
object ScalaExample {
    def main(args : Array[String])
  {
    
    val x : Variable = "x";
    
    val func : Differentiable = x + 1 + 3;
    val dx = func.derivative(x);
   	val dxx = dx.derivative(x);
    
    //println(func.value(Map((x -> 5d))));
    //println(dx.value(Map((x -> 5))));
    //println(dxx.value(Map((x -> 5))));
    
    
    val a : Variable = "a";
    val b : Variable = "b";
    val c : Variable = "c";
    val y :  Variable = "y";
    
    val func2 : Differentiable = a * (x ^ 2) + b * (y ^ 2) + c * x * y;
    val fdx = func2.derivative(x);
    val fdy = func2.derivative(y);
    
    val fdxy = fdx.derivative(y);
    val fdyx = fdy.derivative(x);

    println(fdx.value(Map[Variable, Double](a -> 1, b -> 1, c-> 4, x -> 6, y -> 4)));
    println(fdy.value(Map[Variable, Double](a -> 1, b -> 1, c-> 4, x -> 6, y -> 4)));
        
    println(fdxy.value(Map[Variable, Double](a -> 1, b -> 1, c-> 4, x -> 6, y -> 4)));
    println(fdyx.value(Map[Variable, Double](a -> 1, b -> 1, c-> 4, x -> 6, y -> 4)));
    
    
    
  }

}