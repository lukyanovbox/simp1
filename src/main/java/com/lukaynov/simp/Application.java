package com.lukaynov.simp;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.lukaynov.simp.config.ExampleConfig;
import com.lukaynov.simp.model.ElementValue;


public class Application {

   public static void main(String[] args) {
      ExampleConfig exampleConfig = new ExampleConfig();


      List<List<ElementValue>> result = exampleConfig.generateConditions("F2", true);


      System.out.println(result.get(0).stream()
            .sorted(Comparator.comparing(ElementValue::getName))
            .map(ElementValue::getName).collect(Collectors.joining("| ")));
//      System.out.println();
      result.forEach(line ->
            System.out.println(line.stream()
                  .sorted(Comparator.comparing(ElementValue::getName))
                  .map(ElementValue::getValue).map(Object::toString)
                  .collect(Collectors.joining(" | "))));
   }
}