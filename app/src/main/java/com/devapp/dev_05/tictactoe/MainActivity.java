package com.devapp.dev_05.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;
    private int player1Points;
    private int player2Points;

    private TextView TVplayer1;
    private TextView TVplayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TVplayer1 = (TextView) findViewById(R.id.text_view_p1);
        TVplayer2 = (TextView) findViewById(R.id.text_view_p2);

        // Loop to go trough each button
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String buttonID = "button_" + row + col;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[row][col] = findViewById(resID);
                buttons[row][col].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) // Check if Button does not contains an empty string
            return;

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        //Increase the round count
        roundCount++;
    }


    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                field[row][col] = buttons[row][col].getText().toString();
            }
        }

        // This: Checking for horizontal wins and also making sure that there isn't an empty field
        for (int row = 0; row < 3; row++) {
            if (field[row][0].equals(field[row][1])
                    && field[row][0].equals(field[row][2])
                    && !field[row][0].equals("")) {
                return true;
            }
        }

        // This: Checking for vertical wins and also making sure that there isn't an empty field
        for (int col = 0; col < 3; col++) {
            if (field[0][col].equals(field[1][col])
                    && field[0][col].equals(field[2][col])
                    && !field[0][col].equals("")) {
                return true;
            }
        }

        // This: Checking for left to right diagonal wins and also making sure that there isn't an empty field
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // This: Checking for right to left diagonal wins and also making sure that there isn't an empty field
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

    }
}
