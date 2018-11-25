package com.lukaynov.simp;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.lukaynov.simp.config.LabConfig;
import com.lukaynov.simp.genearation.Lfsr;

import static com.google.common.collect.ImmutableList.of;
import static com.lukaynov.simp.genearation.InputName.X2;
import static com.lukaynov.simp.genearation.InputName.X3;
import static com.lukaynov.simp.genearation.InputName.X5;
import static com.lukaynov.simp.genearation.InputName.X8;


public class Application {

   public static void main(String[] args) {
      LabConfig labConfig = new LabConfig();

      Set<List<Boolean>> result = labConfig.generateConditionsAndPrint();

      //      System.out.println("x1| x2| x3| x4| x5| x6| x7");
      //      result.forEach(line ->
      //            System.out.println(line.stream()
      //                  .map(v -> v ? "1" : "0")
      //                  .collect(Collectors.joining(" | "))));


      Lfsr lfsr = new Lfsr(of(X8, X5, X3, X2));

      //      lfsr.generateSequences().forEach(
      //            seq -> System.out.println(seq.stream().map(v -> v ? "1" : "0").collect(Collectors.joining(", "))));
      System.out.println(result.size());
      lfsr.findConditionForMinTactsCoverageAndPrint(result);
   }
}