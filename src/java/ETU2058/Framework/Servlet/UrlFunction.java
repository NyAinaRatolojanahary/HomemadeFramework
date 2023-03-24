/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ETU2058.Framework.Servlet;

import ETU2058.Mapping.Mapping;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ASUS
 */
public class UrlFunction {
    
    public  static HashMap<String,Mapping> fonction()
    {
       HashMap<String,Mapping> UrlMapping=new HashMap<>();
       ArrayList<Class<?>>li= AnnotationFunction.getModelClasses("test");
         System.out.println(li.size());
       for(int i =0;i<li.size();i++)
       {
         
           Method[] m=li.get(i).getMethods();
            for(int j=0;j<m.length;i++)
            {
                System.out.println("huhu");
               if(m[j].isAnnotationPresent(Url.class))
               {
                                  
                   Url u=m[j].getAnnotation(Url.class);
                   UrlMapping.put(u.name(),new Mapping(li.get(i).getName(),m[j].getName()));
                    System.out.println("haha");
               }
            }
       }
        return UrlMapping;
    }
    
}
