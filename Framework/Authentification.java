package ETU2058.Framework;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentification {
    String profil()default "";  
}
