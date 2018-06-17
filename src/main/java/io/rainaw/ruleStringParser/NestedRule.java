package main.java.io.rainaw.ruleStringParser;

import java.util.ArrayList;
import java.util.List;

public class NestedRule implements RuleEntity {
    private String nextOperator;
    private List<RuleEntity> children;
    private Boolean isNegated;

    /**
     * Constructor
     */
    public NestedRule() {
        this.nextOperator = "";
        this.children = new ArrayList<>();
        this.isNegated = false;
    }

    public String getNextOperator() {
        return this.nextOperator;
    }

    public void setNextOperator(String operator) {
        this.nextOperator = operator;
    }

    public List<RuleEntity> getChildren() {
        return this.children;
    }

    public void addChild(RuleEntity ruleEntity) {
        this.children.add(ruleEntity);
    }

    public Boolean getIsNegated() {
        return this.isNegated;
    }
    public void setIsNegated(Boolean isNegated) {
        this.isNegated = isNegated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (RuleEntity child: children) {
            sb.append(child.toString());
            sb.append(" ");
        }
        return sb.toString();
    }
}
