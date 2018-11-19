package com.lukaynov.simp.model;

import java.util.ArrayList;
import java.util.List;

import com.lukaynov.simp.util.Sign;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Logic1InputElement implements LogicElement {

   private String name;

   private Element firstInputElement;

   private LogicElement nextElement;

   private Sign sign = Sign.POSITIVE;

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
      List<List<ElementValue>> resultElements = new ArrayList<>();
      if (sign == Sign.NEGATIVE) {
         i = !i;
      }

      return firstInputElement.executeStateFuncNested(i);
   }


   @Override
   public List<List<ElementValue>> searchActivePath(final Element fromElement, final List<List<ElementValue>> combinations) {
      if (nextElement == null) {
         return combinations;
      }
      else {
         return nextElement.searchActivePath(this, combinations);
      }
   }
}
