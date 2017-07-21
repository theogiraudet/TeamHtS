package fr.theogiraudet.HtS.LootTables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileExtractor {
	
	static void extractFile(String name, String folderPath) throws IOException, URISyntaxException
    {	
		String serverPath = LootTables.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().split("plugins")[0].split("/")[1];
		String path = serverPath + folderPath;
		createFolder(path);
		
        File target = new File(path + name.split("/")[1]);
        if (target.exists())
            return;

        FileOutputStream out = new FileOutputStream(target);
        ClassLoader cl = LootTables.class.getClassLoader();
        InputStream in = cl.getResourceAsStream(name);

        byte[] buf = new byte[8*1024];
        int len;
        while((len = in.read(buf)) != -1)
        {
            out.write(buf,0,len);
        }
        out.close();
        in.close();
    }
	
	private static void createFolder(String path) {
		if(!Files.exists(Paths.get(path))) { new File(path).mkdirs();}
	}
}
