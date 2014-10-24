package com.june.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private Button mTrueBotton;
	private Button mFalseBotton;
	private Button mCheatBotton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;
	
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(Boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		Log.d("TAG", String.valueOf(mIsCheater));
		if (mIsCheater) {
			messageResId = R.string.judgment_toast;
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}
		
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
			}
			mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		mTrueBotton = (Button)super.findViewById(R.id.true_button);
		mFalseBotton = (Button)super.findViewById(R.id.false_button);
		mCheatBotton  = (Button)super.findViewById(R.id.cheat_button);
		mNextButton = (ImageButton)super.findViewById(R.id.next_button);
		mPrevButton = (ImageButton)super.findViewById(R.id.prev_button);
		
		mQuestionTextView = (TextView)super.findViewById(R.id.question_text_view);
		updateQuestion();
		
		mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
				
			}
		});
		
		mPrevButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = ((mQuestionBank.length - 1) - mCurrentIndex) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
				
			}
		});
		
		mTrueBotton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
				
			}
		});
		
		mQuestionTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mFalseBotton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
				
			}
		});
		
		mCheatBotton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Start cheat activity
				Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivity(intent);
				
			}
		});
	}

}
