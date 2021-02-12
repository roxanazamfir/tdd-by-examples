package guru.springframework;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testMultiplication(){
    Money five = Money.dollar(5);

    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));

    Money fivef = Money.franc(5);

    assertEquals(Money.franc(10), fivef.times(2));
    }

    @Test
    void testDifferentClassEquality(){
        assertTrue(new Money(10, "CHF").equals(new Money(10, "CHF")));
    }
    @Test
    void testEqualityDollar() {
        assertEquals(new Money(5, "USD"), new Money(5, "USD"));
        assertNotEquals(new Money(5, "USD"), new Money(8, "USD"));

    }

    @Test
    void testCurrency(){
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    void testMultiplicationFranc(){
        Money five = new Money(5, "CHF");
        Expression product = five.times(2);
        assertEquals(new Money(10,"CHF"), product);
        product = five.times(3);
        assertEquals(new Money(15,"CHF"),  product);
    }

    @Test
    void testEqualityFranc() {
        assertEquals(Money.franc(5), Money.franc(5));
        assertNotEquals(Money.franc(5), Money.franc(8));
    }

    @Test
    void testPlusReturnSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augmend);
        assertEquals(five, sum.augmend);
    }

    @Test
    void testReduceSum(){
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum);
        assertEquals(Money.dollar(7), result);
    }

    @Test
    void testReduceMoney(){
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1));
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void testReduceMoneyDifferentCurrency(){
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2));
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void testIdentityRate(){
        assertEquals(1, new Bank().rate("USD", "USD"));
        assertEquals(1, new Bank().rate("CHF", "CHF"));
    }

    @Test
    void testMixedAddition(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs));
        assertEquals(Money.dollar(10), result);
    }

    @Test
    private void testSumPlusMoney(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD",2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum);
        assertEquals(Money.dollar(15), result);
    }

    @Test
    private void testSumTimes(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD",2);
        Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
        Money result = bank.reduce(sum);
        assertEquals(Money.dollar(20), result);
    }
}

