package me.ByteCoder.Core.Files.JSON;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

import java.lang.reflect.Type;

public class JConfiguration {

private File path;
private Writer writer;
private Gson gson;
private JsonWriter jwriter;

public JConfiguration(File f){
	this.gson = new Gson();
	this.path = f;
}

public File getFile(){
	return this.path;
}

public void write(Object o) {
	try (FileWriter writer = new FileWriter(path.getName())) {

       this.gson.toJson(o, writer);

    } catch (IOException e) {
        e.printStackTrace();
    }
}

public <T> Object read(Class<T> c) {
	Object o = null;
	try (Reader reader = new FileReader(this.path.toPath().toString())) {
		
        o = gson.fromJson(reader, c);

    } catch (IOException e) {
        e.printStackTrace();
    }
	return o;

}
}
