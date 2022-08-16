package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1)
        {
            throw new IllegalArgumentException();
        }
        if (number == 2)
            return true;
        for (int i = 2; i < number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public int digitSum(int number){
        int i = 0;
        while(number != 0){
            i += number % 10;
            number = number / 10;
        }
        return i;
    }
}
