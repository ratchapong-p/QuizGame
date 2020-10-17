package th.ac.su.cp.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.cp.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView ShowScore;
    private ImageView QuestionImgView;
    private Button[] Buttons=new Button[4];
    private String AnsWord;
    private  Random Rand;
    private  List<WordItem> ItemList;
    private int Score =0;
    private int Count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ShowScore=findViewById(R.id.text_score);
        ShowScore.setText(Count+" คะแนน");

        QuestionImgView = findViewById(R.id.qustion_image_view);
        Buttons[0]=findViewById(R.id.chioce_1_button);
        Buttons[1]=findViewById(R.id.chioce_2_button);
        Buttons[2]=findViewById(R.id.chioce_3_button);
        Buttons[3]=findViewById(R.id.chioce_4_button);

        Buttons[0].setOnClickListener(this);
        Buttons[1].setOnClickListener(this);
        Buttons[2].setOnClickListener(this);
        Buttons[3].setOnClickListener(this);

        Rand = new Random();

        newQuiz(ItemList,Rand);

    }

    private void newQuiz(List<WordItem> ItemList, Random Rand) {

        ItemList =new ArrayList<>(Arrays.asList(WordListActivity.items)) ;
        int answerIndex = Rand.nextInt(ItemList.size());
        WordItem item = ItemList.get(answerIndex);
        QuestionImgView.setImageResource(ItemList.get(answerIndex).imageResId);
        AnsWord=item.word;
        int randomButton =Rand.nextInt(4);
        Buttons[randomButton].setText(item.word);

        ItemList.remove(item);

        Collections.shuffle(ItemList);


        for(int i=0;i<4;i++){
                if(i == randomButton){
                    continue;
                }
                Buttons[i].setText(ItemList.get(i).word);
        }
    }

    @Override
    public void onClick(View view) {
       Button b=findViewById(view.getId()) ;
       String buttonText = b.getText().toString();
        Count++;
        Log.i("Countcheck", "Count: " + Count);


       if(buttonText.equals(AnsWord)){
           Toast.makeText(GameActivity.this,"ถูกต้องนะครับ",Toast.LENGTH_SHORT).show();
                        Score++;
           Log.i("Countcheck", "Score: " + Score);
           ShowScore.setText( Score+" คะแนน");
       }else{
           Toast.makeText(GameActivity.this,"ผิดนะครับ",Toast.LENGTH_SHORT).show();
       }
       newQuiz(ItemList,Rand);
        if(Count>=5){
            AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
            dialog.setTitle("สรุปผล");
            dialog.setMessage("คุณได้ "+String.valueOf(Score)+" คะแนน\n\n"+"ต้องการเล่นใหม่หรือไม่");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Count=0;
                    Score=0;
                    ShowScore.setText(Score+" คะแนน");
                    newQuiz(ItemList,Rand);
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }
    }

}