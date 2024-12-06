import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent06 
{
    public static void main(String[] args) 
    {
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
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
                map.add(tmp);
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
            System.exit(1);
        }

        int n = map.size();
        int m = map.get(0).size();
        int startX = findGuardPosition(map)[0];
        int startY = findGuardPosition(map)[1];
        //Part 2

        ArrayList<ArrayList<Integer>> loopCandidates = new ArrayList<>();
        for(int i = 0; i<n; i++)
        {
            for(int j = 0; j<m; j++)
            {
                ArrayList<ArrayList<Character>> tmpMap = createDeepCopy(map);
                if((tmpMap.get(i).get(j).equals('#')) || (i == startX && j == startY))
                {
                    continue;
                }
                tmpMap.get(i).set(j, '#');
                if (causesLoop(tmpMap))
                {
                    ArrayList<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    tmp.add(j);
                    loopCandidates.add(tmp);
                }
                tmpMap.get(i).set(j, '.');
            }
        }
            
        //Part 1
        int[] guardPosition = findGuardPosition(map);
        boolean guardLeft = false;

        while ((guardPosition[0] < n) && (guardPosition[0] >= 0) && (guardPosition[1] < m) && (guardPosition[1] >= 0) && !guardLeft) 
        { 
            Character guardDirection = map.get(guardPosition[0]).get(guardPosition[1]);
            while(!blockedByObstacle(map, guardPosition) && !guardLeft)
            {   
                map.get(guardPosition[0]).set(guardPosition[1], 'X');
                switch (guardDirection) 
                {
                    case '<' -> 
                    {
                        guardPosition[1] = guardPosition[1] - 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '<');
                    }
                    case '>' -> 
                    {
                        guardPosition[1] = guardPosition[1] + 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '>');
                    }
                    case 'v' -> 
                    {
                        guardPosition[0] = guardPosition[0] + 1;
                        map.get(guardPosition[0]).set(guardPosition[1], 'v');
                    }
                    case '^' -> 
                    {
                        guardPosition[0] = guardPosition[0] - 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '^');
                    }
                }
                if((guardPosition[0]+1 >= n) || (guardPosition[0]-1 < 0) || (guardPosition[1]+1 >= m) || (guardPosition[1]-1 < 0))
                {
                    guardLeft = true;
                    map.get(guardPosition[0]).set(guardPosition[1], 'X');
                }
            }
            if(!guardLeft)
            {
                switch (guardDirection) 
                {
                    case '<' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '^');
                    }
                    case '>' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'v');
                    }
                    case 'v' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '<');
                    }
                    case '^' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '>');
                    }
                }
            }

        }

        System.out.println("Part 1 solution: " + countX(map));
        System.out.println("Part 2 solution: " + loopCandidates.size());
    }

    public static int[] findGuardPosition(ArrayList<ArrayList<Character>> map)
    {
        for(int i = 0; i < map.size(); i++)
        {
            for(int j = 0; j < map.get(0).size(); j++)
            {
                if(map.get(i).get(j).equals('^') || map.get(i).get(j).equals('>') || map.get(i).get(j).equals('v') || map.get(i).get(j).equals('<'))
                {
                    return new int[] {i, j};
                }
            }
        }
        System.out.println("Error: No guard position found!");
        return new int[] {0, 0};
    }

    public static boolean blockedByObstacle(ArrayList<ArrayList<Character>> map, int[] guardPosition)
    {
        int x = guardPosition[0];
        int y = guardPosition[1];
        int rows = map.size();
        int columns = map.get(0).size();
        Character guardDirection = map.get(x).get(y);

        if((x + 1 < rows) && (y + 1 < columns) && (x - 1 >= 0) && (y - 1 >= 0))
        {   
            switch (guardDirection) 
            {
                case '<' -> 
                {
                    return map.get(x).get(y-1).equals('#');
                }
                case '>' -> 
                {
                    return map.get(x).get(y+1).equals('#');
                }
                case 'v' -> 
                {
                    return map.get(x+1).get(y).equals('#');
                }
                case '^' -> 
                {
                    return map.get(x-1).get(y).equals('#');
                }
            }
        }
        return false;
    }

    public static int countX(ArrayList<ArrayList<Character>> map)
    {
        int sum = 0;
        for(int i = 0; i < map.size(); i++)
        {
            for(int j = 0; j < map.get(0).size(); j++)
            {
                if(map.get(i).get(j).equals('X'))
                {
                    sum++;
                }
            }
        }
        return sum;
    }

    public static boolean causesLoop(ArrayList<ArrayList<Character>> map)
    {
        int[] guardPosition = findGuardPosition(map);
        char guardDirection = map.get(guardPosition[0]).get(guardPosition[1]);
        Set<List<Object>> visitedStates = new HashSet<>();

        while (true) 
        {
            List<Object> state = Arrays.asList(guardPosition[0], guardPosition[1], guardDirection);
            if(visitedStates.contains(state))
            {
                return true;
            }
            visitedStates.add(state);

            switch (guardDirection) 
            {
                case '<' -> 
                {
                    if(guardPosition[1] - 1 < 0) return false;
                    if(!blockedByObstacle(map, guardPosition))
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'X');
                        guardPosition[1] = guardPosition[1] - 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '<');
                    }
                }
                case '>' -> 
                {
                    if(guardPosition[1] + 1 >= map.get(0).size()) return false;
                    if(!blockedByObstacle(map, guardPosition))
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'X');
                        guardPosition[1] = guardPosition[1] + 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '>');
                    }
                }
                case 'v' -> 
                {
                    if(guardPosition[0] + 1 >= map.size()) return false;
                    if(!blockedByObstacle(map, guardPosition))
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'X');
                        guardPosition[0] = guardPosition[0] + 1;
                        map.get(guardPosition[0]).set(guardPosition[1], 'v');
                    }
                }
                case '^' -> 
                {
                    if(guardPosition[0] - 1 < 0) return false;
                    if(!blockedByObstacle(map, guardPosition))
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'X');
                        guardPosition[0] = guardPosition[0] - 1;
                        map.get(guardPosition[0]).set(guardPosition[1], '^');
                    }
                }
            } 
            if(blockedByObstacle(map, guardPosition))
            {
                switch (guardDirection) 
                {
                    case '<' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '^');
                    }
                    case '>' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], 'v');
                    }
                    case 'v' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '<');
                    }
                    case '^' -> 
                    {
                        map.get(guardPosition[0]).set(guardPosition[1], '>');
                    }
                }
            }
            guardDirection = map.get(guardPosition[0]).get(guardPosition[1]);
        }
    }

    public static ArrayList<ArrayList<Character>> createDeepCopy (ArrayList<ArrayList<Character>> map)
    {
        ArrayList<ArrayList<Character>> copy = new ArrayList<>();
        for (ArrayList<Character> row : map) 
        {
            ArrayList<Character> newRow = new ArrayList<>(row);
            copy.add(newRow);
        }
        return copy;
    }
}