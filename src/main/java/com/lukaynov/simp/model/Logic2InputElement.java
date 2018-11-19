package com.lukaynov.simp.model;

import java.util.ArrayList;
import java.util.List;

import com.lukaynov.simp.util.Operation;
import com.lukaynov.simp.util.Sign;

import static com.lukaynov.simp.util.Conjunction.conj;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Logic2InputElement implements LogicElement {

   private String name;

   private Element firstInputElement;
   private Element secondInputElement;

   private LogicElement nextElement;

   private Sign sign = Sign.POSITIVE;

   private Operation operation = Operation.AND;

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

   public List<List<ElementValue>> executeStateFuncNested(boolean i) {

      List<List<ElementValue>> leftElements;
      List<List<ElementValue>> rightElements;

      List<List<ElementValue>> resultElements = new ArrayList<>();
      if (sign == Sign.NEGATIVE) {
         i = !i;
      }

      if (operation == Operation.AND) {
         if (i) {
            leftElements = firstInputElement.executeStateFuncNested(true);
            rightElements = secondInputElement.executeStateFuncNested(true);
            resultElements.addAll(conj(leftElements, rightElements));
         }
         else {
            leftElements = firstInputElement.executeStateFuncNested(true);
            rightElements = secondInputElement.executeStateFuncNested(false);
            resultElements.addAll(conj(leftElements, rightElements));

            leftElements = firstInputElement.executeStateFuncNested(false);
            rightElements = secondInputElement.executeStateFuncNested(true);
            resultElements.addAll(conj(leftElements, rightElements));

            leftElements = firstInputElement.executeStateFuncNested(false);
            rightElements = secondInputElement.executeStateFuncNested(false);
            resultElements.addAll(conj(leftElements, rightElements));

         }
      }
      else {
         if (i) {
            leftElements = firstInputElement.executeStateFuncNested(true);
            rightElements = secondInputElement.executeStateFuncNested(true);
            resultElements.addAll(conj(leftElements, rightElements));

            leftElements = firstInputElement.executeStateFuncNested(true);
            rightElements = secondInputElement.executeStateFuncNested(false);
            resultElements.addAll(conj(leftElements, rightElements));

            leftElements = firstInputElement.executeStateFuncNested(false);
            rightElements = secondInputElement.executeStateFuncNested(true);
            resultElements.addAll(conj(leftElements, rightElements));

         }
         else {
            leftElements = firstInputElement.executeStateFuncNested(false);
            rightElements = secondInputElement.executeStateFuncNested(false);
            resultElements.addAll(conj(leftElements, rightElements));

         }
      }

      return resultElements;

   }

   public List<List<ElementValue>> searchActivePath(Element fromElement, List<List<ElementValue>> combinations) {
      List<List<ElementValue>> resultElements = new ArrayList<>();
      List<List<ElementValue>> nestedResultsElements = new ArrayList<>();

      if (fromElement.equals(firstInputElement)) {
         if (operation == Operation.AND) {
            nestedResultsElements = secondInputElement.executeStateFuncNested(true);
         }
         else {
            nestedResultsElements = secondInputElement.executeStateFuncNested(false);
         }
      }
      else {
         if (operation == Operation.AND) {
            nestedResultsElements = firstInputElement.executeStateFuncNested(true);
         }
         else {
            nestedResultsElements = firstInputElement.executeStateFuncNested(false);
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
