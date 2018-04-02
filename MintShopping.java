package win;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*

@TODO    o How do you want to test your code?

             - We can start with the given input set.
             - Tests like  What if the sum of smallest two numbers > some other two number   ? Is it still working correctly ?
             - If all the combinations are being covered or not
                - for ^ we can store the expected output in a List and then keep removing elements from the list which match input.
                  If its empty at the end that means we have done it right if not then we might have missed something.

@TODO     o What changes you want to make, in order to get your code ready for production?

             -We can deploy this to aws lambda and then provide a API to calculate the same. Maybe take input in Json.
             -Create Custom Class for the complex data types being used in the program.


@TODO     o What if I want to remove 3 items, if there are no 2 items that satisfy the requirement?

             -We can sort the list on the basis of value and then start fixing each element 1 by one and taking the other two elements by going over rest of the window
               Or we can use the below mentioned approach.
@TODO     o What if I want to remove K items?

             -We can use backtracking and check for length while using maxYet to get only the minimum sum set.

@TODO     o I don’t have number of item limit, show me all the possible combinations of items I can remove to lower my budget.

             -We can use backtracking to generate all the possible combinations
*/


public class MintShopping
{
    static Set<List<Map.Entry<String, Integer>>> finalOutput = new HashSet<>();
    static int maxYet = Integer.MAX_VALUE;

    public static void main(String[] args)
    {
        HashMap<String, Integer> inputMap = new HashMap<String, Integer>();
        inputMap.put("Backpack", 61);
        inputMap.put("Monitor", 100);
        inputMap.put("arc", 110);
        inputMap.put("Water bottle", 10);
        inputMap.put("Tent", 150);
        inputMap.put("Headphone", 150);
        System.out.print(getCombinationsOfTwo(inputMap, 1200, 1000));
        System.out.println(getCombinationsMoreThanTwo(inputMap, 1200, 1000, 2, true));
    }

    /**
     * This method is intended for case in which we only want to remove two elements
     *
     * 1- Calculate margin = currentBudget - newBudget
     *
     * 2- Store HashMap entries in a List 'details'. We do this in order to access both the name and value when we want
     * to return the final output
     *
     * 3- We then sort 'details' using comparator on the basis of its values
     *
     * 4- We then use binary search approach to find the best pair. Also, we ensure that the pair with the minimum value
     * is returned by keeping track of the sum of pairs using maxYet & using firstValue to get first value from details,
     * second value to get secondValue and sum to calculate sum of both.
     *
     * @param items         input received
     * @param currentBudget
     * @param newBudget
     * @return
     */
    static public String getCombinationsOfTwo(HashMap<String, Integer> items, int currentBudget, int newBudget)
    {
        // 1- Calculate margin = currentBudget - newBudget
        int margin = currentBudget - newBudget;


        // 2- Store HashMap entries in a List 'details'. We do this in order to access both the name and value when we want to return the final output
        List<Map.Entry<String, Integer>> details = new LinkedList<Map.Entry<String, Integer>>(items.entrySet());

        // 3- We then sort 'details' using comparator on the basis of its values
        Collections.sort(details, (o1, o2) -> o1.getValue() - o2.getValue());


        String output = "";
        int maxYet = Integer.MAX_VALUE;

        //4- We then use binary search approach to find the best pair.
        for (int start = 0, end = details.size() - 1; start < end; )
        {

            int firstValue = details.get(start).getValue();
            int secondValue = details.get(end).getValue();


            if (firstValue + secondValue > margin)
            {
                end--;
            }
            else
            {
                start++;
            }


            if (start == end)
            {
                break;
            }

            //updating values

            firstValue = details.get(start).getValue();
            secondValue = details.get(end).getValue();
            int sum = firstValue + secondValue;

            if (sum >= margin && sum < maxYet)
            {
                maxYet = sum;
                output = details.get(start) + "," + details.get(end);
            }
        }

        return output;
    }

    /**
     * This method will be used in case we want to find combinations of size more than 2 or in this case 'k'
     * Or all combinations
     * It uses combine method for recursion
     *
     * @param items
     * @param currentBudget
     * @param newBudget
     * @param k
     * @param getAll        Set to true if all the combinations are required, if set to false only the minimum result
     *                      will be displayed
     * @return
     */
    static public Set<List<Map.Entry<String, Integer>>> getCombinationsMoreThanTwo(HashMap<String, Integer> items, int currentBudget, int newBudget, int k, Boolean getAll)
    {
        // taking care of getting margin
        int margin = currentBudget - newBudget;
        List<Map.Entry<String, Integer>> details = new LinkedList<>(items.entrySet());
        combine(new ArrayList<Integer>(), 0, items.size(), k, details, margin, getAll);

        return finalOutput;
    }

    /**
     * 1- We use back tracking approach to first add one element then generate all its possible combinations till there
     * are no more combinations possible
     * 2- We then remove it and add another element.
     * 3- We keep doing this until all elements have been covered.
     * 4- Before adding to the final output Set finalOutput we sort the combination so that it won't be repeated ,
     * although since we are using backtracking its not really required.
     *
     * @param combination
     * @param start
     * @param end
     * @param size
     * @param details
     * @param margin
     * @param getAll
     */
    public static void combine(List<Integer> combination, int start, int end, int size, List<Map.Entry<String, Integer>> details, int margin, Boolean getAll)
    {
        // if the size is reset than that means we have a combination of desired length
        // check if we getAll is true to ensure we want just the minimum and not all combinations
        if (size == 0 && getAll != true)
        {
            int sum = 0;
            List<Map.Entry<String, Integer>> entries = new LinkedList<>();
            for (int i : combination)
            {
                sum += details.get(i).getValue();
                entries.add(details.get(i));
            }
            if (sum >= margin && sum < maxYet)
            {
                maxYet = sum;

                finalOutput = new HashSet<>(new LinkedList(entries));
            }
            return;
        }
        //if we want all just keep adding at every point
        else if (getAll == true)
        {
            int sum = 0;
            List<Map.Entry<String, Integer>> entries = new LinkedList<>();
            for (int i : combination)
            {
                sum += details.get(i).getValue();
                entries.add(details.get(i));
            }
            if (sum >= margin)
            {
                Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
                finalOutput.add(entries);
            }
        }
        for (int i = start; i < end; i++)
        {
            //add
            combination.add(i);
            //recurse & compute
            combine(combination, i + 1, end, size - 1, details, margin, getAll);
            //remove
            combination.remove(combination.size() - 1);
        }
    }
}