/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ETU2058.Model;

import ETU2058.Framework.Servlet.Url;

/**
 *
 * @author ASUS
 */
public class Employe {
    
    String name;
    String firstName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    
    @Url(name="Emp-findEmpByName")
    public String findEmpByName(String name){
        System.out.println("It's me "+ name);
        return name;
    }
    
}
