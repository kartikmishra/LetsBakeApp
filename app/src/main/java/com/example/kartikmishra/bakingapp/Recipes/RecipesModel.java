package com.example.kartikmishra.bakingapp.Recipes;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipesModel implements Parcelable{

    int id;
    String name;
    String ingredient;
    String measure;
    double quantity;
    int servings;


    public RecipesModel() {
    }

    public RecipesModel(int id, String name, String ingredient, String measure, double quantity, int servings) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    protected RecipesModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredient = in.readString();
        measure = in.readString();
        quantity = in.readDouble();
        servings = in.readInt();
    }

    public static final Creator<RecipesModel> CREATOR = new Creator<RecipesModel>() {
        @Override
        public RecipesModel createFromParcel(Parcel in) {
            return new RecipesModel(in);
        }

        @Override
        public RecipesModel[] newArray(int size) {
            return new RecipesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(ingredient);
        dest.writeString(measure);
        dest.writeDouble(quantity);
        dest.writeInt(servings);
    }
}
