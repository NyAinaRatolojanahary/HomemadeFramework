package ETU2058.Framework.servlet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;


import jakarta.servlet.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import ETU2058.Framework.Annotation;
import ETU2058.Framework.Mapping;
import ETU2058.Framework.Utils;
import ETU2058.Framework.Parametre;
import ETU2058.Framework.ModelView;
import ETU2058.Framework.FileUploader;


@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> mappingUrls = new HashMap<String,Mapping>();
    public void init() {
        String packageName = "test_framework.Test";
        try {
            List<Class> allClass = Outil.getClass(packageName);
            for (int i = 0; i < allClass.size(); i++) {
                Class temp = allClass.get(i);
                Method[] methods = temp.getDeclaredMethods();
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].isAnnotationPresent(Annotation.class)) {
                        Mapping mapping = new Mapping(temp.getName(),methods[j].getName());
                        this.mappingUrls.put(methods[j].getAnnotation(Annotation.class).url(), mapping);
                    }
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    // File traitement part
    private FileUploader fileTraitement(Collection<Part> files, Field field) {
        FileUploader file = new FileUploader();
        String name = field.getName();
        boolean exists = false;
        String filename = null;
        Part filepart = null;
        for (Part part : files) {
            if (part.getName().equals(name)) {
                filepart = part;
                exists = true;
                break;
            }
        }
        try (InputStream io = filepart.getInputStream()) {
            ByteArrayOutputStream buffers = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int) filepart.getSize()];
            int read;
            while ((read = io.read(buffer, 0, buffer.length)) != -1) {
                buffers.write(buffer, 0, read);
            }
            file.setNom(this.getFileName(filepart));
            file.setBytes(buffers.toByteArray());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    //
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        for (String partStr : parts) {
            if (partStr.trim().startsWith("filename"))
                return partStr.substring(partStr.indexOf('=') + 1).trim().replace("\"", "");
        }
        return null;
    }


        public void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            try {
            String url = request.getRequestURI().substring(request.getContextPath().length()+1);
            // out.println(this.mappingUrls);
            if (this.mappingUrls.containsKey(url)){
                Mapping mapping = this.mappingUrls.get(url);
                out.println(url);
                Class clazz = Class.forName(mapping.getClassName());
                Object object = clazz.getConstructor().newInstance();
                Field[] input = clazz.getDeclaredFields();
                Method[] methods = clazz.getDeclaredMethods();
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
                                // throw new Exception("eto "+resultToString[0]);
                            }
                        }
                    }
                }

                Method equalMethod = null;
                out.println(methods);
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].getName().trim().compareTo(mapping.getMethod())==0) {
                        equalMethod = methods[i];
                        break;
                    }
                }

                // out.print(equalMethod.getName() + "Methode");
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


                try {
                    Collection<Part> files = request.getParts();
                    for (Field f : input) {
                        if (f.getType() == etu1922.framework.FileUploader.class) {
                            String s1 = f.getName().substring(0, 1).toUpperCase();
                            String seter = s1 + f.getName().substring(1);
                            Method m = clazz.getMethod("set" + seter, f.getType());
                            Object o = this.fileTraitement(files, f);
                            m.invoke(object, o);
                        }
                    }
                } catch (Exception e) {
                    
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
        }catch (Exception e) {e.printStackTrace(out);}
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