package com.cpxiao.crazy.aa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.crazy.aa.R;
import com.cpxiao.crazy.aa.imps.OnGameListener;
import com.cpxiao.crazy.aa.mode.Extra;
import com.cpxiao.crazy.aa.mode.LevelData;
import com.cpxiao.crazy.aa.views.GameViewWith1Player;
import com.cpxiao.gamelib.activity.BaseActivity;

/**
 * ClassicGameActivity
 *
 * @author cpxiao on 2017/6/6.
 */
public class ClassicGameActivity extends BaseActivity implements OnGameListener {

    private int mLevel = Extra.Key.LEVEL_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_classic);

        if (getIntent() != null) {
            mLevel = getIntent().getIntExtra(Extra.Key.LEVEL, Extra.Key.LEVEL_DEFAULT);
        }

        initWidget();

    }

    private void initWidget() {
        Button titleBarLeftBtn = (Button) findViewById(R.id.title_bar_left_btn);
        titleBarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitle.setText(R.string.btn_classic);


        TextView levelTitleTV = (TextView) findViewById(R.id.sub_title);
        String text = getString(R.string.level) + " " + mLevel;// + " / " + 1200 * (mLevel / 1200 + 1);
        levelTitleTV.setText(text);//设置关卡上限

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_container);
        LevelData data = LevelData.getRandomData(mLevel);
        GameViewWith1Player view = new GameViewWith1Player(this, data);
        view.setOnGameListener(this);
        layout.addView(view);
    }


    @Override
    public void onGameStart() {
        //do nothing
    }

    @Override
    public void onGameOver() {
        ResultActivity.comeToMe(this, Extra.GameMode.CLASSIC, false, mLevel);
        finish();
    }

    @Override
    public void onSuccess() {
        int level = PreferencesUtils.getInt(this, Extra.Key.LEVEL, Extra.Key.LEVEL_DEFAULT);
        if (level <= mLevel) {
            PreferencesUtils.putInt(this, Extra.Key.LEVEL, mLevel);
        }
        ResultActivity.comeToMe(this, Extra.GameMode.CLASSIC, true, mLevel);
        finish();
    }


    public static void comeToMe(Context context, int level) {
        Intent intent = new Intent(context, ClassicGameActivity.class);
        intent.putExtra(Extra.Key.LEVEL, level);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
