package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean isPlayerX = true;
    private int roundCount = 0;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                int finalI = i;
                int finalJ = j;

                buttons[i][j].setOnClickListener(view -> onButtonClick(finalI, finalJ));
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int row, int col) {
        Button button = buttons[row][col];

        if (!button.getText().toString().equals("")) return;

        button.setText(isPlayerX ? "X" : "O");
        roundCount++;

        if (checkWinner()) {
            statusText.setText("Player " + (isPlayerX ? "X" : "O") + " wins!");
            disableBoard();
        } else if (roundCount == 9) {
            statusText.setText("It's a draw!");
        } else {
            isPlayerX = !isPlayerX;
            statusText.setText("Player " + (isPlayerX ? "X" : "O") + "'s turn");
        }
    }

    private boolean checkWinner() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = buttons[i][j].getText().toString();

        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals("") &&
                    board[i][0].equals(board[i][1]) &&
                    board[i][0].equals(board[i][2])) return true;
            if (!board[0][i].equals("") &&
                    board[0][i].equals(board[1][i]) &&
                    board[0][i].equals(board[2][i])) return true;
        }

        return (!board[0][0].equals("") && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) ||
                (!board[0][2].equals("") && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]));
    }

    private void disableBoard() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setEnabled(false);
    }

    private void resetGame() {
        for (Button[] row : buttons)
            for (Button b : row) {
                b.setText("");
                b.setEnabled(true);
            }

        isPlayerX = true;
        roundCount = 0;
        statusText.setText("Player X's turn");
    }
}
