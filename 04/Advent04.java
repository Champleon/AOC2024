import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Advent04 
{
    public static void main(String[] args) 
    {
        ArrayList<ArrayList<Character>> grid = new ArrayList<>();
        String path = System.getProperty("user.dir");
        File f = new File(path + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                ArrayList<Character> tmp = new ArrayList<>();
                for (char c: line.toCharArray())
                {
                    tmp.add(c);
                }
                grid.add(tmp);
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }
        ArrayList<ArrayList<Integer>> xPositions = findX(grid);
        int sumPart1 = 0;
        for(ArrayList<Integer> a: xPositions)
        {
            sumPart1 += findMAS(grid, a.get(0), a.get(1), -1, 0); //up
            sumPart1 += findMAS(grid, a.get(0), a.get(1), -1, 1); //up right
            sumPart1 += findMAS(grid, a.get(0), a.get(1), 0, 1); //right
            sumPart1 += findMAS(grid, a.get(0), a.get(1), 1, 1); //down right
            sumPart1 += findMAS(grid, a.get(0), a.get(1), 1, 0); //down
            sumPart1 += findMAS(grid, a.get(0), a.get(1), 1, -1); //down left
            sumPart1 += findMAS(grid, a.get(0), a.get(1), 0, -1); //left
            sumPart1 += findMAS(grid, a.get(0), a.get(1), -1, -1); //up left
        }
        System.out.println("Sum of Part 1: " + sumPart1);
        
        ArrayList<ArrayList<Integer>> aPositions = findA(grid);
        int sumPart2 = 0;
        for(ArrayList<Integer> a: aPositions)
        {
            sumPart2 += findXMAS(grid, a.get(0), a.get(1));
        }
        System.out.println("Sum of Part 2: " + sumPart2);
    }

    public static ArrayList<ArrayList<Integer>> findX(ArrayList<ArrayList<Character>> grid)
    {
        int n = grid.size();
        int m = grid.get(0).size();
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        for(int i = 0; i<n; i++)
        {
            for(int j = 0; j<m; j++)
            {
                if(grid.get(i).get(j).equals('X'))
                {
                    ArrayList<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    tmp.add(j);
                    array.add(tmp);
                }
            }
        }
        return array;
    }

    public static int findMAS(ArrayList<ArrayList<Character>> grid, int indexX, int indexY, int incX, int incY) //searching for MAS in any direction, starting from a found x
    {
        if((indexX + 3 * incX) >= 0 && (indexX + 3 * incX)<grid.size() && (indexY + 3 * incY) >= 0 && (indexY + 3 * incY) < grid.size()) 
        {
            if(grid.get(indexX + 1 * incX).get(indexY + 1 * incY).equals('M')) 
            {
                if(grid.get(indexX + 2 * incX).get(indexY + 2 * incY).equals('A'))
                {
                    if(grid.get(indexX + 3 * incX).get(indexY + 3 * incY).equals('S'))
                    {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public static ArrayList<ArrayList<Integer>> findA(ArrayList<ArrayList<Character>> grid)
    {
        int n = grid.size();
        int m = grid.get(0).size();
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        for(int i = 0; i<n; i++)
        {
            for(int j = 0; j<m; j++)
            {
                if(grid.get(i).get(j).equals('A'))
                {
                    ArrayList<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    tmp.add(j);
                    array.add(tmp);
                }
            }
        }
        return array;
    }

    public static int findXMAS(ArrayList<ArrayList<Character>> grid, int indexX, int indexY) //searching for x shaped MAS, starting from A
    {
        if((indexX - 1) >= 0 && (indexX + 1) < grid.size() && (indexY - 1) >= 0 && (indexY + 1) <grid.size()) 
        {
            String s1 = ""+ grid.get(indexX - 1).get(indexY - 1) + grid.get(indexX).get(indexY) + grid.get(indexX + 1).get(indexY + 1);
            String s2 = ""+ grid.get(indexX + 1).get(indexY - 1) + grid.get(indexX).get(indexY) + grid.get(indexX - 1).get(indexY + 1);
            if((s1.equals("MAS") && s2.equals("MAS")) || (s1.equals("SAM") && s2.equals("MAS")) || (s1.equals("SAM") && s2.equals("SAM")) || (s1.equals("MAS") && s2.equals("SAM")))
            {
                return 1;
            }
        }
        return 0;
    }
}
