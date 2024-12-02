import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Advent02 
{
    public static void main(String[] args) 
    {
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        String path = System.getProperty("user.dir");
        File f = new File(path + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] numbers = line.split("\\s+");
                array.add(parseIntoList(numbers));
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }
        boolean[] isSafe1 = new boolean[array.size()];
        for(int i = 0; i<array.size(); i++)
        {
            isSafe1[i] = isMonotone(array.get(i)) && correctDifference(array.get(i));
        }
        System.out.println("Amount of safe readings without the problem dampener: " + countSafe(isSafe1));
        boolean[] isSafe2 = new boolean[array.size()];
        for(int i = 0; i<array.size(); i++)
        {
            isSafe2[i] = isSafeWithDampener(array.get(i));
        }
        System.out.println("Amount of safe readings with the problem dampener: " + countSafe(isSafe2));
    }

    public static ArrayList<Integer> parseIntoList(String[] arr) //parse String Array into an integer ArrayList
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (String arr1 : arr) {
            try {
                tmp.add(Integer.valueOf(arr1)); 
            }
            catch (NumberFormatException e) 
            {
                System.out.println("Input file contains something that could not be parsed into an integer.");
                System.exit(1);
            }
        }
        return tmp;
    }

    public static boolean isMonotone (ArrayList<Integer> arr)
    {
        return (isIncreasing(arr) || isDecreasing(arr));
    }
    public static boolean isIncreasing(ArrayList<Integer> arr)
    {
        for (int i = 0; i < arr.size()-1; i++) 
        {
            if(arr.get(i) > arr.get(i+1)) return false;
        }
        return true;
    }
    public static boolean isDecreasing(ArrayList<Integer> arr)
    {
        for (int i = 0; i < arr.size()-1; i++) 
        {
            if(arr.get(i) < arr.get(i+1)) return false;
        }
        return true;
    }

    public static boolean correctDifference(ArrayList<Integer> arr)
    {
        int difference;
        for (int i = 0; i < arr.size()-1; i++) 
        {
            difference = Math.abs(arr.get(i) - arr.get(i+1));
            if(difference > 3 || difference < 1) return false;
        }
        return true;
    }

    public static int countSafe(boolean[] arr)
    {
        int count = 0;
        for (int i = 0; i < arr.length; i++) 
        {
            if(arr[i]) count++;
        }
        return count;
    }

    public static boolean isSafeWithDampener(ArrayList<Integer> arr) //part two
    {
        for(int i = 0; i<arr.size(); i++)
        {
            ArrayList<Integer> tmp = new ArrayList<>(arr);
            tmp.remove(i);
            if(isMonotone(tmp) && correctDifference(tmp)) return true;
        }
        return false;
    }
}
