package com.cpxiao.crazy.aa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.androidutils.library.utils.ThreadUtils;
import com.cpxiao.crazy.aa.R;
import com.cpxiao.crazy.aa.imps.OnGameListener;
import com.cpxiao.crazy.aa.mode.Extra;
import com.cpxiao.crazy.aa.mode.LevelData;
import com.cpxiao.crazy.aa.views.CountDownTextView;
import com.cpxiao.crazy.aa.views.GameViewWith1Player;
import com.cpxiao.gamelib.activity.BaseActivity;

/**
 * TimingGameActivity
 *
 * @author cpxiao on 2017/6/6.
 */
public class TimingGameActivity extends BaseActivity implements OnGameListener {
    private FrameLayout mGameViewLayout;
    private CountDownTextView mCountDownTextView;
    private TextView mScoreTextView;
    private TextView mBestScoreTextView;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_timing);

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
        titleBarTitle.setText(R.string.btn_time);

        initTitle();

        mGameViewLayout = (FrameLayout) findViewById(R.id.game_view_layout);
        initGameView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消倒计时
        mCountDownTextView.setOnTimeUpListener(null);
        mCountDownTextView.destroy();
    }

    private void initGameView() {
        mGameViewLayout.removeAllViews();
        LevelData data = LevelData.getRandomData(mScore);
        GameViewWith1Player view = new GameViewWith1Player(this, data);
        view.setOnGameListener(this);
        mGameViewLayout.addView(view);
    }

    private void initTitle() {
        mScoreTextView = (TextView) findViewById(R.id.score);
        mBestScoreTextView = (TextView) findViewById(R.id.best_score);
        setScoreAndBestScore(getApplicationContext(), 0);

        //        mCountDownTextView = new TimeTextView(this, 30000);
        mCountDownTextView = (CountDownTextView) findViewById(R.id.count_down_text_view);
        mCountDownTextView.setOnTimeUpListener(new CountDownTextView.OnTimeUpListener() {
            @Override
            public void timeUp() {
                // 跳转至结果页
                ResultActivity.comeToMe(getApplicationContext(), Extra.GameMode.TIMING, true, mScore);
                finish();
            }
        });
        //        mCountDownTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_16sp));

    }

    private void setScoreAndBestScore(Context context, int score) {
        int bestScore = PreferencesUtils.getInt(context, Extra.Key.BEST_SCORE, Extra.Key.BEST_SCORE_DEFAULT);
        if (score > bestScore) {
            PreferencesUtils.putInt(context, Extra.Key.BEST_SCORE, score);
            bestScore = score;
        }
        String best = getString(R.string.btn_best_score) + ": " + bestScore;
        mBestScoreTextView.setText(best);
        mScoreTextView.setText(String.valueOf(score));
    }

    public static void comeToMe(Context context) {
        Intent intent = new Intent(context, TimingGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onGameStart() {
        mCountDownTextView.start();
    }

    @Override
    public void onGameOver() {
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 跳转至结果页
                ResultActivity.comeToMe(getApplicationContext(), Extra.GameMode.TIMING, false, 0);
                finish();
            }
        });
    }

    @Override
    public void onSuccess() {
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mScore++;
                setScoreAndBestScore(getApplicationContext(), mScore);
                // 生成新game View
                initGameView();
            }
        });
    }

}
