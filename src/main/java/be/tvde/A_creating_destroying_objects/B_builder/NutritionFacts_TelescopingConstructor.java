package be.tvde.A_creating_destroying_objects.B_builder;

public class NutritionFacts_TelescopingConstructor {

    private final int servingSize;      //required
    private final int servings;         //required
    private final int calories;         //optional
    private final int fat;              //optional
    private final int sodium;           //optional
    private final int carbohydrate;     //optional

    public NutritionFacts_TelescopingConstructor(int servingSize, int servings) {
        this.servingSize = servingSize;
        this.servings = servings;

        this.calories = -1;
        this.fat = -1;
        this.sodium = -1;
        this.carbohydrate = -1;
    }

    public NutritionFacts_TelescopingConstructor(int servingSize, int servings, int calories) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;

        this.fat = -1;
        this.sodium = -1;
        this.carbohydrate = -1;
    }

    public NutritionFacts_TelescopingConstructor(int servingSize, int servings, int calories, int fat) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;

        this.sodium = -1;
        this.carbohydrate = -1;
    }

    public NutritionFacts_TelescopingConstructor(int servingSize, int servings, int calories, int fat, int sodium) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;

        this.carbohydrate = -1;
    }

    public NutritionFacts_TelescopingConstructor(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
