/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ETU2058.Framework.Servlet;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author ASUS
 */
public class AnnotationFunction {
    
    public static ArrayList<Class<?>> getModelClasses(String package_name){
		
		ArrayList<Class<?>> classes= new ArrayList<>();
		try {
			ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
			Enumeration<URL> resources= classLoader.getResources(package_name);
			while(resources.hasMoreElements()) {
				URL res= resources.nextElement();
				if(res.getProtocol().equals("file")) {
					File packageDir= new File(res.toURI());
					for(File file : packageDir.listFiles()) {
						String filename= file.getName();
						if(filename.endsWith(".class")) {
							String className= filename.substring(0, filename.length()-6);
                                                        System.out.println(className);
							Class<?> c= Class.forName(package_name + "." + className);
							classes.add(c);
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return classes;
	}
    
}
