package day5;

import java.util.List;

public record SleighInstructionSet(List<UpdateInstructionRule> rules, List<List<Integer>> updateInstructions){}
