package ETU2058.Framework.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import ETU2058.Framework.Annotation;
import ETU2058.Framework.Mapping;
import ETU2058.Framework.Utils;

public class FrontServlet extends HttpServlet {
        HashMap<String,Mapping> mappingUrls;

        public void init() {
            String packageName = "Test";
            try {
                List<Class> allClass = Outil.getClass(packageName);
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
            try (PrintWriter out = response.getWriter()) {
                out.println(" ok ");
            }
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