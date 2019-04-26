package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recyclerview.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Food> litems;
    private RecyclerView mRecycleView;
    private FoodListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilizeData();

        mRecycleView = findViewById(R.id.rvFoodLst);
        mAdapter = new FoodListAdapter(this,litems);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initilizeData(){
        litems = new ArrayList<>();

        litems.add(new Food("Kale/Lemon Sandwiches","This sandwich is stunningly delicious and tastes as good as it is healthy.\n" +
                "Toast bread well.If using a very dense bread or a rye or pumpernickel double or even triple toast.","Ingredients \n"+ "4 slices whole grain bread \n"
                +"1 bunch of kale or chard, center rib removed and chopped in bite sized pieces (4 cups or more) \n"
                +"1/4-1/3 cup hummus \n"
                +"a few green onions, light green parts only (save the rest for soup), sliced thin \n"
                +"2 tbsp chopped cilantro or parsley leaves \n"
                +"1 lemon \n"
                +"Finishing oil (sunflower or olive) \n"
                +"salt and pepper \n"
                +"1 large tomato, sliced in 4 thick slices (optional)", "Procedure \n"+"Preheat the oven to 375°F. \n"
                +"Peel the apples if you'd like (I often don't). \n"
                +"Core and slice the apples and mix with lemon juice. \n"
                +"Combine brown sugar, cinnamon and nutmeg. Add to apples and toss. \n"
                +"Combine flour, sugar and oats. \n"
                +"Cut butter into small pieces and cut into flour until crumbly. Stir in nuts.",""));
        litems.add(new Food("Mango-Lime Bean Salad","Put kale in a pot with 3 to 4 inches of water in the bottom.\n" +
                "        Bring to a boil, cover, and cook until tender, 3 to 5 minutes; check frequently.\n" +
                "        Kale is good when cooked to an almost spinachlike tenderness.","Ingredients \n"+ "4 slices whole grain bread \n" +
                "                1 bunch of kale or chard, center rib removed and chopped in bite sized pieces (4 cups or more) \n" +
                "                1/4-1/3 cup hummus \n" +
                "                a few green onions, light green parts only (save the rest for soup), sliced thin \n" +
                "                2 tbsp chopped cilantro or parsley leaves \n" +
                "                1 lemon \n" +
                "                Finishing oil (sunflower or olive) \n" +
                "                salt and pepper \n" +
                "                1 large tomato, sliced in 4 thick slices (optional)","testing proc",""));
        litems.add(new Food("Sweet Potato and Lentil Soup with Shiitake Mushrooms","Spread the toast thickly with hummus, sprinkle with green onions,\n" +
                "        pile cilantro on top of the green onions, and then place lemon slices over the cilantro","","",""));
        litems.add(new Food("Lime Mousse","Cut the ends off the lemon, zest them, then squeeze the juice from the ends into a bowl with the zest.\n" +
                "        Discard the ends. VERY thinly slice the main part of the lemon and set aside.","","",""));
        litems.add(new Food("Broiled Tilapia Parmesan","If using a very dense bread or a rye or pumpernickel double or even triple toast.\n" +
                "        Make it almost crackerlike.Cut the ends off the lemon, zest them, then squeeze the juice from the ends into a bowl with\n" +
                "        the zest.","","",""));
    }
}
