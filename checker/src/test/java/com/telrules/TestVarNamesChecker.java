package com.telrules;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestVarNamesChecker {

    private static final int AMOUNT_OF_HUNGARIAN_MEMBER_ERRORS = 6;

    @Test
    public void ignore_local_variables() throws Exception {
        Checker checker = prepareCheckStyleChecker();
        List<File> files = prepareFilesToBeChecked();

        addListener(checker);

        int numberOfErrors = checker.process(files);
        assertThat(numberOfErrors, is(AMOUNT_OF_HUNGARIAN_MEMBER_ERRORS));
    }

    private void addListener(Checker checker) {
        checker.addListener(new AuditListener() {
            public void debug(AuditEvent ae) {
               System.out.println( ae.getFileName() + " " + ae.getLine() + " line " + ae.getMessage() );
            }

            public void auditStarted(AuditEvent auditEvent) {

            }

            public void auditFinished(AuditEvent auditEvent) {

            }

            public void fileStarted(AuditEvent auditEvent) {

            }

            public void fileFinished(AuditEvent auditEvent) {

            }

            public void addError(AuditEvent auditEvent) {
                debug(auditEvent);
            }

            public void addException(AuditEvent auditEvent, Throwable throwable) {
                debug(auditEvent);
            }
        });
    }

    private Checker prepareCheckStyleChecker() throws CheckstyleException {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(prepareConfiguration());
        return checker;
    }

    private DefaultConfiguration prepareConfiguration() {
        DefaultConfiguration checks = new DefaultConfiguration("Checks");
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        DefaultConfiguration antiHungarian = new DefaultConfiguration(VarNamesChecker.class.getCanonicalName());
        checks.addChild(treeWalker);
        treeWalker.addChild(antiHungarian);
        return checks;
    }

    private List<File> prepareFilesToBeChecked() {
        String testFileName = "TestClassWithErrors.java";
        URL testFileUrl = getClass().getResource(testFileName);
        File testFile = new File(testFileUrl.getFile());
        List<File> files = new ArrayList<File>();
        files.add(testFile);
        return files;
    }
}
