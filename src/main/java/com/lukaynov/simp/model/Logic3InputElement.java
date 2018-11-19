package com.lukaynov.simp.model;

import java.util.ArrayList;
import java.util.List;

import com.lukaynov.simp.util.Operation;
import com.lukaynov.simp.util.Sign;

import static com.google.common.collect.ImmutableList.of;
import static com.lukaynov.simp.util.Conjunction.conj;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Logic3InputElement implements LogicElement {

   private String name;

   private Element firstInputElement;
   private Element secondInputElement;
   private Element thirdInputElement;


   private LogicElement nextElement;

   private Sign sign = Sign.POSITIVE;

   private Operation operation = Operation.AND;

   @Override
   public List<List<ElementValue>> executeStateFunc(boolean i) {
      i = !i;
      List<List<ElementValue>> resultElements = executeStateFuncNested(i);

      if (nextElement != null) {
         return nextElement.searchActivePath(this, resultElements);
      }
      else {
         return resultElements;
      }
   }

   @Override
   public List<List<ElementValue>> executeStateFuncNested(boolean i) {

      List<List<ElementValue>> firstInputElements;
      List<List<ElementValue>> secondInputElements;
      List<List<ElementValue>> thirdInputElements;

      List<List<ElementValue>> resultElements = new ArrayList<>();

      if (sign == Sign.NEGATIVE) {
         i = !i;
      }

      if (operation == Operation.AND) {
         if (i) {
            firstInputElements = firstInputElement.executeStateFuncNested(true);
            secondInputElements = secondInputElement.executeStateFuncNested(true);
            thirdInputElements = thirdInputElement.executeStateFuncNested(true);
            resultElements.addAll(conj(firstInputElements, secondInputElements, thirdInputElements));
         }
         else {
            List<List<Boolean>> combinations = of(
                  of(false, false, false),
                  of(false, false, true),
                  of(false, true, false),
                  of(false, true, true),
                  of(true, false, false),
                  of(true, false, true),
                  of(true, true, false));

            for (List<Boolean> combination : combinations) {
               firstInputElements = firstInputElement.executeStateFuncNested(combination.get(0));
               secondInputElements = secondInputElement.executeStateFuncNested(combination.get(1));
               thirdInputElements = thirdInputElement.executeStateFuncNested(combination.get(2));
               resultElements.addAll(conj(firstInputElements, secondInputElements, thirdInputElements));
            }
         }
      }
      else {
         if (i) {
            List<List<Boolean>> combinations = of(
                  of(false, false, true),
                  of(false, true, false),
                  of(false, true, true),
                  of(true, false, false),
                  of(true, false, true),
                  of(true, true, false),
                  of(true, true, true));

            for (List<Boolean> combination : combinations) {
               firstInputElements = firstInputElement.executeStateFuncNested(combination.get(0));
               secondInputElements = secondInputElement.executeStateFuncNested(combination.get(1));
               thirdInputElements = thirdInputElement.executeStateFuncNested(combination.get(2));
               resultElements.addAll(conj(firstInputElements, secondInputElements, thirdInputElements));
            }
         }
         else {
            firstInputElements = firstInputElement.executeStateFuncNested(false);
            secondInputElements = secondInputElement.executeStateFuncNested(false);
            thirdInputElements = thirdInputElement.executeStateFuncNested(false);
            resultElements.addAll(conj(firstInputElements, secondInputElements, thirdInputElements));
         }
      }

      return resultElements;
   }

   @Override
   public List<List<ElementValue>> searchActivePath(final Element fromElement, final List<List<ElementValue>> combinations) {
      List<List<ElementValue>> resultElements = new ArrayList<>();
      List<List<ElementValue>> nestedResultsElements = new ArrayList<>();

      if (fromElement.equals(firstInputElement)) {
         if (operation == Operation.AND) {
            nestedResultsElements = conj(
                  secondInputElement.executeStateFuncNested(true),
                  thirdInputElement.executeStateFuncNested(true));
         }
         else {
            nestedResultsElements = conj(
                  secondInputElement.executeStateFuncNested(false),
                  thirdInputElement.executeStateFuncNested(false));
         }
      }
      else if(fromElement.equals(secondInputElement)){
         if (operation == Operation.AND) {
            nestedResultsElements = conj(
                  firstInputElement.executeStateFuncNested(true),
                  thirdInputElement.executeStateFuncNested(true));
         }
         else {
            nestedResultsElements = conj(
                  firstInputElement.executeStateFuncNested(false),
                  thirdInputElement.executeStateFuncNested(false));
         }
      } else {
         if (operation == Operation.AND) {
            nestedResultsElements = conj(
                  firstInputElement.executeStateFuncNested(true),
                  secondInputElement.executeStateFuncNested(true));
         }
         else {
            nestedResultsElements = conj(
                  firstInputElement.executeStateFuncNested(false),
                  secondInputElement.executeStateFuncNested(false));
         }
      }
      resultElements = conj(nestedResultsElements, combinations);

      if (nextElement == null) {
         return resultElements;
      }
      else {
         return nextElement.searchActivePath(this, resultElements);
      }
   }
}
