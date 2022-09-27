package Reflection;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ExtensionLoader<C> {

    public Class load(String directory, String classpath, Class<C> parentClass) throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        File pluginsDir = new File(directory);
        return  load(pluginsDir , classpath , parentClass);
    }

    public Class load(File file, String classpath, Class<C> parentClass ) throws ClassNotFoundException, MalformedURLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (File childClass : file.listFiles()) {
            try {
                ClassLoader loader = URLClassLoader.newInstance(
                        new URL[]{childClass.toURL()},
                        getClass().getClassLoader()
                );
                Class<?> clazz = Class.forName(classpath, true, loader);
                Class<? extends C> newClass = clazz.asSubclass(parentClass);
                return newClass;
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
        throw new ClassNotFoundException(" class not found in directory");
    }
}