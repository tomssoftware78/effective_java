package be.tvde.A_creating_destroying_objects.B_builder;

public class App {

    public static void main(String[] args) {
        NutritionFacts nf = new NutritionFacts.Builder(240, 8)
                .calories(100)
                .sodium(35)
                .build();
    }
}
