/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package annotation;

/**
 *
 * @author °°JUDICAEL°°
 */
public @interface ForeignKeyAnnotation {
    String referenceTable();
    String referenceColumn();
}
