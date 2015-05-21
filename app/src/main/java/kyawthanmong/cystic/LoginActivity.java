package kyawthanmong.cystic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;


public class LoginActivity extends ActionBarActivity {

    private MaterialEditText userId, userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shouldSetupView();
    }


    private void shouldSetupView() {

        userId = (MaterialEditText) findViewById(R.id.userId);
        userPassword = (MaterialEditText) findViewById(R.id.password);


    }


    public void didClickOnSignIn(View view)
    {

    }

}
