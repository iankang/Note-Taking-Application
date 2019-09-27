package com.example.ian.notetaking;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.notetaking.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener, GestureDetector.OnGestureListener , GestureDetector.OnDoubleTapListener {

    private static final String TAG = "NoteActivity";

    private LineEditText mLineEditText;
    private EditText mEditText;
    private TextView mTextView;

    private final int EDIT_MODE_ENABLED = 1;
    private final int  EDIT_MODE_DISABLED = 0;


    private boolean isFirstTime;
    private Note mNote;
    private GestureDetector gestureDetector;
    private RelativeLayout mCheckContainer, mbackArrowContainer;
    private ImageButton mCheckImageButton, mbackImageButton;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mLineEditText = findViewById(R.id.note_text);
        mEditText =  findViewById(R.id.note_edit_title);
        mTextView = findViewById(R.id.note_title_text);
        mCheckContainer = findViewById(R.id.check_container);
        mbackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheckImageButton = findViewById(R.id.toolbar_check_symbol);
        mbackImageButton = findViewById(R.id.toolbar_arrow_back);

        if(hasNotBeenOpened()){
            setNewNote();
            enableEditMode();
        }else{
            setNote();
            disableContentInteraction();
        }

        setListeners();

    }

    private void setListeners(){
        mLineEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        mCheckImageButton.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mbackImageButton.setOnClickListener(this);
    }

    private void enableEditMode(){
        mbackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mTextView.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        mbackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mTextView.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if(view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean hasNotBeenOpened(){
        if(getIntent().hasExtra("selected_note")){

           mNote = getIntent().getParcelableExtra("selected_note");
            mMode = EDIT_MODE_DISABLED;
            isFirstTime = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        isFirstTime = true;
        return true;
    }

    private void setNewNote(){
        mLineEditText.setText("Content goes here.");
        mEditText.setText("Title is set here");
        mTextView.setText("Title is here");
    }

    private void setNote(){
        mLineEditText.setText(mNote.getContent());
        mEditText.setText(mNote.getTitle());
        mTextView.setText(mNote.getTitle());
    }

    private void disableContentInteraction(){
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusable(false);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusable(true);
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: it has been double tapped");
        enableEditMode();

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.toolbar_check_symbol:{
                disableEditMode();
                hideSoftKeyboard();
                break;
            }

            case R.id.note_title_text:{
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;

            }
            case R.id.toolbar_arrow_back:{
                finish();
                break;

            }

        }
    }

    @Override
    public void onBackPressed() {

        if(mMode == EDIT_MODE_ENABLED){
            onClick(mCheckImageButton);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }
}
