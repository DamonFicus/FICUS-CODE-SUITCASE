package ficus.suitcase.dynamicLoad;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用场景：
 * 在运行期间，动态创建字节码并运行
 * 常规的方式，对于一个java文件编译成.class文件并予以执行，一般JavaCompiler 直接run的方式
 * 要求源码位于磁盘上，且编译后的字节码也是写在本地磁盘上
 * 而要对于内存中动态生成或者是网络上获取的java文件，运行期动态创建并运行，
 * 就要通过ForwardingJavaFileManager 的API去实现；
 *
 */
public final class MemoryJavaFileManager extends ForwardingJavaFileManager {

	private final static String EXT = ".java";
	private Map<String, byte[]> classBytes;

	public MemoryJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
		classBytes = new HashMap<String, byte[]>();
	}

	public Map<String, byte[]> getClassBytes() {
		return classBytes;
	}

	@Override
	public void close() throws IOException {
		classBytes = new HashMap<String, byte[]>();
	}

	@Override
	public void flush() throws IOException {
	}

	/**
	 * A file object used to represent Java source coming from a string.
	 */
	private static class StringInputBuffer extends SimpleJavaFileObject {
		final String code;

		StringInputBuffer(String name, String code) {
			super(toURI(name), Kind.SOURCE);
			this.code = code;
		}

		@Override
		public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
			return CharBuffer.wrap(code);
		}

		@SuppressWarnings("unused")
		public Reader openReader() {
			return new StringReader(code);
		}
	}

	/**
	 * A file object that stores Java bytecode into the classBytes map.
	 */
	private class ClassOutputBuffer extends SimpleJavaFileObject {
		private String name;

		ClassOutputBuffer(String name) {
			super(toURI(name), Kind.CLASS);
			this.name = name;
		}

		@Override
		public OutputStream openOutputStream() {
			return new FilterOutputStream(new ByteArrayOutputStream()) {
				@Override
				public void close() throws IOException {
					out.close();
					ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
					classBytes.put(name, bos.toByteArray());
				}
			};
		}
	}

	@Override
	public JavaFileObject getJavaFileForOutput(
			Location location, String className,
			JavaFileObject.Kind kind, FileObject sibling) throws IOException {
		if (kind == JavaFileObject.Kind.CLASS) {
			return new ClassOutputBuffer(className);
		} else {
			return super.getJavaFileForOutput(location, className, kind,
					sibling);
		}
	}

	static JavaFileObject makeStringSource(String name, String code) {
		return new StringInputBuffer(name, code);
	}

	static URI toURI(String name) {
		File file = new File(name);
		if (file.exists()) {
			return file.toURI();
		} else {
			try {
				final StringBuilder newUri = new StringBuilder();
				newUri.append("mfm:///");
				newUri.append(name.replace('.', '/'));
				if (name.endsWith(EXT)) {
					newUri.replace(newUri.length() - EXT.length(),
							newUri.length(), EXT);
				}
				return URI.create(newUri.toString());
			} catch (Exception exp) {
				return URI.create("mfm:///com/sun/script/java/java_source");
			}
		}
	}
}
