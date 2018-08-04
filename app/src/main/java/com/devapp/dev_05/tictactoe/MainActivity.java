package com.devapp.dev_05.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                resetGame();
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

        if (checkForWin()){
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        }

        // If there's a draw
        else if (roundCount == 9) {
            draw();
        }

        // If there's no Win or Draw
        else {
            player1Turn = !player1Turn;
        }
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

        return false;
    }

    private void player1Wins() {
        player1Points++; // Increment Player 1's score
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show(); // Show win message
        updatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++; // Increment Player 2's score
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show(); // Show win message
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show(); // Show Draw message
        resetBoard();
    }

    private void updatePointsText() {
        TVplayer1.setText("Player 1: "+ player1Points);
        TVplayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int row = 0; row <3; row++) {
            for (int col = 0; col <3; col++) {
                buttons[row][col].setText(""); // Set text button to zero; empty it
            }
        }

        roundCount = 0; // Reset round count
        player1Turn = true; // Player 1's turn once game has been reset
    }

    private void resetGame() {
        player2Points = 0;
        player1Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
