package ETU2058.Framework.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.lang.reflect.Parameter;

import ETU2058.Framework.Annotation;
import ETU2058.Framework.Mapping;
import ETU2058.Framework.Utils;
import ETU2058.Framework.ModelView;
import ETU2058.Framework.Parametre;

public class FrontServlet extends HttpServlet{
        HashMap<String,Mapping> mappingUrls = new HashMap<String,Mapping>();

        public void init() {

            String packageName = "Test.Models";
            
            try {
                List<Class> allClass = Utils.getClass(packageName);
                for (int i = 0; i < allClass.size(); i++) {
                    Class temp = allClass.get(i);
                    Method[] methods = temp.getDeclaredMethods();
                    for (int j = 0; j < methods.length; j++) {
                        if (methods[j].isAnnotationPresent(Annotation.class)) {
                            Mapping mapping = new Mapping(temp.getName(),methods[j].getName());
                            this.mappingUrls.put(methods[j].getAnnotation(Annotation.class).url(),mapping);
                        }
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
        public void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {      
                PrintWriter out = response.getWriter();
                try {
                    String url = request.getRequestURI().substring(request.getContextPath().length()+1);
                    out.println(this.mappingUrls);
                    out.println("Sababa");
                    if (this.mappingUrls.containsKey(url))
                    {
                        Mapping mapping = this.mappingUrls.get(url);
                        Class clazz = Class.forName(mapping.getClassName());
                        Object object = clazz.getConstructor().newInstance();
                        Method[] methods = object.getClass().getDeclaredMethods();
                        Field[] input = clazz.getDeclaredFields();
                        Enumeration<String> nom = request.getParameterNames();
                        List<String> list = Collections.list(nom);
                        for (int k = 0; k < input.length; k++) {    
                            String table = input[k].getName() + ((input[k].getType().isArray()) ? "[]" : "");
                            for (int j = 0; j < list.size(); j++) {
                                if (input[k].getName().trim().equals(list.get(j).trim())) {
                                    String s1 = list.get(j).substring(0, 1).toUpperCase();
                                    String seter = s1 + list.get(j).substring(1);
                                    Method me = clazz.getMethod("set" + seter, input[k].getType() );
                                    if (input[k].getType().isArray()==false) {
                                        String object2 = request.getParameter(input[k].getName());
                                        if (input[k].getType() == java.util.Date.class) {
                                            SimpleDateFormat newF = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                                            Date newD = newF.parse(object2);
                                            me.invoke(object, newD);
                                        }else if (input[k].getType() == java.sql.Date.class) {
                                            java.sql.Date obj = java.sql.Date.valueOf(object2);
                                            me.invoke(object, obj);
                                        }else {
                                            Object obj = input[k].getType().getConstructor(String.class).newInstance(object2);
                                            me.invoke(object, obj);
                                        }    
                                    } else{
                                        String[] resultToString = request.getParameterValues(table);
                                        me.invoke(object,(Object) resultToString);
                                        throw new Exception("eto "+resultToString[0]);
                                    }
                                }
                            }
                        }

                        Method equalMethod = null;
                        for (int i = 0; i < methods.length; i++) {
                            if (methods[i].getName().trim().compareTo(mapping.getMethod())==0) {
                                equalMethod = methods[i];
                                break;
                            }
                        }
                        
                        out.print(equalMethod.getName() + "Methode");
                        Parameter[] p = equalMethod.getParameters();
                        Object[] params = new Object[p.length];
                        for (int i = 0; i < p.length; i++) {
                            if (p[i].isAnnotationPresent(Parametre.class)) {
                                Parametre annotation = p[i].getAnnotation(Parametre.class);
                                String temp = p[i].getAnnotation(Parametre.class).param() + ((p[i].getType().isArray()) ? "[]" : "");
                                for (int j = 0; j < list.size(); j++) {
                                    if (temp.trim().equals(list.get(j).trim())) {
                                        if (p[i].getType().isArray()==false) {
                                            String object2 = request.getParameter(temp);
                                            if (p[i].getType() == java.util.Date.class) {
                                                SimpleDateFormat newF = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                                                Date newD = newF.parse(object2);
                                                params[i] = newD;
                                            }else if (p[i].getType() == java.sql.Date.class) {
                                                java.sql.Date obj = java.sql.Date.valueOf(object2);
                                                params[i] = obj;
                                            }else {
                                                Object obj = p[i].getType().getConstructor(String.class).newInstance(object2);
                                                params[i] = obj;
                                            }    
                                        } else{
                                            String[] resultToString = request.getParameterValues(temp);
                                            params[i] = resultToString;
                                        }
                                    }
                                }
                            }
                        }
                        Object returnObject = equalMethod.invoke(object, params);

                        if (returnObject instanceof ModelView) {
                            ModelView modelview = (ModelView) returnObject;
                            HashMap<String,Object> data = modelview.getData();
                            for(Map.Entry<String,Object> o : data.entrySet()){
                                request.setAttribute(o.getKey(),o.getValue());
                            }  
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(modelview.getView());
                            requestDispatcher.forward(request, response);
                        }
                    }
                }   
                catch (Exception e) {e.printStackTrace(out);}

        }

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            processRequest(request, response);
        }

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            processRequest(request, response);
        }
}
