package com.example.p03session2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    EditText pax;
    EditText discount;
    ToggleButton svs;
    double svsAmount = 0;
    ToggleButton gst;
    double gstAmount = 0;
    RadioGroup payment;
    Button split;
    Button reset;
    TextView totalBill;
    TextView eachPay;
    double discountDouble = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editTextAmount);
        pax = findViewById(R.id.editTextPax);
        discount = findViewById(R.id.editTextDiscount);
        svs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);
        payment = findViewById(R.id.radioGroupPayment);
        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);
        totalBill = findViewById(R.id.textViewTotalBill);
        eachPay = findViewById(R.id.textViewEachPay);


        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().isEmpty()||pax.getText().toString().isEmpty()||discount.getText().toString().isEmpty()||payment.getCheckedRadioButtonId()==-1){
                    Context context = getApplicationContext();
                    CharSequence text = "error";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                if(gst.isChecked()){
                    gstAmount = 0.07;
                }else{
                    gstAmount = 0;
                }
                if(svs.isChecked()){
                    svsAmount = 0.1;
                }else{
                    svsAmount = 0;
                }
                discountDouble = Double.parseDouble(discount.getText().toString());
                calcTotalAmount();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });
    }
    public void calcTotalAmount(){
        double total;
        total = Double.parseDouble(amount.getText().toString());
        System.out.println(svsAmount);
        System.out.println(gstAmount);
        total = total*(1+svsAmount)*(1+gstAmount)*(1-(discountDouble/100));
        String totalString;
        double eachPayDouble = total/Integer.parseInt(pax.getText().toString());
        totalString = String.format("%.2f",total);
        String eachPayString = String.format("%.2f",eachPayDouble);
        totalBill.setText("Total Bill: "+totalString);
        eachPay.setText("Each Pays: $"+eachPayString);
        int paymentSelected = payment.getCheckedRadioButtonId();
        if(paymentSelected==R.id.radioButtonPaynow){
            eachPay.append(" via PayNow to 912345678");
        }else if(paymentSelected==R.id.radioButtonCash){
            eachPay.append(" in Cash");
        }
    }
    public void clearForm(){
        amount.setText("");
        pax.setText("");
        discount.setText("");
        totalBill.setText("");
        eachPay.setText("");
        payment.clearCheck();
        svs.setChecked(false);
        gst.setChecked(false);
    }
}