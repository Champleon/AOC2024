import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Advent07 
{
    public static void main(String[] args) 
    {
        ArrayList<ArrayList<BigInteger>> equationsList = new ArrayList<>();
        String path = System.getProperty("user.dir");
        File f = new File(path + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] numbers = line.split("\\s+");
                numbers[0] = numbers[0].replace(":", "");
                equationsList.add(parseIntoList(numbers));
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }
        BigInteger sum1 = new BigInteger("0");
        BigInteger sum2 = new BigInteger("0");
        for(ArrayList<BigInteger> arr: equationsList)
        {
            if(equationSolvable(arr, new BigInteger("0")))
            {
                sum1 = sum1.add(arr.get(0));
            }
            if(equationSolvablePart2(arr, new BigInteger("0")))
            {
                sum2 = sum2.add(arr.get(0));
            }
        }
        System.out.println("Part 1: " + sum1);
        System.out.println("Part 2: " + sum2);
    }

    public static ArrayList<BigInteger> parseIntoList(String[] arr) //parse String Array into an integer ArrayList
    {
        ArrayList<BigInteger> tmp = new ArrayList<>();
        for (String arr1 : arr) {
            try {
                tmp.add(new BigInteger(arr1)); 
            }
            catch (NumberFormatException e) 
            {
                System.out.println("Input file contains something that could not be parsed into an integer.");
                System.exit(1);
            }
        }
        return tmp;
    }

    public static boolean equationSolvable(ArrayList<BigInteger> equation, BigInteger sum)
    {
        if(equation.size() == 1) return sum.equals(equation.get(0));
        ArrayList<BigInteger> equationAdd = new ArrayList<>(equation);
        ArrayList<BigInteger> equationMul = new ArrayList<>(equation);
        BigInteger tmp = equationAdd.remove(1);
        equationMul.remove(1);
        return (equationSolvable(equationAdd, tmp.add(sum))) || (equationSolvable(equationMul, tmp.multiply(sum)));
    }

    public static boolean equationSolvablePart2(ArrayList<BigInteger> equation, BigInteger sum)
    {
        if(equation.size() == 1) return sum.equals(equation.get(0));
        if(sum.compareTo(equation.get(0)) == 1) return false;
        ArrayList<BigInteger> equationAdd = new ArrayList<>(equation);
        ArrayList<BigInteger> equationMul = new ArrayList<>(equation);
        ArrayList<BigInteger> equationConcat = new ArrayList<>(equation);
        BigInteger tmp = equationAdd.remove(1);
        equationMul.remove(1);
        equationConcat.remove(1);
        return (equationSolvablePart2(equationAdd, tmp.add(sum))) || (equationSolvablePart2(equationMul, tmp.multiply(sum))) || (equationSolvablePart2(equationConcat, new BigInteger(sum.toString() + tmp.toString())));
    }
}
