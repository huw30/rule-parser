package main.java.io.rainaw.ruleStringParser.Exceptions;

public class InvalidRuleStringFormatException extends Exception {
    @Override
    public String getMessage() {
        return "The Rule String you provided is in an invalid format.";
    }
}
