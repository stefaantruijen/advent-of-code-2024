package day5;

import java.util.List;

public class UpdateInstructionRule {
	private final int subject;
	private final int constraint;

	/**
	 * If rule is represented like, e.g 15|23, then 15 is the rule's subject and 23 is the rule's constraint.
	 * It means page 15 must always come before 23.
	 */
	public UpdateInstructionRule(final int subject, final int constraint) {
		this.subject = subject;
		this.constraint = constraint;
	}

	public boolean isSatisfiedBy(final List<Integer> updateInstructions) {
		for (int i = 0; i < updateInstructions.size(); i++) {
			if (updateInstructions.get(i) == subject) {
				for (int j = 0; j < i; j++) {
					if (updateInstructions.get(j) == constraint) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
