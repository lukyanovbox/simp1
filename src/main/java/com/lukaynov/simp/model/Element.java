package com.lukaynov.simp.model;

import java.util.List;


public interface Element {

   List<List<ElementValue>> executeStateFuncNested(boolean i);
}
