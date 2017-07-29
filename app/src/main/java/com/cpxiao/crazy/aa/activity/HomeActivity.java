package com.cpxiao.crazy.aa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.crazy.aa.mode.Extra;
import com.cpxiao.gamelib.activity.BaseActivity;


/**
 * HomeActivity
 *
 * @author cpxiao on 2017/6/6.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidget();
        initAdMobAds50("ca-app-pub-4157365005379790/8066720531");
    }

    private void initWidget() {

        Button classic = (Button) findViewById(R.id.btn_classic);
        classic.setOnClickListener(this);

        Button time = (Button) findViewById(R.id.btn_time);
        time.setOnClickListener(this);

        Button twoPlayers = (Button) findViewById(R.id.btn_two_players);
        twoPlayers.setOnClickListener(this);

        Button settings = (Button) findViewById(R.id.btn_settings);
        settings.setVisibility(View.GONE);

        Button quit = (Button) findViewById(R.id.btn_quit);
        quit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_classic) {
            int level = PreferencesUtils.getInt(this, Extra.Key.LEVEL, Extra.Key.LEVEL_DEFAULT);
            ClassicGameActivity.comeToMe(this, level);
        } else if (id == R.id.btn_time) {
            TimingGameActivity.comeToMe(this);
        } else if (id == R.id.btn_two_players) {
            TwoPlayerGameActivity.comeToMe(this);
        } else if (id == R.id.btn_quit) {
            finish();
        }
    }
}
