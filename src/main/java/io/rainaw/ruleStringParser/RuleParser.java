package main.java.io.rainaw.ruleStringParser;

import main.java.io.rainaw.ruleStringParser.Exceptions.InvalidRuleStringFormatException;
import main.java.io.rainaw.ruleStringParser.Guards.RuleStringGuard;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class RuleParser {

    private HashSet<Character> logicalComp = new HashSet<>();
    private RuleStringGuard guard;

    /**
     * Constructor
     */
    public RuleParser() {
        this.populateLogicalComp();
        this.guard = new RuleStringGuard();
    }

    /**
     * Serialize a NestedRule object given the root
     *
     * @param root the root of the NestedRule
     * @return String
     */
    public String serialize(NestedRule root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb, 0);
        return sb.toString();
    }

    /**
     * Nested Rule Traversal (DFS)
     * Build string for the Nested Rule
     *
     * @param ruleEntity The current Rule Element
     * @param sb StringBuilder
     * @param index level of traversal
     */
    private void dfs(RuleEntity ruleEntity, StringBuilder sb, int index) {
        if (ruleEntity == null) {
            return;
        }
        if (ruleEntity instanceof Rule) {
            sb.append(ruleEntity.toString());

            if (!ruleEntity.getNextOperator().equals("")) {
                sb.append(" ");
                sb.append(ruleEntity.getNextOperator());
                sb.append(" ");
            }

            return;
        }

        if (ruleEntity instanceof NestedRule) {
            if (((NestedRule) ruleEntity).getIsNegated()) {
                sb.append("!");
            }
            if (index != 0) {
                sb.append("{");
            }

            List<RuleEntity> children = ((NestedRule) ruleEntity).getChildren();

            for (RuleEntity child: children) {
                dfs(child, sb, index + 1);
            }

            if (index != 0) {
                sb.append("}");
            }

            if (!ruleEntity.getNextOperator().equals("")) {
                sb.append(" ");
                sb.append(ruleEntity.getNextOperator());
                sb.append(" ");
            }
        }
    }

    //TODO:test case for throw exception

    /**
     * Deserialize the rule string to a NestedRule object
     *
     * @param ruleString String to deserialize
     * @return NestedRule
     *
     * @throws InvalidRuleStringFormatException Throws an exception when the rule string is misformed.
     */
    public NestedRule deserialize(String ruleString) throws InvalidRuleStringFormatException {
        if (!this.guard.validate(ruleString)) {
            throw new InvalidRuleStringFormatException();
        }

        NestedRule root = new NestedRule();
        RuleEntity pre = root;
        Boolean isNegated = false;

        Stack<NestedRule> stack = new Stack<>();
        stack.push(root);

        for (int i = 0; i < ruleString.length(); i++) {
            NestedRule curr = stack.peek();

            if (ruleString.charAt(i) == ' ') continue;

            if (ruleString.charAt(i) == '(') {
                int start = i;

                while (ruleString.charAt(i) != ')') {
                    i++;
                }

                String curRuleString = ruleString.substring(start, i+1);
                Rule newRule = new Rule(curRuleString);
                curr.addChild(newRule);
                pre = newRule;

            } else if (ruleString.charAt(i) == '{') {
                NestedRule nestedRule = new NestedRule();
                curr.addChild(nestedRule);
                stack.push(nestedRule);

                if (isNegated) {
                    nestedRule.setIsNegated(true);
                    isNegated = false;
                }

                pre = nestedRule;

            } else if (ruleString.charAt(i) == '}') {
                pre = stack.pop();

            } else if (logicalComp.contains(ruleString.charAt(i))) {
                if (ruleString.charAt(i) != '!') {
                    i++;
                    pre.setNextOperator(ruleString.substring(i-1, i+1));
                } else {
                    isNegated = true;
                }
            }
        }

        return root;
    }

    /**
     * Add allowed logical comparator into the set.
     */
    private void populateLogicalComp() {
        logicalComp.add('|');
        logicalComp.add('&');
        logicalComp.add('!');
    }
}
