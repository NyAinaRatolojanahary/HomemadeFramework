/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ETU2058.Framework;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 *
 * @author ASUS
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation {

    String url() default "";
 


}