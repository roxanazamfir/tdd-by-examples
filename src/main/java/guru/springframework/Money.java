package guru.springframework;



public class Money implements Expression {
     final int amount;
     final String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    String currency() {
        return currency;
    }

@Override
    public  Expression times(int multiplier){
        return new Money(amount * multiplier, this.currency);
    }

    @Override
    public Expression plus (Expression added) {return  new Sum(this, added);}

    public static Money dollar(int amount) { return new Money(amount, "USD");}

    public static Money franc(int amount) { return new Money(amount, "CHF");}


    public boolean equals(Object object){
        Money money = (Money) object;
        return amount == money.amount && this.currency == money.currency;
    }

    @Override
    public  Money reduce(Bank bank, String to){
        return new Money(amount / bank.rate(this.currency, to), to);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
