package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerview.model.Food;

public class FoodDetailActivity extends AppCompatActivity {
    Food food;
    ImageView ivFoodImage;
    TextView tvIngre;
    TextView tvProc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        ivFoodImage = findViewById(R.id.FoodImage);
        tvIngre = findViewById(R.id.ingred);
        tvProc = findViewById(R.id.proc);


        Bundle extra = getIntent().getExtras();
        if (extra !=null){
            food = (Food)extra.getSerializable("Food");
            Log.d("lentos",food.getProcedure());
            this.setTitle(food.getTitle());
            tvIngre.setText(food.getIngredients());
            tvProc.setText(food.getProcedure());
            ivFoodImage.setImageResource(R.drawable.kale);

        }
    }
}
