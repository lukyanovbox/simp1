package com.lukaynov.simp.genearation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.of;
import static java.lang.String.format;


public class Lfsr {

   private final Map<InputName, LfsrElement> elements;

   private final Supplier<Boolean> xorFunction;

   public Lfsr(final List<InputName> names) {
      LfsrElement X1 = LfsrElement.builder().name(InputName.X1).state(true).build();
      LfsrElement X2 = LfsrElement.builder().name(InputName.X2).build();
      LfsrElement X3 = LfsrElement.builder().name(InputName.X3).build();
      LfsrElement X4 = LfsrElement.builder().name(InputName.X4).build();
      LfsrElement X5 = LfsrElement.builder().name(InputName.X5).build();
      LfsrElement X6 = LfsrElement.builder().name(InputName.X6).build();
      LfsrElement X7 = LfsrElement.builder().name(InputName.X7).build();
      LfsrElement X8 = LfsrElement.builder().name(InputName.X8).build();

      X1.setNext(X2);
      X2.setNext(X3);
      X3.setNext(X4);
      X4.setNext(X5);
      X5.setNext(X6);
      X6.setNext(X7);
      X7.setNext(X8);

      elements = ImmutableMap.<InputName, LfsrElement> builder()
            .put(InputName.X1, X1)
            .put(InputName.X2, X2)
            .put(InputName.X3, X3)
            .put(InputName.X4, X4)
            .put(InputName.X5, X5)
            .put(InputName.X6, X6)
            .put(InputName.X7, X7)
            .put(InputName.X8, X8)
            .build();

      xorFunction = () -> names.stream()
            .map(n -> elements.get(n).state)
            .reduce((a, b) -> a ^ b)
            .get();

   }

   public void findConditionForMinTactsCoverageAndPrint(Set<List<Boolean>> faultTestConditions) {
      List<List<Boolean>> startSequences = generateSequences();

      int minimalMaxTactIndex = Integer.MAX_VALUE;
      List<Boolean> resultStartSequence = new ArrayList<>();

      for (List<Boolean> startSequence : startSequences) {
         int maxTactPerStartSequence = 0;
         List<List<Boolean>> generatedSequences = generateSequences(startSequence);

         for (List<Boolean> testCondition : faultTestConditions) {
            int tactWhenFaultFound = indexOf(generatedSequences, testCondition);
            if (tactWhenFaultFound > maxTactPerStartSequence) {
               maxTactPerStartSequence = tactWhenFaultFound;
            }
         }

         if (maxTactPerStartSequence < minimalMaxTactIndex) {
            minimalMaxTactIndex = maxTactPerStartSequence;
            resultStartSequence = startSequence;
         }
      }

      System.out.println(format("max tact %s ", minimalMaxTactIndex + 1));
      System.out.println(format("Start condition:  %s",
            resultStartSequence.stream().map(v -> v ? "1" : "0").collect(Collectors.joining(", "))));
   }


   public List<List<Boolean>> generateSequences() {
      return generateSequences(of(true, false, false, false, false, false, false, false));
   }

   public List<List<Boolean>> generateSequences(List<Boolean> startSequence) {
      checkState(startSequence.size() == 8);

      List<LfsrElement> lfsrElementList = Arrays.stream(InputName.values()).map(elements::get).collect(Collectors.toList());
      for (int i = 0; i < elements.size(); i++) {
         lfsrElementList.get(i).setState(startSequence.get(i));
      }

      List<List<Boolean>> sequences = new ArrayList<>();
      List<Boolean> initial = getStateOutputs();

      List<Boolean> sequence = initial;
      do {
         sequences.add(sequence);
         sequence = generateSequence();
      }
      while (!initial.equals(sequence));

      return sequences;
   }


   private List<Boolean> generateSequence() {
      elements.get(InputName.X1).updateStateBounded(xorFunction.get());

      return getStateOutputs();
   }


   private List<Boolean> getStateOutputs() {
      List<Boolean> stateOutputs = new ArrayList<>();

      for (LfsrElement element : Arrays.stream(InputName.values()).map(elements::get).collect(Collectors.toList())) {
         stateOutputs.add(element.isState());
      }

      return stateOutputs;
   }

   private int indexOf(List<List<Boolean>> source, List<Boolean> object) {
      for (int i = 0; i < source.size(); i++) {
         List<Boolean> subList = source.get(i).subList(1, source.get(i).size());

         if (subList.equals(object)) {
            return i;
         }
      }
      return -1;
   }
}
