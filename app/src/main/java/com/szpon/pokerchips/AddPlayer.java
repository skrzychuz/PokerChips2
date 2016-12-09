package com.szpon.pokerchips;

import android.app.Activity;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;


public class AddPlayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_players);
    }

    public void funkcjadodaj (View view) {
        Intent intent = getIntent();

        EditText edtTime = (EditText) findViewById(R.id.add_name_ID);
        intent.putExtra("name", edtTime.getText().toString());

        EditText edtNotes = (EditText) findViewById(R.id.add_stack_ID);
        intent.putExtra("stack", edtNotes.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }

    public void funkcjaanuluj(View view) {
        finish();
    }
}
