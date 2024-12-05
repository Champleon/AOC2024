import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Advent05 
{
    public static void main(String[] args) 
    {
        ArrayList<String> rulesInput = new ArrayList<>();
        ArrayList<String> updatesInput = new ArrayList<>();
        String path = System.getProperty("user.dir");
        File f = new File(path + "\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.contains("|"))
                {
                    rulesInput.add(line);
                }
                else if(line.contains(","))
                {
                    updatesInput.add(line);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }
        ArrayList<int[]> rules = new ArrayList<>();
        for(String rule: rulesInput)
        {
            String[] parts = rule.split("\\|");
            rules.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
        }
        
        int sumOfMiddlePagesPart1 = 0;
        int sumOfMiddlePagesPart2 = 0;
        for(String update: updatesInput)
        {
            String[] parts = update.split(",");
            ArrayList<Integer> updates = new ArrayList<>();
            for(String s: parts)
            {
                updates.add(Integer.valueOf(s));
            }
            if(isValidOrder(updates, rules))
            {
                int middlePage = updates.get(updates.size() / 2);
                sumOfMiddlePagesPart1 += middlePage;
            }
            else //part 2
            {
                ArrayList<Integer> correctList = correctOrder(updates, rules);
                int middlePage = correctList.get(correctList.size()/2);
                sumOfMiddlePagesPart2 += middlePage;
            }
        }
        System.out.println("Sum Part 1: " + sumOfMiddlePagesPart1);
        System.out.println("Sum Part 2: " + sumOfMiddlePagesPart2);
    }

    public static boolean isValidOrder(ArrayList<Integer> updates, ArrayList<int[]> rules)
    {
        Map<Integer, Integer> indexMap = new HashMap<>();
        for(int i = 0; i < updates.size(); i++)
        {
            indexMap.put(updates.get(i), i);
        }
        for(int[] rule: rules)
        {
            int x = rule[0];
            int y = rule[1];
            if(indexMap.containsKey(x) && indexMap.containsKey(y))
            {
                if(indexMap.get(x) >= indexMap.get(y))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<Integer> correctOrder(ArrayList<Integer> updates, ArrayList<int[]> rules)
    {
        ArrayList<Integer> sorted = new ArrayList<>();
        Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> incomingN = new HashMap<>();

        for (int update : updates)
        {
            graph.put(update, new ArrayList<>());
            incomingN.put(update, 0);
        }

        for(int[] rule: rules)
        {
            int x = rule[0];
            int y = rule[1];
            if(updates.contains(x) && updates.contains(y))
            {
                graph.get(x).add(y);
                incomingN.put(y, incomingN.get(y) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int update: updates) 
        {
            if(incomingN.get(update) == 0)
            {
                queue.add(update);
            }
        }

        while(!queue.isEmpty())
        {
            int current = queue.remove();
            sorted.add(current);

            for (int neighbor : graph.get(current))
            {
                incomingN.put(neighbor, incomingN.get(neighbor) -1);
                if(incomingN.get(neighbor) == 0)
                {
                    queue.add(neighbor);
                }
            }
        }
        return sorted;
    }
}
