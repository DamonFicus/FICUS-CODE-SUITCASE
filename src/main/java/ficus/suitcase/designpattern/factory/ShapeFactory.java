package ficus.suitcase.designpattern.factory;

import org.apache.commons.lang3.StringUtils;

public class ShapeFactory {

   public  Shape getShape(String shapeType){
       if(StringUtils.isEmpty(shapeType)){
           return null;
       }
       else if(shapeType.equalsIgnoreCase("circle")){
           return new Circle();
       }
       else if(shapeType.equalsIgnoreCase("rectangle")){
           return new Rectangle();
       }
       else if(shapeType.equalsIgnoreCase("square")){
           return new Square();
       }
       else{
           return null;
       }
   }
}
