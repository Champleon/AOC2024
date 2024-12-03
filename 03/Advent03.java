import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent03 
{
    public static void main(String[] args) 
    {
        String path = System.getProperty("user.dir");
        String input = "";
        try
        {
            input = Files.readString(Paths.get(path + "\\input.txt"));
        }
        catch(IOException e)
        {
            System.out.println("Error reading file");
            System.exit(1);
        }
        Pattern mulPattern = Pattern.compile("mul\\([1-9][0-9]*,[1-9][0-9]*\\)");
        Matcher mulMatcher = mulPattern.matcher(input);

        Pattern controlPattern = Pattern.compile("do\\(\\)|don't\\(\\)");
        Matcher controlMatcher = controlPattern.matcher(input);
        boolean doSwitch = true;
        int controlIndex = 0;

        List<String> foundPatternsPartOne = new ArrayList<>();
        List<Integer> productsPartOne = new ArrayList<>();

        List<String> foundPatternsPartTwo = new ArrayList<>();
        List<Integer> productsPartTwo = new ArrayList<>();
        while(mulMatcher.find())
        {
            while(controlMatcher.find(controlIndex) && controlMatcher.start() < mulMatcher.start())
            {
                String control = controlMatcher.group();
                doSwitch = control.equals("do()");
                controlIndex = controlMatcher.end();
            }
            String tmp = mulMatcher.group();
            foundPatternsPartOne.add(tmp);
            if(doSwitch)
            {
                foundPatternsPartTwo.add(tmp);
            }
            
        }
        for (String p : foundPatternsPartOne)
        {
            p = p.replace("mul(", "");
            p = p.replace(")", "");
            productsPartOne.add(splitAndMult(p));
        }
        System.out.println("Part 1: " + sumList(productsPartOne));

        for (String p : foundPatternsPartTwo)
        {
            p = p.replace("mul(", "");
            p = p.replace(")", "");
            productsPartTwo.add(splitAndMult(p));
        }
        System.out.println("Part 2: " + sumList(productsPartTwo));
    }
    public static int splitAndMult(String s)
    {
        String[] tmp = s.split(",");
        int x = 0;
        int y = 0;
        try 
        {
            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);
        } 
        catch (NumberFormatException e) 
        {
            System.out.println("Error while multiplying the numbers");
            System.exit(1);
        }
        return x * y;
    }

    public static int sumList(List<Integer> list)
    {
        int sum = 0;
        for (int i : list)
        {
            sum +=i;
        }
        return sum;
    }
}
