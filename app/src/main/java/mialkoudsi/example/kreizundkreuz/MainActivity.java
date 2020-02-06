package mialkoudsi.example.kreizundkreuz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int[] clickedSquareIndicatorArray = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[][] winnerSets = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public enum Player {
        X(1),
        O(2);
        private final int val;

        Player(final int value) {
            val = value;
        }

        public int getVal() {
            return val;
        }
    }

    Player currentPlayer = Player.X;

    boolean gameOver = false;


    public void showXO(View view) {

        ImageView clickedSquareImg = (ImageView) view;
        int clickedSquareTag = Integer.parseInt(clickedSquareImg.getTag().toString());
        if (!gameOver && clickedSquareIndicatorArray[clickedSquareTag] == -1) {
            clickedSquareIndicatorArray[clickedSquareTag] = currentPlayer.getVal();
            if (currentPlayer == Player.X) {
                clickedSquareImg.setImageResource(R.drawable.x_red);
                currentPlayer = Player.O;
            } else {
                clickedSquareImg.setImageResource(R.drawable.o_green);
                currentPlayer = Player.X;
            }
            clickedSquareImg.animate().alpha(1).setDuration(250);
            Button playAgainBtn = (Button) findViewById(R.id.playAgainButton);
            TextView winnerTxtView = (TextView) findViewById(R.id.winnerTextView);

            for (int[] set : winnerSets) {
                if (clickedSquareIndicatorArray[set[0]] == clickedSquareIndicatorArray[set[1]] && clickedSquareIndicatorArray[set[1]] == clickedSquareIndicatorArray[set[2]] && clickedSquareIndicatorArray[set[0]] != -1) {
                    gameOver = true;
                    String winner = currentPlayer == Player.X ? "O" : "X";
                    playAgainBtn.setVisibility(View.VISIBLE);
                    winnerTxtView.setText(winner + " has won!");
                    winnerTxtView.setVisibility(View.VISIBLE);
                    break;
                }
            }
            if (isDraw() && !gameOver) {
                gameOver = true;
                playAgainBtn.setVisibility(View.VISIBLE);
                winnerTxtView.setText("Draw!");
                winnerTxtView.setVisibility(View.VISIBLE);
            }
            if (!gameOver) {
                ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
                TransitionDrawable transition = (TransitionDrawable) constraintLayout.getBackground();
                if (currentPlayer == Player.X)
                    transition.reverseTransition(500);
                else transition.startTransition(500);
            }
        }
    }

    public boolean isDraw() {
        for (int i : clickedSquareIndicatorArray) {
            if (i == -1) {
                return false;
            }
        }
        return true;
    }

    public void playAgain(View view) {

        Button playAgainBtn = (Button) findViewById(R.id.playAgainButton);
        playAgainBtn.setVisibility(View.INVISIBLE);
        TextView winnerTxtView = (TextView) findViewById(R.id.winnerTextView);
        winnerTxtView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView xoImg = (ImageView) gridLayout.getChildAt(i);
            xoImg.setImageDrawable(null);
            clickedSquareIndicatorArray[i] = -1;
        }
        gameOver = false;
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        TransitionDrawable transition = (TransitionDrawable) constraintLayout.getBackground();
        if (currentPlayer == Player.X)
            transition.reverseTransition(500);
        else transition.startTransition(500);
    }

    public void goToGithub(View view){
        String mialkoudsi_github = "https://github.com/mi-alkoudsi/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mialkoudsi_github));
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
