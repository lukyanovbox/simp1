package com.lukaynov.simp.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ElementValue {
   String name;
   boolean value;


   public int getIntValue() {
      return value ? 1 : 0;
   }

   @Override
   public String toString() {
      return name + " " + (value ? 1 : 0);
   }
}
