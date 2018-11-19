package com.lukaynov.simp.model;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ElementValue {
   String name;
   boolean value;


   @Override
   public String toString() {
      return name + " " + (value ? 1 : 0);
   }
}
