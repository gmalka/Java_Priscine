package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 7, 5})
    public void isPrimeForPrimesTest(int number){
        Assertions.assertTrue(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {31, 79, 191})
    public void isPrimeForNotPrimesTest(int number){
        Assertions.assertTrue(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 77, 50249})
    public void isPrimeForIncorrectNumbersTest(int number){
        Assertions.assertFalse(new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, -77, -50249, Integer.MIN_VALUE})
    public void isPrimeForExceptionNumbersTest(int number) throws Exception {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->new NumberWorker().isPrime(number));
        Assertions.assertThrows(IllegalArgumentException.class, ()->new NumberWorker().isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.scv")
    public void isPriveForDigitsumTest(int num, int result)
    {
        Assertions.assertEquals(result, new NumberWorker().digitSum(num));
    }

}