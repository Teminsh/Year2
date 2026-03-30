package ADS.Semester1.Lab3;

/*
Лаба №3 "Задача о простых множителях"
На вход дается одно число х, нужно вывести все числа от 1 до х, удовлетворяющие условию:
3^K * 5^L * 7^M=xi

где K, L, M - натуральные числа или могут быть равны 0.
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main
{
    static void main()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число x = ");
        int x = scanner.nextInt();
        ArrayList<Integer> result = new ArrayList<>();

        for (long p3 = 1; p3 <= x; p3 *= 3)
        {
            for (long p5 = 1; p3 * p5 <= x; p5 *= 5)
            {
                for (long p7 = 1; p3 * p5 * p7 <= x; p7 *= 7)
                {
                    result.add((int) (p3 * p5 * p7));
                }
            }
        }

        result.sort(Comparator.naturalOrder());
        for (int num : result)
        {
            System.out.println(num);
        }
    }
}