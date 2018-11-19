package com.lukaynov.simp.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class XElement implements Element {

   String name;

   @Override
   public List<List<ElementValue>> executeStateFuncNested(final boolean i) {
      List<List<ElementValue>> result = new ArrayList<>();

      List<ElementValue> nestedList =new ArrayList<>();
      nestedList.add(new ElementValue(name, i));
      result.add(nestedList);

      return result;
   }
}
