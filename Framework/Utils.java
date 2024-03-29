package ETU2058.Framework;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Class> getClass(String packages)throws Exception{
        List<Class> listClass = new ArrayList<>();

        String path = packages.replaceAll("[.]", "\\\\");
        
        URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);
        File packDir =new File(packageUrl.toURI());
        File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
        List<Class> lists = new ArrayList<>();
        for(File f : inside){
            String c = packages+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
            lists.add(Class.forName(c));
        }
        return listClass;
    }
    
}