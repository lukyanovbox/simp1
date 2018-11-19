package com.lukaynov.simp.util;

import java.util.ArrayList;
import java.util.List;

import com.lukaynov.simp.model.ElementValue;


public class Conjunction {

   public static List<List<ElementValue>> conj(List<List<ElementValue>> left, List<List<ElementValue>> right) {
      List<List<ElementValue>> result = new ArrayList<>();

      for (int i = 0; i < left.size(); i++) {
         for (int j = 0; j < right.size(); j++) {
            List<ElementValue> singleResult = new ArrayList<>();
            singleResult.addAll(left.get(i));
            singleResult.addAll(right.get(j));

            result.add(singleResult);
         }
      }

      return result;
   }
}
