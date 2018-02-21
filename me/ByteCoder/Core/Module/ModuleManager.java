package me.ByteCoder.Core.Module;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import com.google.common.base.Preconditions;

public class ModuleManager
{
  private final Yaml yaml;
  private HashMap<String, Module> modules = new HashMap<String, Module>();
  private HashMap<String, ModuleDescription> toLoad = new HashMap<String, ModuleDescription>();
  
public ModuleManager()
  {
	Main.loadedManagers++;
    Logger.println("Module manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
    this.modules = new LinkedHashMap<String, Module>();
    this.toLoad = new HashMap<String, ModuleDescription>();
    final Constructor yamlConstructor = new Constructor();
    final PropertyUtils propertyUtils = yamlConstructor.getPropertyUtils();
    propertyUtils.setSkipMissingProperties(true);
    yamlConstructor.setPropertyUtils(propertyUtils);
    this.yaml = new Yaml(yamlConstructor);
    
  }
  
  public Collection<Module> getModules()
  {
    return this.modules.values();
  }
  
  public Module getModule(String name)
  {
    return (Module)this.modules.get(name);
  }
  
  public void loadPlugins()
  {
	  final Map<ModuleDescription, Boolean> pluginStatuses = new HashMap<ModuleDescription, Boolean>();
    for (final Map.Entry<String, ModuleDescription> entry : this.toLoad.entrySet())
    {
    	final ModuleDescription plugin = (ModuleDescription)entry.getValue();
      if (!enablePlugin(pluginStatuses, new Stack<ModuleDescription>(), plugin)) {
       Logger.println("Failed to enable module " + entry.getKey(), LoggType.ERROR);
      }
    }
    this.toLoad.clear();
  }
  
  public void disablePlugins() {
	  for (Module module : this.modules.values()) {
		  Logger.println("Disabled module " + module.getDescription().getName() + ".", LoggType.INFO);
		  module.onDisable();
	  }
  }
  
  public void enablePlugins()
  {
    for (Module plugin : this.modules.values()) {
      try
      {
    	Logger.println("Enabled module " + plugin.getDescription().getName() + " version " + plugin.getDescription().getVersion(), LoggType.INFO);
        plugin.onEnable();
      }
      catch (Throwable t)
      {
        Logger.println("While enable " + plugin.getDescription().getName() + ". Error: " + t.getMessage(), LoggType.ERROR);
      }
    }
  }
  
  private boolean enablePlugin(Map<ModuleDescription, Boolean> pluginStatuses, Stack<ModuleDescription> dependStack, ModuleDescription plugin)
  {
    if (pluginStatuses.containsKey(plugin)) {
      return ((Boolean)pluginStatuses.get(plugin)).booleanValue();
    }
    Set<String> dependencies = new HashSet<String>();
    dependencies.addAll(plugin.getDepends());
    dependencies.addAll(plugin.getSoftDepends());
    
    boolean status = true;
    for (String dependName : dependencies)
    {
    	ModuleDescription depend = (ModuleDescription)this.toLoad.get(dependName);
      Boolean dependStatus = depend != null ? (Boolean)pluginStatuses.get(depend) : Boolean.FALSE;
      if (dependStatus == null) {
        if (dependStack.contains(depend))
        {
          StringBuilder dependencyGraph = new StringBuilder();
          for (ModuleDescription element : dependStack) {
            dependencyGraph.append(element.getName()).append(" -> ");
          }
          dependencyGraph.append(plugin.getName()).append(" -> ").append(dependName);
          Logger.println("Circular dependency detected: " + dependencyGraph, LoggType.INFO);
          status = false;
        }
        else
        {
          dependStack.push(plugin);
          dependStatus = Boolean.valueOf(enablePlugin(pluginStatuses, dependStack, depend));
          dependStack.pop();
        }
      }
      if ((dependStatus == Boolean.FALSE) && (plugin.getDepends().contains(dependName)))
      {
    	Logger.println(dependName + " (required by " + plugin.getName() + ") is unavailable", LoggType.ERROR);
        status = false;
      }
      if (!status) {
        break;
      }
    }
    if (status) {
      try
      {
        @SuppressWarnings("resource")
		Object loader = new ModuleClassLoader(new URL[] { plugin.getFile().toURI().toURL() });
        
        Class<?> main = ((URLClassLoader)loader).loadClass(plugin.getMain());
        Module clazz = (Module)main.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        
        clazz.init(plugin);
        this.modules.put(plugin.getName(), clazz);
        clazz.onLoad();
      }
      catch (Throwable t)
      {
    	Logger.println("While enabling module " + plugin.getName(), LoggType.ERROR);
      }
    }
    pluginStatuses.put(plugin, Boolean.valueOf(status));
    return status;
  }
  
  public void detectModules(File folder)
  {
      Preconditions.checkNotNull(folder, (Object)"folder");
      Preconditions.checkArgument(folder.isDirectory(), (Object)"Must load from a directory");
      if(folder.listFiles().length == 0) {
    	  Logger.println("No one module are detected!", LoggType.INFO);
      }
      for (final File file : folder.listFiles()) {
          if (file.isFile() && file.getName().endsWith(".jar")) {
              try (final JarFile jar = new JarFile(file)) {
                  JarEntry pdf = jar.getJarEntry("module.yml");
                  Preconditions.checkNotNull(pdf, (Object)"Module must have a module.yml");
                  try (final InputStream in = jar.getInputStream(pdf)) {
                      final ModuleDescription desc = this.yaml.loadAs(in, ModuleDescription.class);
                      desc.setFile(file);
                      this.toLoad.put(desc.getName(), desc);
                  }
              }
              catch (Exception ex) {
            	  Logger.println("While detecting module: " + file + ". Error: " + ex.getMessage(), LoggType.ERROR);
              }
          }
      }
}
}
