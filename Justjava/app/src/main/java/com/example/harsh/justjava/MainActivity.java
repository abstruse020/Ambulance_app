package com.example.harsh.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //this method is called when the order button is clicked
    public void submitOrder(View view) {
        int price_pre_cup=5;
        CheckBox whipped_cream=(CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream=whipped_cream.isChecked();
        if(hasWhippedCream)
            price_pre_cup++;

        CheckBox chocolate=(CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();
        if(hasChocolate)
            price_pre_cup+=2;

        int price=calculatePrice(quantity,price_pre_cup);

        EditText name = (EditText) findViewById(R.id.name_field);
        String customerName= name.getText().toString();

        String summary = createOrderSummary(price,hasWhippedCream,hasChocolate,customerName);

        //displayOrderSummary(summary); not showing the order in the app
        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just java orders for" + customerName);
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }
    public void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
//    public void displayOrderSummary(String summary)
//    {
//        TextView priceTextView= (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(summary);
//    }
    public int calculatePrice(int quantity,int price_of_1)
    {
        int price =quantity*price_of_1;
        return price;
    }
    public String createOrderSummary(int price,boolean haswhippedcream,boolean hasChocoate,String name)
    {
        String summary="Name: "+name;
        summary=summary+"\nAdd Whipped Cream? "+ haswhippedcream;
        summary+="\nAdd Chocolate? "+hasChocoate;
        summary=summary+"\nQuantity: "+quantity;
        summary=summary+"\nTotal ";
        summary = summary+NumberFormat.getCurrencyInstance().format(price);
        summary= summary + "\nThank you!";
        return summary;
    }
    public void increment(View view)
    {
        //int quantity=3;
        quantity++;
        display(quantity);
        //displayPrice(quantity);
    }
    public void decrement (View view)
    {
        //int quantity=1;
        if(quantity>0)
            quantity--;
        display(quantity);
        //displayPrice(quantity);
    }

}
