package main.java.io.rainaw.ruleStringParser;

public class Rule implements RuleEntity {
    private String left;
    private String right;
    private String operator;
    private String nextOperator;

    /**
     * Constructor from parts
     * @param left
     * @param right
     * @param operator
     */
    public Rule(String left, String right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Constructor from string
     * @param rule
     */
    public Rule(String rule) {
        rule = rule.trim();
        rule = rule.replace("(", "");
        rule = rule.replace(")", "");
        String [] arrOfStr = rule.split(" ", 3); // what about those ones that doesn't have spaces
        this.left = arrOfStr[0];
        this.operator = arrOfStr[1];
        this.right = arrOfStr[2];
        this.nextOperator = "";
    }

    public String getLeft() {
        return this.left;
    }
    public String getRight() {
        return this.right;
    }
    public String getOperator() {
        return this.operator;
    }

    public String getNextOperator() {
        return this.nextOperator;
    }
    public void setNextOperator(String operator) {
        this.nextOperator = operator;
    }

    @Override
    public String toString()
    {
        return "(" + this.left + " " + this.operator + " " + this.right + ")";
    }
}
