package com.lukaynov.simp.model;

import java.util.List;


public interface LogicElement extends Element {

   List<List<ElementValue>> executeStateFunc(boolean i);
   List<List<ElementValue>> searchActivePath(Element fromElement, List<List<ElementValue>> combinations);

}
