package com.example.final_project_1200105.activites ;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.gallery.OrderDialogFragment;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;
    private FavoritesDatabaseHelper favoritesDatabaseHelper;
    private OrdersDatabaseHelper ordersDatabaseHelper;
    private boolean isFavoritesContext;
    private String userEmail;

    public PizzaAdapter(List<Pizza> pizzaList, Context context, String userEmail) {
        this.pizzaList = pizzaList;
        this.context = context;
        this.userEmail = userEmail;
        favoritesDatabaseHelper = new FavoritesDatabaseHelper(context);
        ordersDatabaseHelper = new OrdersDatabaseHelper(context);
    }

    public void setFavoritesContext(boolean isFavoritesContext) {
        this.isFavoritesContext = isFavoritesContext;
    }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.nameTextView.setText(pizza.getName());

        if (isFavoritesContext) {
            holder.addToFavoritesButton.setVisibility(View.GONE);
            holder.removeFromFavoritesButton.setVisibility(View.VISIBLE);
        } else {
            holder.addToFavoritesButton.setVisibility(View.VISIBLE);
            holder.removeFromFavoritesButton.setVisibility(View.GONE);
        }

        holder.detailsButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("pizza", pizza);
            Navigation.findNavController(v).navigate(R.id.action_nav_Menu_to_pizzaDetailsFragment, bundle);
        });

        holder.addToFavoritesButton.setOnClickListener(v -> {
            addToFavorites(pizza);
        });

        holder.removeFromFavoritesButton.setOnClickListener(v -> {
            removeFromFavorites(pizza, position);
        });

        holder.orderButton.setOnClickListener(v -> {
            showOrderMenu(pizza);
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public void updateList(List<Pizza> newList) {
        pizzaList = newList;
        notifyDataSetChanged();
    }

    public class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button detailsButton;
        Button addToFavoritesButton;
        Button removeFromFavoritesButton;
        Button orderButton;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            addToFavoritesButton = itemView.findViewById(R.id.addToFavoritesButton);
            removeFromFavoritesButton = itemView.findViewById(R.id.removeFromFavoritesButton);
            orderButton = itemView.findViewById(R.id.orderButton);
        }
    }

    private void addToFavorites(Pizza pizza) {
        boolean isAdded = favoritesDatabaseHelper.insertFavorite(userEmail, pizza);
        if (isAdded) {
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Already in favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromFavorites(Pizza pizza, int position) {
        boolean isRemoved = favoritesDatabaseHelper.deleteFavorite(userEmail, pizza.getName());
        if (isRemoved) {
            pizzaList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, pizzaList.size());
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error removing from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrderMenu(Pizza pizza) {
        OrderDialogFragment orderDialog = OrderDialogFragment.newInstance(pizza, userEmail , ordersDatabaseHelper, context);
        orderDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "order_dialog");
    }
}



