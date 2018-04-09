package zhengc.bcit.ca.benehome;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Eligible extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnCheck;
    final Context context = this;
    private static final String TAG = MainActivity.class.getName();
    private Spinner anwser1, anwser2, anwser3, anwser4, anwser5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible);

        btnBack = (ImageView) findViewById(R.id.btn_back_eligi);

        setSpinner1();
        setSpinner2();
        setSpinner3();
        setSpinner4();
        setSpinner5();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnBack onclick on eligi");
                Intent intent = new Intent();
                intent.setClass(Eligible.this, MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnBack onClick on eligi");
            }
        });

        btnCheck = (Button) findViewById(R.id.btn_check_eligi);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                AlertDialog.Builder alertChk = new AlertDialog.Builder(context);
                alertChk.setTitle("Congratulations");
                alertChk.setMessage("You are eligible to apply!")
                        .setCancelable(false)
                        .setPositiveButton("Continue",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                //Eligible.this.finish();
                                Intent intent = new Intent();
                                intent.setClass(Eligible.this,House_list.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertChk.create();
                alertDialog.show();
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });

    }

    private void setSpinner1() {
        anwser1 = (Spinner) findViewById(R.id.spr_ans_eligi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.yesNo,android.R.layout.simple_spinner_item);
        anwser1.setAdapter(adapter);
    }
    private void setSpinner2() {
        anwser2 = (Spinner) findViewById(R.id.spr_ans2_eligi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.age,android.R.layout.simple_spinner_item);
        anwser2.setAdapter(adapter);
    }

    private void setSpinner3() {
        anwser3 = (Spinner) findViewById(R.id.spr_ans3_eligi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.yesNo,android.R.layout.simple_spinner_item);
        anwser3.setAdapter(adapter);
    }

    private void setSpinner4() {
        anwser4 = (Spinner) findViewById(R.id.spr_ans4_eligi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.yesNo,android.R.layout.simple_spinner_item);
        anwser4.setAdapter(adapter);
    }

    private void setSpinner5() {
        anwser5 = (Spinner) findViewById(R.id.spr_ans5_eligi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.yesNo,android.R.layout.simple_spinner_item);
        anwser5.setAdapter(adapter);
    }
}
