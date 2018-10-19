package jason.tcpdemo.funcs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import jason.tcpdemo.R;

/**
 * Created by Chao on 2018-03-16.
 */

public class FuncWelcome extends Activity {
    private String TAG = "FuncWelcome";
    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.welcomelayout);
        //context = this;

    }

}
