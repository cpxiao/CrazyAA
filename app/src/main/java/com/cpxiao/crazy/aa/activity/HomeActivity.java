package com.cpxiao.crazy.aa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.crazy.aa.R;
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
    }

    private void initWidget() {

        Button classic = (Button) findViewById(R.id.btn_classic);
        classic.setOnClickListener(this);

        Button time = (Button) findViewById(R.id.btn_time);
        time.setOnClickListener(this);

        Button _2player = (Button) findViewById(R.id.btn_2player);
        _2player.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_classic) {
            int level = PreferencesUtils.getInt(this, Extra.Key.LEVEL, Extra.Key.LEVEL_DEFAULT);
            ClassicGameActivity.comeToMe(this, level);
        } else if (id == R.id.btn_time) {
            TimingGameActivity.comeToMe(this);
        } else if (id == R.id.btn_2player) {
            TwoPlayerGameActivity.comeToMe(this);
        }
    }
}
