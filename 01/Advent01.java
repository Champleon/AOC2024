import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Advent01 
{
    public static void main(String[] args) 
    {
        List<Integer> array1 = new ArrayList<>();
        List<Integer> array2 = new ArrayList<>();
        String path = System.getProperty("user.dir");
        File f = new File(path + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] numbers = line.split("\\s+");
                array1.add(Integer.parseInt(numbers[0]));
                array2.add(Integer.parseInt(numbers[1]));
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }
        Collections.sort(array1);
        Collections.sort(array2);

        int[] distances = distanceArray(array1, array2);   
        System.out.println("Total Distance: " + totalArrayValue(distances));
        System.out.println("Similarity score: " + similarityScore(array1, array2));
    }

    public static int[] distanceArray(List<Integer> list1, List<Integer> list2) //get sorted Lists of same size as inputs
    {
        int n = list1.size();
        int[] results = new int[n];
        for (int i = 0; i < n; i++) 
        {
            results[i] = Math.abs(list1.get(i) - list2.get(i));
        }
        return results;
    }

    public static int totalArrayValue(int[] list)
    {
        int n = list.length;
        int sum = 0;
        for (int i = 0; i < n; i++) 
        {
            sum += list[i];
        }
        return sum;
    }

    public static int similarityScore(List<Integer> list1, List<Integer> list2)
    {
        int score = 0;
        int multiplier = 0;
        int n = list1.size();
        for (int i = 0; i < n; i++) 
        {
            multiplier = containsHowOften(list2, list1.get(i));
            score = score + (list1.get(i) * multiplier);
        }
        return score;
    }

    public static int containsHowOften(List<Integer> list, int x)
    {
        int count = 0;
        if(list.contains(x))
        {
            int n = list.size();
            for(int i = 0; i < n; i++)
            {
                if(list.get(i) == x)
                {
                    count++;
                }
            }
        }
        return count;
    }
}
