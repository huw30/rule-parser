package main.java.io.rainaw.ruleStringParser.Guards;

import java.util.HashSet;
import java.util.Stack;
import java.util.regex.*;

public class RuleStringGuard {
    /**
     * Main validates
     * @param ruleString string to test
     * @return boolean
     */
    public boolean validate(String ruleString){
        if (!this.bracketsGuard(ruleString)) return false;
        if (!this.spacesGuard(ruleString)) return false;
        return true;
    }

    /**
     * Check if all the brackets are properly closed.
     *
     * @param ruleString string to test
     * @return boolean
     */
    private boolean bracketsGuard(String ruleString) {
        int i = 0;

        HashSet<Character> brackets = new HashSet<>();
        brackets.add('(');
        brackets.add(')');
        brackets.add('{');
        brackets.add('}');

        Stack<Character> stack = new Stack<>();

        while (i < ruleString.length()) {
            Character curr = ruleString.charAt(i);

            if (!brackets.contains(curr)) {
                i++;
                continue;
            }

            if (curr == '{') {
                stack.push('}');
            } else if (curr == '(') {
                stack.push(')');
            } else if (stack.isEmpty() || stack.pop() != curr) {
                return false;
            }

            i++;
        }

        return stack.isEmpty();
    }

    /**
     * Check if the Rule is properly formed.
     * Uses a regex matcher
     *
     * @param ruleString string to test
     * @return boolean
     */
    private boolean spacesGuard(String ruleString) {
        int i = 0;

        String pattern = "^[\\(][^\\s]+[\\s][!=><]{1,2}[\\s][^\\s]+[\\)]$";

        while (i < ruleString.length()) {
            Character curr = ruleString.charAt(i);
            if (ruleString.charAt(i) == '(') {
                int start = i;

                while (ruleString.charAt(i) != ')') {
                    i++;
                }

                String curRuleString = ruleString.substring(start, i+1);

                if (!this.regexChecker(pattern, curRuleString)) {
                    return false;
                }
            }
            i++;
        }
        return true;
    }

    /**
     * Regex checker
     *
     * @param regex defined regex
     * @param strToCheck string to check
     * @return boolean
     */
    private boolean regexChecker(String regex, String strToCheck) {
        Pattern checkRegex = Pattern.compile(regex);
        Matcher regexMatcher = checkRegex.matcher(strToCheck);
        return regexMatcher.find();
    }
}
