package fr.xebia.extremperf;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PricerTest {

    @Test
    public void first_simple_test() {
        Double price = new Pricer(
                100d,
                102d,
                1.07,
                1.01,
                1).fairValue();

        assertThat(price).isEqualTo(2.76);
    }

    @Test
    public void upper_strike() {
        Double price = new Pricer(
                100d,
                108d,
                1.07,
                1.01,
                1).fairValue();

        assertThat(price).isEqualTo(0);
    }

    @Test
    public void lower_strike() {
        Double price = new Pricer(
                100d,
                93.48,
                1.07,
                1.01,
                1).fairValue();

        assertThat(price).isEqualTo(7.46);
    }

    @Test
    public void over_three_periods() {
        Double price = new Pricer(
                100d,
                100d,
                1.07,
                1.01,
                3).fairValue();

        assertThat(price).isEqualTo(6.58);
    }

    @Test
    public void over_five_periods() {
        Double price = new Pricer(
                100d,
                100d,
                1.07,
                1.01,
                5).fairValue();

        assertThat(price).isEqualTo(8.87);
    }

}
