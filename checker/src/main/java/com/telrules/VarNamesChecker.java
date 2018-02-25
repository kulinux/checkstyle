package com.telrules;


import com.puppycrawl.tools.checkstyle.api.*;



public class VarNamesChecker extends Check {

    private static final String CATCH_MSG = "TRules said no. ";

    private final HungarianNotationMemberDetector detector = new HungarianNotationMemberDetector();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF, TokenTypes.PARAMETER_DEF};
    }

    @Override
    public void visitToken(DetailAST aAST) {

        String variableName = findVariableName(aAST);
        String type = findVariableType(aAST);
        if (itsAFieldVariable(aAST) && detector.matchOkVarName(type, variableName) == false ) {
            reportStyleError(aAST, variableName);
        }
    }

    private String findVariableName(DetailAST aAST) {
        DetailAST identifier = aAST.findFirstToken(TokenTypes.IDENT);

        return identifier.toString();
    }

    private String findVariableType(DetailAST aAST) {
        return aAST.findFirstToken(TokenTypes.TYPE).getFirstChild().getText();
    }

    private boolean itsAFieldVariable(DetailAST aAST) {
        return aAST.getType() == TokenTypes.PARAMETER_DEF
                || aAST.getType() == TokenTypes.VARIABLE_DEF;
    }

    private void reportStyleError(DetailAST aAST, String variableName) {
        log(aAST.getLineNo(), CATCH_MSG + variableName);
    }

}
