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

import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

/**
 * File manager that uses in-memory byte buffer file objects.
 */
final class ByteArrayOutputStreamJavaFileManager
	extends ForwardingJavaFileManager<StandardJavaFileManager>
{

	/**
	 * Map of classes by name.
	 */
	private final Map<String, ByteArrayOutputStreamJavaFileObject> classMap;

	/**
	 * Constructor.
	 * 
	 * @param standardManager The manager to which to delegate.
	 */
	public ByteArrayOutputStreamJavaFileManager(
			final StandardJavaFileManager standardManager)
	{
		super(standardManager);
		
		classMap = new HashMap<String, ByteArrayOutputStreamJavaFileObject>();
	}

	/**
	 * @see javax.tools.ForwardingJavaFileManager#getClassLoader(javax.tools.JavaFileManager.Location)
	 */
	@Override
	public ClassLoader getClassLoader(final Location location)
	{
		return new SecureClassLoader()
		{

			@Override
			protected Class<?> findClass(final String name)
				throws ClassNotFoundException
			{
				ByteArrayOutputStreamJavaFileObject outputStreamFileObject = classMap.get(name);
				if(outputStreamFileObject == null)
				{
					throw new ClassNotFoundException(name);
				}
				
				byte[] b = outputStreamFileObject.getBytes();
				return super.defineClass(name, outputStreamFileObject.getBytes(), 0, b.length);
			}
		};
	}

	/**
	 * @see javax.tools.ForwardingJavaFileManager#getJavaFileForOutput(javax.tools.JavaFileManager.Location, java.lang.String, javax.tools.JavaFileObject.Kind, javax.tools.FileObject)
	 */
	@Override
	public JavaFileObject getJavaFileForOutput(final Location location,
			final String className, final Kind kind, final FileObject sibling)
	{		
		ByteArrayOutputStreamJavaFileObject outputStreamFileObject = new ByteArrayOutputStreamJavaFileObject(className, kind);
		classMap.put(className, outputStreamFileObject);
		return outputStreamFileObject;
	}
	
	/**
	 * @see javax.tools.ForwardingJavaFileManager#close()
	 */
	@Override
	public void close() throws IOException
	{
		classMap.clear();
		super.close();
	}
	
}
