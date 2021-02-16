package tugas.lab.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, winningStatus;
    private final Button [] buttons = new Button[9];
    private Button btn_reset;

    private int scoreP1, scoreP2, rount;
    boolean playerTurn;

    //Empty     >> 0
    //Player 1  >> 1
    //Player 2  >> 2
    int [] gameState = {0,0,0,0,0,0,0,0,0};

    int [][] winningPosotions = {
            {0,1,2}, {3,4,5}, {6,7,8},  //rows
            {0,3,6}, {1,4,7}, {2,5,8},  //column
            {0,4,8}, {2,4,6},           //cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        winningStatus = (TextView) findViewById(R.id.winningStatus);

        btn_reset = (Button) findViewById(R.id.btn_reset);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID  = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button)findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rount = 0;
        scoreP1 = 0;
        scoreP2 = 0;
        playerTurn = true;

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (playerTurn) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFFFFF"));
            gameState[gameStatePointer] = 1;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#870B0B"));
            gameState[gameStatePointer] = 2;
        }
        rount++;

        if(checkWinner()){
            if(playerTurn){
                scoreP1++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                scoreP2++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rount == 9){
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        }else{
            playerTurn = !playerTurn;
        }

        if(scoreP1 > scoreP2){
            winningStatus.setText("Player One is Winning!");
        }else if(scoreP2 > scoreP1){
            winningStatus.setText("Player Two is Winning!");
        }else{
            winningStatus.setText("");
        }

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                scoreP1 = 0;
                scoreP2 = 0;
                winningStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int[] winningPosition : winningPosotions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] !=0){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(scoreP1));
        playerTwoScore.setText(Integer.toString(scoreP2));
    }

    public void playAgain(){
        rount  = 0;
        playerTurn = true;

        for (int i = 0; i < buttons.length; i++){
            gameState[i] = 0;
            buttons[i].setText("");
        }
    }
}