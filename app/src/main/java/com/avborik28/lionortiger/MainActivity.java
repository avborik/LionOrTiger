package com.avborik28.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    enum Player {
        ONE, TWO, NoOne
    }
    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];
    int [][] winnerRowsColumns = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private boolean gameOver = false;
    private Button btnReset;
    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetGrid();

        btnReset = findViewById(R.id.btnReset);
        gridLayout = findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                resetTheGame();
            }
        });
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[tiTag] == Player.NoOne && gameOver == false) {

            tappedImageView.setTranslationX(-2000);

            playerChoices[tiTag] = currentPlayer;
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
            Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]
                        && playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]]
                        && playerChoices[winnerColumns[0]] != Player.NoOne) {

                        btnReset.setVisibility(View.VISIBLE);
                        gameOver = true;
                        String theWinnerOfTheGame = "";
                    if (currentPlayer == Player.ONE) {
                        theWinnerOfTheGame = "The player Two ";
//                    Toast.makeText(this, "Player Two is the winner", Toast.LENGTH_LONG).show();
                    } else if (currentPlayer == Player.TWO) {
                        theWinnerOfTheGame = "The player One ";
//                    Toast.makeText(this, "Player One is the winner", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(this, theWinnerOfTheGame + "is the winner", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // reset button function
    private void resetTheGame(){
        for(int index = 0; index < gridLayout.getChildCount(); index ++){
            ImageView  ImageView = (ImageView) gridLayout.getChildAt(index);
            ImageView.setImageDrawable(null);
            ImageView.setAlpha(0.2f);
        }

        currentPlayer = Player.ONE;
        resetGrid();

        gameOver = false;
        btnReset.setVisibility(View.INVISIBLE);
    }

    public void resetGrid(){
        for(int index = 0; index < playerChoices.length;index++){
            playerChoices[index] = Player.NoOne;
        }
    }
}