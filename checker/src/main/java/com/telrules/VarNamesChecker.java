package com.telrules;


import com.puppycrawl.tools.checkstyle.api.*;



public class VarNamesChecker extends AbstractCheck {

    private static final String CATCH_MSG = "TRules said no. ";

    private final HungarianNotationMemberDetector detector = new HungarianNotationMemberDetector();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF, TokenTypes.PARAMETER_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return this.getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST aAST) {

        if( aAST.getParent() != null &&
                aAST.getParent().getType() == TokenTypes.OBJBLOCK )
        {
            return;
        }

        String sVAriableName = findVariableName(aAST);
        String sType = findVariableType(aAST);
        if (itsAFieldVariable(aAST) && detector.matchOkVarName(sType, sVAriableName) == false ) {
            reportStyleError(aAST, sVAriableName);
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

    private void reportStyleError(DetailAST aAST, String sVariableName) {
        log(aAST.getLineNo(), CATCH_MSG + sVariableName);
    }

}
