package day7;

public class Day7Shared {

    static boolean linearCombinationMakesEquationMatchUp(final long equationResult, final long[] operands, final char[][] operators) {
        long intermediateResult = 0L;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < operators.length; i++) {
            for (int j = 0; j < operands.length; j++) {
                if (j == 0) {
                    intermediateResult = operands[0];
                    sb.append(operands[j]);
                } else {
                    if (operators[i][j - 1] == '+') {
                        sb.append(" + ");
                        sb.append(operands[j]);
                        intermediateResult += operands[j];
                    } else if (operators[i][j - 1] == '*') {
                        sb.append(" * ");
                        sb.append(operands[j]);
                        intermediateResult *= operands[j];
                    } else if (operators[i][j - 1] == '|') {
                        sb.append(" || ");
                        sb.append(operands[j]);
                        intermediateResult = Long.parseLong(intermediateResult + "" + operands[j]);
                    } else {
                        throw new IllegalStateException("unexpected operator");
                    }
                }
            }
            sb.append(" = ");
            sb.append(intermediateResult);
            if (intermediateResult == equationResult) {
                sb.append(" SUCCESS\n");
//                System.out.println(sb.toString());
                return true;
            }
            sb.append(" FAIL\n");
        }
        return false;
    }

}
