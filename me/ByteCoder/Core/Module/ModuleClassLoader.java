package me.ByteCoder.Core.Module;

import java.util.concurrent.CopyOnWriteArraySet;
import java.net.URL;
import java.util.Set;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader
{
    private static final Set<ModuleClassLoader> allLoaders;
    
    public ModuleClassLoader(final URL[] urls) {
        super(urls);
        ModuleClassLoader.allLoaders.add(this);
    }
    
    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        return this.loadClass0(name, resolve, true);
    }
    
    private Class<?> loadClass0(final String name, final boolean resolve, final boolean checkOther) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        }
        catch (ClassNotFoundException ex) {
            if (checkOther) {
                for (final ModuleClassLoader loader : ModuleClassLoader.allLoaders) {
                    if (loader != this) {
                        try {
                            return loader.loadClass0(name, resolve, false);
                        }
                        catch (ClassNotFoundException ex2) {}
                    }
                }
            }
            throw new ClassNotFoundException(name);
        }
    }
    
    static {
        allLoaders = new CopyOnWriteArraySet<ModuleClassLoader>();
        ClassLoader.registerAsParallelCapable();
    }
}
