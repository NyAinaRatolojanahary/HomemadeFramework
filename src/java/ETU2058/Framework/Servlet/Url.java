/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ETU2058.Framework.Servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author ASUS
 */

@Target({ElementType.TYPE , ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface Url {
    String name() default"";
}
