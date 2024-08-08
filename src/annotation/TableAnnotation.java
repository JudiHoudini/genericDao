/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author °°JUDICAEL°°
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Cible les classes
public @interface TableAnnotation {
    String nom();
}
