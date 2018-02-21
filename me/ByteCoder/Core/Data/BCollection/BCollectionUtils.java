package me.ByteCoder.Core.Data.BCollection;

import java.io.File;

import me.ByteCoder.Core.Files.JSON.JConfiguration;
import me.ByteCoder.Core.Files.YAML.YConfiguration;

public class BCollectionUtils {

public static void saveCollectionAsDataFile(File f, BCollection bc) {
	if(bc.getType().equals(BCollection.BCollectionType.YAML)) {
		YConfiguration cfg = new YConfiguration(f);
		
		for(BField fields : bc.getValues()) {
			for(String keys : fields.getKeys()) {
				cfg.getConfiguration().set(bc.getName() + "." + fields.getFieldName() + "." + keys, fields.getData(keys));
			}
		}
		
		cfg.save();
	}else if(bc.getType().equals(BCollection.BCollectionType.JSON)) {
		JConfiguration cfg = new JConfiguration(f);
		
		cfg.write(bc);
	}
}

public static BCollection loadCollectionFromFile(File f, BCollection.BCollectionType type) {
	BCollection collection = null;
	
	if(type.equals(BCollection.BCollectionType.YAML)) {
		YConfiguration cfg = new YConfiguration(f);
		
		for(String str : cfg.getConfiguration().getStringList("Fields")) {
			BField field = new BField(str);
			
			cfg.getConfiguration().getSection(str);
		}
	}else if(type.equals(BCollection.BCollectionType.JSON)) {
		JConfiguration cfg = new JConfiguration(f);
		
		collection = (BCollection) cfg.read(BCollection.class);
	}
	return collection;
}
}
