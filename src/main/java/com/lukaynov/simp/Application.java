package com.lukaynov.simp;

import java.util.List;
import java.util.stream.Collectors;

import com.lukaynov.simp.model.ElementValue;
import com.lukaynov.simp.model.Logic2InputElement;
import com.lukaynov.simp.model.XElement;

import static com.lukaynov.simp.util.Operation.AND;
import static com.lukaynov.simp.util.Operation.OR;
import static com.lukaynov.simp.util.Sign.NEGATIVE;
import static com.lukaynov.simp.util.Sign.POSITIVE;


public class Application {

   public static void main(String[] args) {

      XElement x1 = XElement.builder().name("x1").build();
      XElement x2 = XElement.builder().name("x2").build();
      XElement x3 = XElement.builder().name("x3").build();
      XElement x4 = XElement.builder().name("x4").build();
      XElement x5 = XElement.builder().name("x5").build();
      XElement x6 = XElement.builder().name("x6").build();


      Logic2InputElement F1 = Logic2InputElement.builder().firstInputElement(x1).secondInputElement(x2).operation(AND).sign(
            POSITIVE).build();
      Logic2InputElement F2 = Logic2InputElement.builder().firstInputElement(x4).secondInputElement(x5).operation(OR).sign(
            NEGATIVE).build();
      Logic2InputElement F3 = Logic2InputElement.builder().firstInputElement(F2).secondInputElement(x6).operation(AND).sign(
            NEGATIVE).build();
      Logic2InputElement F4 = Logic2InputElement.builder().firstInputElement(x3).secondInputElement(F3).operation(AND).sign(
            POSITIVE).build();
      Logic2InputElement F5 = Logic2InputElement.builder().firstInputElement(F1).secondInputElement(F4).operation(OR).sign(
            POSITIVE).build();


      F1.setNextElement(F5);
      F2.setNextElement(F3);
      F3.setNextElement(F4);
      F4.setNextElement(F5);


      List<List<ElementValue>> result = F3.executeStateFunc(true);

      result.forEach(line ->
            System.out.println(line.stream().map(Object::toString).collect(Collectors.joining(", "))));
   }
}