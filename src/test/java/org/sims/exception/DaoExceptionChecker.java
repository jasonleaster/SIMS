package org.sims.exception;

import org.junit.Assert;

public class DaoExceptionChecker {
    private boolean exceptionHappened = false;

    protected void exceptionHappenedChecker(){
        Assert.assertTrue(exceptionHappened);
        exceptionHappened = false; // reset
    }

    protected void exceptionNotHappenedChecker(){
        Assert.assertFalse(exceptionHappened);
    }

    public boolean isExceptionHappened() {
        return exceptionHappened;
    }

    public void setExceptionHappened(boolean exceptionHappened) {
        this.exceptionHappened = exceptionHappened;
    }
}
