package ficus.suitcase.dynamicload;


import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 动态生成字节码并运行的场景实例；
 * 从数据库中获取包，方法名，统一进行入口调用；
 * @author DamonFicus
 */
public final class MethodProxyUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodProxyUtil.class);
	
	private static final String APP_NAME = "debit-server";
	
	private static final int CONN_TIMEOUT = 10000;
	
	private static final int READ_TIMEOUT = 180000;

	private static final String REGEX = "[\\w.]+:[\\w.]+";
	
	private static final Map<String, Map<String, Object>> SERVICE_INFO_MAP = new HashMap<String, Map<String,Object>>();
	
	/**
	 * 调用远端Hessian服务方法
	 * @param hessianUrl 远端hessian服务访问地址
	 * @param pkgName 包名
	 * @param interfaceName 接口名
	 * @param methodName 方法名
	 * @param argTypes 参数类型 例如：{msg:java.lang.String, list:java.util.List}
	 * @param argMap 参数值对照
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void invokeRemoteMethodByHessian(
			String hessianUrl, String pkgName, String interfaceName, 
			String methodName, String argTypes, Map<String, Object> argMap) {
		try {
			Map<String, Object> map = null;
			synchronized (SERVICE_INFO_MAP) {
				if (SERVICE_INFO_MAP.containsKey(hessianUrl)) {
					map = SERVICE_INFO_MAP.get(hessianUrl);
				} else {
					map = getRemoteInterfaceInfo(pkgName, interfaceName, methodName, argTypes);
					SERVICE_INFO_MAP.put(hessianUrl, map);
				}
			} 
			Class<?> clazz = (Class<?>) map.get("clazz");
			Map<String, Class> paramTypes = (Map<String, Class>) map.get("paramTypes");
			Method method = (Method) map.get("method");
			URLClassLoader classLoader = (URLClassLoader) map.get("classLoader");			
			HessianProxyFactory factory = new HessianProxyFactory(classLoader);
			factory.setConnectTimeout(CONN_TIMEOUT);
			factory.setReadTimeout(READ_TIMEOUT);
	        Object obj = factory.create(clazz, hessianUrl);
	        invokeMethod(argMap, paramTypes, method, obj);   			
		} catch (Exception e) {
			logger.error("HessianProxyUtil.invokeRemoteMethodByHessian exception", e);
		}
	}
	/**
	 * 执行服务方法
	 * @param argMap
	 * @param paramTypes
	 * @param method
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static void invokeMethod(Map<String, Object> argMap,
			Map<String, Class> paramTypes, Method method, Object obj)
			throws Exception {
		if (!CollectionUtils.isEmpty(paramTypes)) {
			Object[] args = new Object[paramTypes.size()];
			int i = 0;
			for (String key : paramTypes.keySet()) {
				if (!argMap.containsKey(key)) {
					throw new Exception(String.format("[%s] parameter invalid!!!", key));
				}
				args[i++] = argMap.get(key); 
			}
		    method.invoke(obj, args);	
		} else {
		    method.invoke(obj);	
		}
	}
	/**
	 * 获取远端接口及方法信息
	 * @param pkgName
	 * @param interfaceName
	 * @param methodName
	 * @param argTypes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> getRemoteInterfaceInfo(
			String pkgName, String interfaceName, String methodName, String argTypes) throws Exception {
		Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, String> types = parseArgTypes(argTypes);
		Map<String, Class> paramTypes = null;
		StringBuilder paramStrB = new StringBuilder();
		if (types != null && types.size() > 0) {
			paramTypes = new LinkedHashMap<>(types.size());
			for (String key : types.keySet()) {
				String className = types.get(key);
				paramStrB.append(",").append(className).append(" ").append(key);
				paramTypes.put(key, Class.forName(className));
			}
		}
		if (paramStrB.length() > 0) {
			paramStrB.deleteCharAt(0);
		}
		paramStrB.insert(0, "(").append(")");		
		String javaInterface = "package " + pkgName + ";" +
				"public interface " + interfaceName + " {" +
				"	public void " + methodName + paramStrB +";" + 
				"}" +
				"" +
				"";
		Map<String, byte[]> map = DynamicLoader.compileInterface(javaInterface);
		DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(map);
		Class<?> clazz = classLoader.loadClass(pkgName + "." + interfaceName);
		Method method = null;        
		if (paramTypes != null) {			
        	method = clazz.getMethod(methodName, 
        			(Class[])paramTypes.values().toArray(new Class[0]));
        } else {
        	method = clazz.getMethod(methodName);
        } 		
		resultMap.put("clazz", clazz);
		resultMap.put("method", method);
		resultMap.put("paramTypes", paramTypes);
		resultMap.put("classLoader", classLoader);
		return resultMap;
	}
	
	/**
	 * 解析参数类型 json格式字符串 {msg:java.lang.String, list:java.util.List}
	 * @param argTypes
	 * @return
	 */
	private static Map<String, String> parseArgTypes(String argTypes) {		
		Map<String,String> result = new LinkedHashMap<String,String>();
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(argTypes);
		while(matcher.find()) {
			String[] groups = matcher.group().split(":");
			result.put(groups[0].trim(), groups[1].trim());
		}
		return result;
	}
	
	/**
	 * 调用本地Spring服务方法
	 * @param pkgName 包名
	 * @param interfaceName 接口名
	 * @param methodName 方法名
	 * @param argTypes 参数类型 例如：{msg:java.lang.String, list:java.util.List}
	 * @param argMap 参数值对照
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void invokeLocalMethod(String pkgName, String interfaceName, 
			String methodName, String argTypes, Map<String, Object> argMap) {
		try {			
			Map<String, Object> map = null;
			synchronized (SERVICE_INFO_MAP) {
				String localKey = new StringBuilder().append(pkgName)
						.append(interfaceName).append(methodName).append(argTypes).toString();
				if (SERVICE_INFO_MAP.containsKey(localKey)) {
					map = SERVICE_INFO_MAP.get(localKey);
				} else {
					map = getLocalInterfaceInfo(pkgName, interfaceName, methodName, argTypes);
					SERVICE_INFO_MAP.put(localKey, map);
				}
			} 			
			Class<?> clazz = (Class<?>) map.get("clazz");
			Map<String, Class> paramTypes = (Map<String, Class>) map.get("paramTypes");
			Method method = (Method) map.get("method");			
			Object obj = SpringContextHolder.getBean(clazz);
			invokeMethod(argMap, paramTypes, method, obj);
		} catch (Exception e) {
			logger.error("HessianProxyUtil.invokeLocalMethod exception", e);
		}		
	}

	/**
	 * 获取本地接口信息
	 * @param pkgName
	 * @param interfaceName
	 * @param methodName
	 * @param argTypes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> getLocalInterfaceInfo(String pkgName,
			String interfaceName, String methodName, String argTypes) throws Exception {
		Map<String, Object> resultMap = new HashMap<>(16);
		String interfaceFullName = pkgName + "." + interfaceName;
		Class<?> clazz = Class.forName(interfaceFullName);
        Map<String, String> types = parseArgTypes(argTypes);
        Map<String, Class> paramTypes = null;
		if (types != null && types.size() > 0) {
			paramTypes = new LinkedHashMap<String, Class>(types.size());
			for (String key : types.keySet()) {
				String className = types.get(key);
				paramTypes.put(key, Class.forName(className));
			}
		}
		Method method = null;        
		if (paramTypes != null) {			
        	method = clazz.getMethod(methodName, 
        			(Class[])paramTypes.values().toArray(new Class[0]));
        } else {
        	method = clazz.getMethod(methodName);
        }		
		resultMap.put("clazz", clazz);
		resultMap.put("method", method);
		resultMap.put("paramTypes", paramTypes);
		return resultMap;
	}
}
