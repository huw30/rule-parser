package test.java;

import main.java.io.rainaw.ruleStringParser.Exceptions.InvalidRuleStringFormatException;
import main.java.io.rainaw.ruleStringParser.NestedRule;
import main.java.io.rainaw.ruleStringParser.Rule;
import main.java.io.rainaw.ruleStringParser.RuleEntity;
import main.java.io.rainaw.ruleStringParser.RuleParser;
import org.junit.Assert;
import org.junit.Test;

public class RuleParserTest {
    @Test
    public void testDeserializeValidRuleString() {
        RuleParser ruleParser = new RuleParser();

        String strToTest = "!{(country == US) && (deviceName == PS4)} && (appVersion >= 10.2)";

        try {
            NestedRule nestedRule = ruleParser.deserialize(strToTest);

            RuleEntity firstNestedRule = nestedRule.getChildren().get(0);
            Assert.assertTrue(firstNestedRule instanceof NestedRule);

            Boolean isFirstElementNegated =((NestedRule) firstNestedRule).getIsNegated();
            Assert.assertTrue(isFirstElementNegated);

            Assert.assertEquals(2, nestedRule.getChildren().size());
            Assert.assertEquals(2, ((NestedRule) firstNestedRule).getChildren().size());
        } catch (InvalidRuleStringFormatException e) {
            // do nothing
        }
    }
    @Test(expected = InvalidRuleStringFormatException.class)
    public void testDeserializeRuleStringNonClosedBrackets() throws InvalidRuleStringFormatException {
        RuleParser ruleParser = new RuleParser();

        String strToTest = "{{{{(country == US) && (deviceName == PS4)}";

        ruleParser.deserialize(strToTest);
    }
    @Test(expected = InvalidRuleStringFormatException.class)
    public void testDeserializeRuleStringInvalidRule() throws InvalidRuleStringFormatException {
        RuleParser ruleParser = new RuleParser();

        String strToTest1 = "(  country == US) && (deviceName== PS4)";

        ruleParser.deserialize(strToTest1);
    }
    @Test
    public void testSerializeNestedRule() {
        String expected = "!{(country != US) && (deviceName == PS4)} || (appVersion >= 10.2)";

        NestedRule root = new NestedRule();
        NestedRule firstElement = new NestedRule();
        Rule firstRule = new Rule("country", "US", "!=");
        Rule SecoundRule = new Rule("(deviceName == PS4)");
        Rule ThirdRule = new Rule("(appVersion >= 10.2)");

        firstRule.setNextOperator("&&");
        firstElement.setNextOperator("||");
        firstElement.setIsNegated(true);

        firstElement.addChild(firstRule);
        firstElement.addChild(SecoundRule);

        root.addChild(firstElement);
        root.addChild(ThirdRule);

        RuleParser ruleParser = new RuleParser();
        String res = ruleParser.serialize(root);
        Assert.assertEquals(expected, res);
    }

    @Test
    public void testSerializeDeserializedNestedRule() {
        RuleParser ruleParser = new RuleParser();

        String strToTest1 = "(country == US) && (deviceName == PS4) && (appVersion >= 10.2)";
        String strToTest2 = "!{(country == US) && (deviceName == PS4)} && (appVersion >= 10.2)";
        String strToTest3 = "(country == US) && {!{(deviceName == PS4) && {(appVersion >= 102) || (appVersion <= 12)}} || {(deviceName == ANDROID) || (appVersion >= 42)}}";

        try {
            NestedRule nestedRule1 = ruleParser.deserialize(strToTest1);
            NestedRule nestedRule2 = ruleParser.deserialize(strToTest2);
            NestedRule nestedRule3 = ruleParser.deserialize(strToTest3);


            String serialize1 = ruleParser.serialize(nestedRule1);
            String serialize2 = ruleParser.serialize(nestedRule2);
            String serialize3 = ruleParser.serialize(nestedRule3);

            Assert.assertEquals(strToTest1, serialize1);
            Assert.assertEquals(strToTest2, serialize2);
            Assert.assertEquals(strToTest3, serialize3);
        } catch (InvalidRuleStringFormatException e) {
            // do nothing
        }
    }
}
