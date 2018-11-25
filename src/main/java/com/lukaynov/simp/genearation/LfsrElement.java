package com.lukaynov.simp.genearation;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class LfsrElement {
   InputName name;
   boolean state;
   LfsrElement next;


   Integer getIntState() {
      return state ? 1 : 0;
   }


   void updateStateBounded(boolean newState){
      if(next != null){
         next.updateStateBounded(state);
      }
      this.state = newState;
   }

}
