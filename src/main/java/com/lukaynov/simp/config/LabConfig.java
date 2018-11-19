package com.lukaynov.simp.config;

import java.util.ArrayList;
import java.util.List;

import com.lukaynov.simp.model.ElementValue;
import com.lukaynov.simp.model.Logic1InputElement;
import com.lukaynov.simp.model.Logic2InputElement;
import com.lukaynov.simp.model.Logic3InputElement;
import com.lukaynov.simp.model.LogicElement;
import com.lukaynov.simp.model.XElement;

import static com.lukaynov.simp.util.Operation.AND;
import static com.lukaynov.simp.util.Operation.OR;
import static com.lukaynov.simp.util.Sign.NEGATIVE;
import static com.lukaynov.simp.util.Sign.POSITIVE;


public class LabConfig {

   private List<LogicElement> scheme = new ArrayList<>();

   public LabConfig() {
      XElement x1 = XElement.builder().name("x1").build();
      XElement x2 = XElement.builder().name("x2").build();
      XElement x3 = XElement.builder().name("x3").build();
      XElement x4 = XElement.builder().name("x4").build();
      XElement x5 = XElement.builder().name("x5").build();
      XElement x6 = XElement.builder().name("x6").build();
      XElement x7 = XElement.builder().name("x7").build();



      Logic2InputElement F1 = Logic2InputElement.builder().name("F1").firstInputElement(x1).secondInputElement(x2).operation(OR)
            .sign(NEGATIVE).build();
      Logic1InputElement F2 = Logic1InputElement.builder().name("F2").firstInputElement(x3)
            .sign(NEGATIVE).build();
      Logic2InputElement F3 = Logic2InputElement.builder().name("F3").firstInputElement(x5).secondInputElement(x6).operation(AND)
            .sign(POSITIVE).build();
      Logic3InputElement F4 = Logic3InputElement.builder().name("F4").firstInputElement(x4).secondInputElement(F3).thirdInputElement(x7).operation(OR)
            .sign(NEGATIVE).build();
      Logic2InputElement F5 = Logic2InputElement.builder().name("F5").firstInputElement(F2).secondInputElement(F4).operation(AND)
            .sign(NEGATIVE).build();
      Logic2InputElement F6 = Logic2InputElement.builder().name("F6").firstInputElement(F1).secondInputElement(F5).operation(OR)
            .sign(POSITIVE).build();


      F1.setNextElement(F6);
      F2.setNextElement(F5);
      F3.setNextElement(F4);
      F4.setNextElement(F5);
      F5.setNextElement(F6);

      scheme.add(F1);
      scheme.add(F2);
      scheme.add(F3);
      scheme.add(F4);
      scheme.add(F5);
      scheme.add(F6);

   }

   public List<List<ElementValue>> generateConditions(String name, boolean value) {
      return scheme.stream().filter(logicElement -> logicElement.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException())
            .executeStateFunc(value);
   }
}
