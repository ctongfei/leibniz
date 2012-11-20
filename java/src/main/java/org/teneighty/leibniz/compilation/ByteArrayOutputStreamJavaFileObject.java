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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Java file object for writing output that captures everything in an in-memory
 * buffer.
 */
final class ByteArrayOutputStreamJavaFileObject
	extends SimpleJavaFileObject
{

	/**
	 * Buffer for the bytes.
	 */
	private final ByteArrayOutputStream outputStream;

	/**
	 * Registers the compiled class object under URI containing the class full
	 * name
	 * 
	 * @param name Full name of the compiled class
	 * @param kind Kind of the data. It will be CLASS in our case
	 */
	public ByteArrayOutputStreamJavaFileObject(final String name,
			final Kind kind)
	{
		super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);

		outputStream = new ByteArrayOutputStream();
	}

	/**
	 * Get the bytes of the output.
	 * 
	 * @return The bytes.
	 */
	byte[] getBytes()
	{
		return outputStream.toByteArray();
	}

	/**
	 * @see javax.tools.SimpleJavaFileObject#openOutputStream()
	 */
	@Override
	public OutputStream openOutputStream()
	{
		return outputStream;
	}

}
