package kyawthanmong.cfsmart.controller;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import at.markushi.ui.CircleButton;
import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import kyawthanmong.cfsmart.adapter.Survey;
import net.steamcrafted.loadtoast.LoadToast;

import kyawthanmong.cfsmart.AppUtils;
import kyawthanmong.cfsmart.R;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.delegate.InterfacePostServery;
import kyawthanmong.cfsmart.network.POSTSurvey;

public class SurveyActivity extends AppCompatActivity implements InterfacePostServery

{

  private TextView currentStatus, currentSurveyQuestion;
  private NumberProgressBar numberProgressBar;
  CircleButton yesButton, noButton;
  int Counter = 0;
  String post_delaycount = "Some random";
  private Settings settings;

  LoadToast loadToast;
  ArrayList<Integer> answerList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_survey);
    answerList = new ArrayList<>();

    if (!AppUtils.isMondayYet(this)) {
      finish();
    }

    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_from_survey);
    setSupportActionBar(toolbar);
    settings = new Settings(this);
    setTitle("Home");

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setElevation(0);
    }
    this.loadToast = new LoadToast(this);

    shouldSetupView();
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    manager.cancel(1001);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  private void shouldSetupView() {
    currentStatus = (TextView) findViewById(R.id.textViewCurrent);
    currentSurveyQuestion = (TextView) findViewById(R.id.surveyQuestion);
    numberProgressBar = (NumberProgressBar) findViewById(R.id.numberbarForCurrent);
    yesButton = (CircleButton) findViewById(R.id.yes);
    noButton = (CircleButton) findViewById(R.id.no);

    currentSurveyQuestion.setText(SurveyQuestions[0]);
    currentStatus.setText("Question 1 of  12 ");
    numberProgressBar.setMax(12);
    numberProgressBar.setProgress(Counter + 1);
  }

  public void didResponseToQuestion(View view) {
    int i = view.getId();

    String post_answers = getResources().getResourceEntryName(i);

    if (post_answers.equals("no")) {
      answerList.add(0);
    } else {
      answerList.add(1);
    }

    Counter = Counter + 1;
    if (Counter < 14) {

      currentSurveyQuestion.setText(SurveyQuestions[Counter]);
      currentStatus.setText("Question  " + String.valueOf(Counter + 1) + " of 14 ");
      numberProgressBar.setProgress(Counter + 1);
      return;
    }
    yesButton.setEnabled(false);
    noButton.setEnabled(false);
    new POSTSurvey(this, answerList, this);
    loadToast.setText("submitting Questionnaire");
    loadToast.show();
  }

  String[] SurveyQuestions = {
          " In the past week have you had worsening sputum volume or colour?",

          "In the past week have you had new or increased blood in your sputum?",

          "In the past week have you had increased cough?",
          "In the past week have you had new or increased chest pain?",

          "In the past week have you had new or increased wheeze?",

          "In the past week have you had new or increased chest tightness?",

          "In the past week have you had increased shortness of breath or difficulty breathing?",

          "In the past week have you had increased fatigue or lethargy?",

          "In the past week have you had a fever?",

          "In the past week have you had loss of appetite or weight?",

          "In the past week have you had sinus pain or tenderness?",
          "In the past week do you feel that your health has worsened? ",
          "In the past week have you felt low in mood?",

          "In the past week have you felt worried?"
  };

  @Override public void didSucceedPostSurvey() {
    loadToast.success();
    new Survey(this).resetOnSurveyToken();
    finish();
  }

  @Override public void didFailedPostServer() {
    loadToast.error();
    finish();
  }
}
