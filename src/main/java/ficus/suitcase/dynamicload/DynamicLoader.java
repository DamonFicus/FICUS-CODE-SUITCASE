package ficus.suitcase.dynamicload;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态装备字节码
 * @author DamonFicus
 */
public class DynamicLoader {

	private static final Pattern  INTERFACE_PATTERN=Pattern.compile("public\\s+interface\\s+(\\w+)");

	private static final Pattern  CLASS_PATTERN=Pattern.compile("public\\s+class\\s+(\\w+)");


	public static Map<String, byte[]> compileInterface(String javaSrc) {
		Matcher matcher = INTERFACE_PATTERN.matcher(javaSrc);
		if (matcher.find()) {
			return compile(matcher.group(1) + ".java", javaSrc);
		}
		return null;
	}
	
	public static Map<String, byte[]> compileClass(String javaSrc) {
		Matcher matcher = CLASS_PATTERN.matcher(javaSrc);
		if (matcher.find()) {
			return compile(matcher.group(1) + ".java", javaSrc);
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public static Map<String, byte[]> compile(String javaName, String javaSrc) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
		try {
			MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager);
			JavaFileObject javaFileObject = MemoryJavaFileManager.makeStringSource(javaName, javaSrc);
			JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
			if (task.call()) {
				return manager.getClassBytes();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class MemoryClassLoader extends URLClassLoader {
		Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

		public MemoryClassLoader(Map<String, byte[]> classBytes) {
			super(new URL[0], MemoryClassLoader.class.getClassLoader());
			this.classBytes.putAll(classBytes);
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			byte[] buf = classBytes.get(name);
			if (buf == null) {
				return super.findClass(name);
			}
			classBytes.remove(name);
			return defineClass(name, buf, 0, buf.length);
		}
	}
}
