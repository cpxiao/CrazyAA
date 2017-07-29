package com.cpxiao.crazy.aa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cpxiao.R;
import com.cpxiao.crazy.aa.mode.Extra;
import com.cpxiao.gamelib.activity.BaseActivity;


/**
 * ResultActivity
 *
 * @author cpxiao on 2017/6/6.
 */
public class ResultActivity extends BaseActivity implements View.OnClickListener {

    private int mGameMode = Extra.GameMode.CLASSIC;
    private static final String EXTRA_NAME_IS_SUCCESS = "EXTRA_NAME_IS_SUCCESS";
    private boolean isSuccess = false;
    private int mLevel = 0;

    private TextView mGameModeTV;
    private TextView mTitleTV;
    private TextView mSubTitleTV;
    private Button mPlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initWidget();
        initWidgetData();
        initAdMobAds50("ca-app-pub-4157365005379790/2403330318");
    }

    private void initWidget() {
        mGameModeTV = (TextView) findViewById(R.id.game_mode);
        mTitleTV = (TextView) findViewById(R.id.title);
        mSubTitleTV = (TextView) findViewById(R.id.sub_title);

        mPlayBtn = (Button) findViewById(R.id.play_btn);
        mPlayBtn.setOnClickListener(this);
    }

    private void initWidgetData() {
        if (getIntent() != null) {
            mGameMode = getIntent().getIntExtra(Extra.Name.GAME_MODE, Extra.GameMode.CLASSIC);
            isSuccess = getIntent().getBooleanExtra(EXTRA_NAME_IS_SUCCESS, false);
            mLevel = getIntent().getIntExtra(Extra.Name.LEVEL, 1);
        }

        if (mGameMode == Extra.GameMode.CLASSIC) {
            mGameModeTV.setText(R.string.btn_classic);
            if (isSuccess) {
                mTitleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_success));
                mTitleTV.setText(R.string.success);
                mPlayBtn.setText(R.string.next_level);
            } else {
                mTitleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_fail));
                mTitleTV.setText(R.string.fail);
                mPlayBtn.setText(R.string.retry_level);
            }
            String levelMsg = getString(R.string.level) + " " + mLevel;
            mSubTitleTV.setText(levelMsg);
        } else if (mGameMode == Extra.GameMode.TIMING) {
            mGameModeTV.setText(R.string.btn_time);
            if (isSuccess) {
                mTitleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_success));
                mTitleTV.setText(R.string.success);
                String scoreMsg = getString(R.string.score) + ": " + mLevel;
                mSubTitleTV.setText(scoreMsg);

            } else {
                mTitleTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_fail));
                mTitleTV.setText(R.string.fail);
                mSubTitleTV.setVisibility(View.INVISIBLE);
            }
            mPlayBtn.setText(R.string.play_again);
        }
    }

    /**
     * @param context   context
     * @param gameMode  游戏模式：经典模式、计时模式
     * @param isSuccess 是否成功
     * @param level     关卡或分数
     */
    public static void comeToMe(Context context, int gameMode, boolean isSuccess, int level) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(Extra.Name.GAME_MODE, gameMode);
        intent.putExtra(EXTRA_NAME_IS_SUCCESS, isSuccess);
        intent.putExtra(Extra.Name.LEVEL, level);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.play_btn) {
            if (mGameMode == Extra.GameMode.TIMING) {
                TimingGameActivity.comeToMe(ResultActivity.this);
            } else if (mGameMode == Extra.GameMode.CLASSIC) {
                if (isSuccess) {
                    ClassicGameActivity.comeToMe(ResultActivity.this, mLevel + 1);
                } else {
                    ClassicGameActivity.comeToMe(ResultActivity.this, mLevel);
                }
            }
        }

        finish();
    }
}
