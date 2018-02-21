package me.ByteCoder.Core.Files.YAML;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration
  extends ConfigurationProvider
{
  private final ThreadLocal<Object> yaml = new ThreadLocal<Object>()
  {
    protected Yaml initialValue()
    {
      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
      return new Yaml(options);
    }
  };
  
  public void save(Configuration config, File file)
    throws IOException
  {
    FileWriter writer = new FileWriter(file);Throwable localThrowable3 = null;
    try
    {
      save(config, writer);
    }
    catch (Throwable localThrowable1)
    {
      localThrowable3 = localThrowable1;throw localThrowable1;
    }
    finally
    {
      if (writer != null) {
        if (localThrowable3 != null) {
          try
          {
            writer.close();
          }
          catch (Throwable localThrowable2)
          {
            localThrowable3.addSuppressed(localThrowable2);
          }
        } else {
          writer.close();
        }
      }
    }
  }
  
  public void save(Configuration config, Writer writer)
  {
    ((Yaml)this.yaml.get()).dump(config.self, writer);
  }
  
  public Configuration load(File file)
    throws IOException
  {
    return load(file, null);
  }
  
  public Configuration load(File file, Configuration defaults)
    throws IOException
  {
    FileReader reader = new FileReader(file);Throwable localThrowable3 = null;
    try
    {
      return load(reader, defaults);
    }
    catch (Throwable localThrowable4)
    {
      localThrowable3 = localThrowable4;throw localThrowable4;
    }
    finally
    {
      if (reader != null) {
        if (localThrowable3 != null) {
          try
          {
            reader.close();
          }
          catch (Throwable localThrowable2)
          {
            localThrowable3.addSuppressed(localThrowable2);
          }
        } else {
          reader.close();
        }
      }
    }
  }
  
  public Configuration load(Reader reader)
  {
    return load(reader, null);
  }
  
  @SuppressWarnings("unchecked")
public Configuration load(Reader reader, Configuration defaults)
  {
    Map<String, Object> map = (Map<String, Object>)((Yaml)this.yaml.get()).loadAs(reader, LinkedHashMap.class);
    if (map == null) {
      map = new LinkedHashMap<String, Object>();
    }
    return new Configuration(map, defaults);
  }
  
  public Configuration load(InputStream is)
  {
    return load(is, null);
  }
  
  @SuppressWarnings("unchecked")
public Configuration load(InputStream is, Configuration defaults)
  {
    Map<String, Object> map = (Map<String, Object>)((Yaml)this.yaml.get()).loadAs(is, LinkedHashMap.class);
    if (map == null) {
      map = new LinkedHashMap<String, Object>();
    }
    return new Configuration(map, defaults);
  }
  
  public Configuration load(String string)
  {
    return load(string, null);
  }
  
  @SuppressWarnings("unchecked")
public Configuration load(String string, Configuration defaults)
  {
    Map<String, Object> map = (Map<String, Object>)((Yaml)this.yaml.get()).loadAs(string, LinkedHashMap.class);
    if (map == null) {
      map = new LinkedHashMap<String, Object>();
    }
    return new Configuration(map, defaults);
  }
}
