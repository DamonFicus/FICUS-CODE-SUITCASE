package ficus.suitcase.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationContextHelper
    implements ApplicationContextAware
{

	private static ApplicationContext atx;
	
    public ApplicationContextHelper()
    {
    }

    public static ApplicationContext getApplicationContext()
    {
        return atx;
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
        throws BeansException
    {
    	setApplicationContextHelperValue(context);
    }
    
    public static void setApplicationContextHelperValue(ApplicationContext context)
    {
    	 ApplicationContextHelper.atx = context;
    }
   
    public static Object getBean(String name) {
        return null != atx ? atx.getBean(name) : null;
    }

    public static <T> T getBean(Class<T> clazz)
    {
        return null != atx ? atx.getBean(clazz) : null;
    }

    public static Object getBean(String name, Class<?> requiredType)
    {
        return null != atx ? atx.getBean(name, requiredType) : null;
    }

    
}