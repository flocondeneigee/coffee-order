package com.example.android.happybirthday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.happybirthday.R.id.name_field;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {

        if (numberOfCoffees == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);

    }

    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        displayQuantity(numberOfCoffees);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox WhippedCream = (CheckBox) findViewById(R.id.Whipped_Cream_checkbox);
        Boolean hasTheCream = WhippedCream.isChecked();

        CheckBox Chocolate = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        Boolean hasChocolate = Chocolate.isChecked();

        int price = calculatePrice(hasTheCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasTheCream, hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For" + name);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] { "sailorchien@gmail.com" });
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        int price = numberOfCoffees * basePrice;
        displayMessage("$"+price);
        return price;

    }

    private String createOrderSummary(String name, int price, boolean hasTheCream1, boolean hasChocolate1) {
        String priceMessage = "Name: " + name + "\nAdd Whipped Cream? " + hasTheCream1 + "\nAdd Chocolate? " + hasChocolate1 + "\nQuantity: " + numberOfCoffees + "\nTotal $" + price + "\nThank You!";
        return priceMessage;


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number1) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText(" " + number1);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
        orderSummaryTextView.setText(message);

    }
}