package com.telrules;

import java.util.Arrays;
import java.util.regex.Pattern;

public class HungarianNotationMemberDetector {

    public boolean matchOkVarName(String type, String variableName) {
        return Arrays.asList(CheckerDefinition.values())
            .stream()
            .filter(x -> Arrays.asList(x.cls).contains(type))
            .map(x -> x.patternOk )
            .map(Pattern::compile)
            .map(x -> x.matcher(variableName).matches())
            .findAny()
            .orElse(false);
    }

    enum CheckerDefinition {
        STRING(new String[] {"String"}, "s[A-Z0-9].*"),
        INT(new String[] {"Integer", "int"}, "i[A-Z0-9].*"),
        MAP(new String[] {"Map", "int"}, "h[A-Z0-9].*"),
        List(new String[] {"List", "Set"}, "v[A-Z0-9].*"),
        ;

        private final String[] cls;
        private final String patternOk;

        CheckerDefinition(String[] cls, String patternOk) {
            this.cls = cls;
            this.patternOk = patternOk;
        }


    }
}
