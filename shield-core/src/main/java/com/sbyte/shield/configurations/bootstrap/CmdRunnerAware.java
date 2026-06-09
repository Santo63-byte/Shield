package com.sbyte.shield.configurations.bootstrap;

import com.sbyte.shield.core.base.annotations.OnApplicationStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component("CmdRunnerAware")
public class CmdRunnerAware implements CommandLineRunner {

    private static final String DEFAULT_INIT_METHOD = "init";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {

        Map<String, Object> onApplicationStarterBeans = applicationContext.getBeansWithAnnotation(OnApplicationStart.class);

        for (Object bean : onApplicationStarterBeans.values()) {
            System.out.println("Initializing OnApplicationStarter bean: " + bean.getClass().getName());
            OnApplicationStart annotation = bean.getClass().getAnnotation(OnApplicationStart.class);
            String methodName = annotation.value().isEmpty() ? DEFAULT_INIT_METHOD : annotation.value() ;
            try {
                Method method = bean.getClass().getMethod(methodName);
                method.invoke(bean);
            } catch (Exception e) {
                throw new RuntimeException("ContextAwarerException :: Failed to invoke method " + methodName + " on bean " + bean.getClass().getName(), e);
            }
        }
    }
}
