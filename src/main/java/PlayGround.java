package main.java;

import main.java.io.rainaw.ruleStringParser.Exceptions.InvalidRuleStringFormatException;
import main.java.io.rainaw.ruleStringParser.NestedRule;
import main.java.io.rainaw.ruleStringParser.RuleEntity;
import main.java.io.rainaw.ruleStringParser.RuleParser;

public class PlayGround {

    public static void main(String[] args) {
        RuleParser ruleParser = new RuleParser();
        try {
            RuleEntity ruleSet1 = ruleParser.deserialize("(country == US) && (deviceName == PS4) && (appVersion >= 10.2)");

            NestedRule ruleSet2 = ruleParser.deserialize("(country == US) && {!{(deviceName == PS4) && {(appVersion >= 102) || (appVersion <= 12)}} || {(deviceName == ANDROID) || (appVersion >= 42)}}");

            System.out.println(ruleParser.serialize(ruleSet2));
        } catch(InvalidRuleStringFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
