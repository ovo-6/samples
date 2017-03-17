package com.ovo6.expenses.repo;

import java.math.BigDecimal;

/**
 * Holder for sum and average to be returned by JQL query.
 */
public class ExpenseStats {

    private final BigDecimal sum;
    private final Double avg;

    public ExpenseStats(BigDecimal sum, Double avg) {
        this.sum = (sum == null)? new BigDecimal(0): sum;
        this.avg = (avg == null)? 0d: avg;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }
}
