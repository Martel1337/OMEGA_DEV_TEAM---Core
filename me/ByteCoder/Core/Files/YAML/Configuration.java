package me.ByteCoder.Core.Files.YAML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Configuration
{
  final Map<String, Object> self;
  private final Configuration defaults;
  
  public Configuration()
  {
    this(null);
  }
  
  Configuration(Map<String, Object> self, Configuration config)
  {
    this.self = self;
    this.defaults = config;
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public Configuration(Configuration defaults)
  {
	    this(new LinkedHashMap(), defaults);
  }
  
  @SuppressWarnings("unchecked")
private Configuration getSectionFor(String path)
  {
    int index = path.indexOf('.');
    if (index == -1) {
      return this;
    }
    String root = path.substring(0, index);
    Object section = this.self.get(root);
    if (section == null)
    {
      section = new LinkedHashMap<Object, Object>();
      this.self.put(root, section);
    }
    if ((section instanceof Configuration)) {
      return (Configuration)section;
    }
    return new Configuration((Map<String, Object>)section, this.defaults == null ? null : this.defaults.getSectionFor(path));
  }
  
  private String getChild(String path)
  {
    int index = path.indexOf('.');
    return index == -1 ? path : path.substring(index + 1);
  }
  
  @SuppressWarnings("unchecked")
public <T> T get(String path, T def)
  {
    Configuration section = getSectionFor(path);
    Object val;
    if (section == this) {
      val = this.self.get(path);
    } else {
      val = section.get(getChild(path), def);
    }
    return (T)(val != null ? val : def);
  }
  
  public Object get(String path)
  {
    return get(path, getDefault(path));
  }
  
  public Object getDefault(String path)
  {
    return this.defaults == null ? null : this.defaults.get(path);
  }
  
  public void set(String path, Object value)
  {
    Configuration section = getSectionFor(path);
    if (section == this)
    {
      if (value == null) {
        this.self.remove(path);
      } else {
        this.self.put(path, value);
      }
    }
    else {
      section.set(getChild(path), value);
    }
  }
  
  @SuppressWarnings("unchecked")
public Configuration getSection(String path)
  {
    Object def = getDefault(path);
    return new Configuration((Map<String, Object>)get(path, (def instanceof Map) ? def : Collections.EMPTY_MAP), this.defaults == null ? null : this.defaults.getSection(path));
  }
  
  public byte getByte(String path)
  {
    Object def = getDefault(path);
    return getByte(path, (byte)((def instanceof Number) ? ((Number)def).byteValue() : 0));
  }
  
  public byte getByte(String path, byte def)
  {
    Object val = get(path, Byte.valueOf(def));
    return (val instanceof Number) ? ((Number)val).byteValue() : def;
  }
  
  public List<Byte> getByteList(String path)
  {
    List<?> list = getList(path);
    List<Byte> result = new ArrayList<Byte>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Byte.valueOf(((Number)object).byteValue()));
      }
    }
    return result;
  }
  
  public short getShort(String path)
  {
    Object def = getDefault(path);
    return getShort(path, (short)((def instanceof Number) ? ((Number)def).shortValue() : 0));
  }
  
  public short getShort(String path, short def)
  {
    Object val = get(path, Short.valueOf(def));
    return (val instanceof Number) ? ((Number)val).shortValue() : def;
  }
  
  public List<Short> getShortList(String path)
  {
    List<?> list = getList(path);
    List<Short> result = new ArrayList<Short>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Short.valueOf(((Number)object).shortValue()));
      }
    }
    return result;
  }
  
  public int getInt(String path)
  {
    Object def = getDefault(path);
    return getInt(path, (def instanceof Number) ? ((Number)def).intValue() : 0);
  }
  
  public int getInt(String path, int def)
  {
    Object val = get(path, Integer.valueOf(def));
    return (val instanceof Number) ? ((Number)val).intValue() : def;
  }
  
  public List<Integer> getIntList(String path)
  {
    List<?> list = getList(path);
    List<Integer> result = new ArrayList<Integer>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Integer.valueOf(((Number)object).intValue()));
      }
    }
    return result;
  }
  
  public long getLong(String path)
  {
    Object def = getDefault(path);
    return getLong(path, (def instanceof Number) ? ((Number)def).longValue() : 0L);
  }
  
  public long getLong(String path, long def)
  {
    Object val = get(path, Long.valueOf(def));
    return (val instanceof Number) ? ((Number)val).longValue() : def;
  }
  
  public List<Long> getLongList(String path)
  {
    List<?> list = getList(path);
    List<Long> result = new ArrayList<Long>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Long.valueOf(((Number)object).longValue()));
      }
    }
    return result;
  }
  
  public float getFloat(String path)
  {
    Object def = getDefault(path);
    return getFloat(path, (def instanceof Number) ? ((Number)def).floatValue() : 0.0F);
  }
  
  public float getFloat(String path, float def)
  {
    Object val = get(path, Float.valueOf(def));
    return (val instanceof Number) ? ((Number)val).floatValue() : def;
  }
  
  public List<Float> getFloatList(String path)
  {
    List<?> list = getList(path);
    List<Float> result = new ArrayList<Float>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Float.valueOf(((Number)object).floatValue()));
      }
    }
    return result;
  }
  
  public double getDouble(String path)
  {
    Object def = getDefault(path);
    return getDouble(path, (def instanceof Number) ? ((Number)def).doubleValue() : 0.0D);
  }
  
  public double getDouble(String path, double def)
  {
    Object val = get(path, Double.valueOf(def));
    return (val instanceof Number) ? ((Number)val).doubleValue() : def;
  }
  
  public List<Double> getDoubleList(String path)
  {
    List<?> list = getList(path);
    List<Double> result = new ArrayList<Double>();
    for (Object object : list) {
      if ((object instanceof Number)) {
        result.add(Double.valueOf(((Number)object).doubleValue()));
      }
    }
    return result;
  }
  
  public boolean getBoolean(String path)
  {
    Object def = getDefault(path);
    return getBoolean(path, (def instanceof Boolean) ? ((Boolean)def).booleanValue() : false);
  }
  
  public boolean getBoolean(String path, boolean def)
  {
    Object val = get(path, Boolean.valueOf(def));
    return (val instanceof Boolean) ? ((Boolean)val).booleanValue() : def;
  }
  
  public List<Boolean> getBooleanList(String path)
  {
    List<?> list = getList(path);
    List<Boolean> result = new ArrayList<Boolean>();
    for (Object object : list) {
      if ((object instanceof Boolean)) {
        result.add((Boolean)object);
      }
    }
    return result;
  }
  
  public char getChar(String path)
  {
    Object def = getDefault(path);
    return getChar(path, (def instanceof Character) ? ((Character)def).charValue() : '\000');
  }
  
  public char getChar(String path, char def)
  {
    Object val = get(path, Character.valueOf(def));
    return (val instanceof Character) ? ((Character)val).charValue() : def;
  }
  
  public List<Character> getCharList(String path)
  {
    List<?> list = getList(path);
    List<Character> result = new ArrayList<Character>();
    for (Object object : list) {
      if ((object instanceof Character)) {
        result.add((Character)object);
      }
    }
    return result;
  }
  
  public String getString(String path)
  {
    Object def = getDefault(path);
    return getString(path, (def instanceof String) ? (String)def : "");
  }
  
  public String getString(String path, String def)
  {
    Object val = get(path, def);
    return (val instanceof String) ? (String)val : def;
  }
  
  public List<String> getStringList(String path)
  {
    List<?> list = getList(path);
    List<String> result = new ArrayList<String>();
    for (Object object : list) {
      if ((object instanceof String)) {
        result.add((String)object);
      }
    }
    return result;
  }
  
  public List<?> getList(String path)
  {
    Object def = getDefault(path);
    return getList(path, (def instanceof List) ? (List<?>)def : Collections.EMPTY_LIST);
  }
  
  public List<?> getList(String path, List<?> def)
  {
    Object val = get(path, def);
    return (val instanceof List) ? (List<?>)val : def;
  }
}
