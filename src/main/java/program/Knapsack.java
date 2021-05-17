package program;

import java.util.Arrays;
import java.util.Collections;
import java.lang.Comparable;

class Item implements Comparable<Item> {
    public int weight;
    public int value;
    public float valueToWeight;
    public Item(int obWeight, int obValue) {
        weight = obWeight;
        value = obValue;
        valueToWeight = value / (float)weight;
    }
    @Override
    public int compareTo(Item other) {
        return (int)(this.valueToWeight - other.valueToWeight);//(valueToWeight.CompareTo(other.valueToWeight));
    }

}
public class Knapsack {
    public boolean[] Greedy(Item[] obj, int amount, int capacity) {
        int i;
        int actualWeight = 0;
        boolean[] put = new boolean[amount];

        for (i = 0; i < amount; i++)
        {
            put[i] = false;
        }

        Arrays.sort(obj, Collections.reverseOrder());
        for (i = 0; i < amount; i++)
        {
            if (obj[i].weight + actualWeight <= capacity)
            {
                put[i] = true;
                actualWeight += obj[i].weight;
            }
        }
        return put;
    }
    public static void main(String[] args) {
        int i;
        RandomNumberGenerator rnd = new RandomNumberGenerator(1);

        //Console.Write("Number of items: ");
        //int n = int.Parse(Console.ReadLine());
        //Console.Write("Knapsack capacity: ");
        //int c = int.Parse(Console.ReadLine());

        int n = rnd.nextInt(5, 29);
        int c = rnd.nextInt(15, 49);

        System.out.println("Number: " + n + "\tCapacity: " + c);

//        int[] v = new int[n]; // values
//        int[] w = new int[n]; // weights
        boolean[] knapsack = new boolean[n]; // 0, 1
        Item[] objects = new Item[n];

        //for (i = 0; i < n; ++i)
        //{
        //     Console.WriteLine("Put value of item " + i + ":");
        //     int value = int.Parse(Console.ReadLine());
        //     Console.WriteLine("Put weight of item " + i + ":");
        //     int weight = int.Parse(Console.ReadLine());
        //     objects[i] = new Item(weight, value);
        //}

        System.out.println("value\tweight");

        for (i = 0; i < n; ++i)
        {
            int value = rnd.nextInt(1, 29);
            int weight = rnd.nextInt(1, 29);
            objects[i] = new Item(weight, value);
            System.out.println("\t" + value + "\t" + weight);
        }

        var kp = new Knapsack();
        knapsack = kp.Greedy(objects, n, c);
        System.out.println("Items in knapsack:\nvalue\tweight");
        for (i = 0; i < n; i++)
        {
            if (knapsack[i] == true)
            {
                System.out.println("\t" + objects[i].value + "\t" + objects[i].weight);
            }
        }

    }
    public static boolean[] Generate(int n, int c, Item[] objects) {
        RandomNumberGenerator rnd = new RandomNumberGenerator(1);
//        int[] v = new int[n]; // values
//        int[] w = new int[n]; // weights
        boolean[] knapsack = new boolean[n]; // 0, 1
        objects = new Item[n];
        for(int i = 0; i < n; ++i) {
            int value = rnd.nextInt(1, 29);
            int weight = rnd.nextInt(1, 29);
            objects[i] = new Item(weight, value);
        }

        var kp = new Knapsack();
        knapsack = kp.Greedy(objects, n, c);
        return knapsack;
    }
}