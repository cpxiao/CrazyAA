package com.cpxiao.crazy.aa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.cpxiao.androidutils.library.utils.ThreadUtils;
import com.cpxiao.gamelib.activity.BaseActivity;
import com.cpxiao.crazy.aa.R;
import com.cpxiao.crazy.aa.imps.OnTwoPlayerGameListener;
import com.cpxiao.crazy.aa.mode.LevelData;
import com.cpxiao.crazy.aa.views.GameViewWith2Player;
import com.cpxiao.crazy.aa.views.RotateTextView;


/**
 * TwoPlayerGameActivity
 *
 * @author cpxiao on 2017/6/6.
 */

public class TwoPlayerGameActivity extends BaseActivity implements OnTwoPlayerGameListener {

    /**
     * 多少分一局
     */
    private static final int SCORE_OF_END = 7;
    private FrameLayout mGameViewLayout;

    private int mScoreTop = 0, mScoreBottom = 0;
    private RotateTextView mScoreView;
    private RotateTextView mTopPlayerMsgView;
    private RotateTextView mBottomPlayerMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_2player);

        mTopPlayerMsgView = (RotateTextView) findViewById(R.id.top_player_msg);
        mTopPlayerMsgView.setText("");
        mBottomPlayerMsgView = (RotateTextView) findViewById(R.id.bottom_player_msg);
        mBottomPlayerMsgView.setText("");

        mScoreView = (RotateTextView) findViewById(R.id.score_text_view);
        setScore();

        mGameViewLayout = (FrameLayout) findViewById(R.id.game_view_layout);
        initGameView();

    }

    private void setScore() {
        String text = mScoreTop + " : " + mScoreBottom;
        mScoreView.setText(text);
    }

    private void initGameView() {
        mGameViewLayout.removeAllViews();
        LevelData data = LevelData.getRandomData(0);
        GameViewWith2Player view = new GameViewWith2Player(this, data);
        view.setOnTwoPlayerGameListener(this);
        mGameViewLayout.addView(view);
    }


    public static void comeToMe(Context context) {
        Intent intent = new Intent(context, TwoPlayerGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onBottomPlayerWin() {
        mScoreBottom++;
        checkGameOver();
    }

    @Override
    public void onTopPlayerWin() {
        mScoreTop++;
        checkGameOver();
    }

    private void checkGameOver() {
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setScore();
                if (mScoreTop >= SCORE_OF_END) {
                    mTopPlayerMsgView.setText(R.string.you_win);
                    mBottomPlayerMsgView.setText(R.string.you_lose);
                    return;
                }
                if (mScoreBottom >= SCORE_OF_END) {
                    mTopPlayerMsgView.setText(R.string.you_lose);
                    mBottomPlayerMsgView.setText(R.string.you_win);
                    return;
                }
                // 生成新game View
                initGameView();
            }
        });


    }


}
