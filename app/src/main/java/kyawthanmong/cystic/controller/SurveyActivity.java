package kyawthanmong.cystic.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import net.steamcrafted.loadtoast.LoadToast;

import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.delegate.InterfacePostServery;
import kyawthanmong.cystic.network.POSTSurvey;


public class SurveyActivity extends ActionBarActivity implements InterfacePostServery

{

    private TextView currentStatus, currentSurveyQuestion;
    private NumberProgressBar numberProgressBar;
    Button yesButton, noButton;
    int Counter = 0;
    String post_delaycount = "Some random";
    private Settings settings ;

    String post_choices ;
    String post_answers ;
    LoadToast loadToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);


        if (!AppUtils.isMondayYet(this)){
            finish();
        }
        settings = new Settings(this);
        if (!settings.getSurveyAvailableStatus()){
            finish();
        }
        setTitle("Survey");
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);

        }
        this.loadToast = new LoadToast(this);

        shouldSetupView();
    }

    private void shouldSetupView() {
        currentStatus               = (TextView) findViewById(R.id.textViewCurrent);
        currentSurveyQuestion       = (TextView) findViewById(R.id.surveyQuestion);
        numberProgressBar           = (NumberProgressBar) findViewById(R.id.numberbarForCurrent);
        yesButton                   = (Button) findViewById(R.id.yes);
        noButton                    = (Button) findViewById(R.id.no);



        currentSurveyQuestion.setText(SurveyQuestions[0]);
        currentStatus.setText("Question 1 of up to 12 ");
        numberProgressBar.setMax(12);
        numberProgressBar.setProgress(Counter + 1);

    }


    public void didResponseToQuestion(View view){
        int i = view.getId();

        if (Counter == 0) {
            post_answers = getResources().getResourceEntryName(i);
            post_choices = String.valueOf(Counter + 1);
        }
        else
        {
            post_answers = post_answers + " , " + getResources().getResourceEntryName(i);
            post_choices = post_choices + " ," + String.valueOf(Counter + 1);
        }

        Counter = Counter + 1;
        if (Counter < 12 ){

            currentSurveyQuestion.setText(SurveyQuestions[Counter]);
            currentStatus.setText("Question  " + String.valueOf(Counter + 1) + " of up to 12 ");
            numberProgressBar.setProgress(Counter + 1);
            return;

        }
        Log.i(post_answers, post_choices);
        new POSTSurvey(this, post_choices, post_answers, "", this);
        loadToast.setText("Posting Survey");
        loadToast.show();




    }



    String [] SurveyQuestions = {
            "In the past week have you had\t a change in sputum volume or colour",
            "In the past week have you had\t new or increased blood in your sputum",
            "In the past week have you had\t increased cough",
            "In the past week have you had\t increased wheeze",
            "In the past week have you had\t increased shortness of breath",
            "In the past week have you had\t increased fatigue or lethargy",
            "In the past week have you had\t a fever",
            "In the past week have you had\t a loss of appetite or weight",
            " In the past week have you had\t sinus pain or tenderness",
            "In the past week have you had\t new or increased chest pain",
            "In the past week have\t you felt low in mood",
            "In the past week have\t felt worried"


    };


    @Override
    public void didSucessedPostServey() {
        loadToast.success();
        settings.setSurveyAvailableStatus(false);
        finish();

    }

    @Override
    public void didFailedPostServer() {
        loadToast.error();

    }
}
