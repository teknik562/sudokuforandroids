package de.fhd.medien.mait.sfa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText; 

public class UserForm extends Activity
  {
    protected final int SUCCESS_RETURN_CODE = 1;

    public void onCreate(Bundle icicle) 
      {
        super.onCreate(icicle);
        // take userform.xml as current view
        setContentView(R.layout.userform);
       
        // Create a button that points to button on the XML-View
        Button userform_btn = (Button)findViewById(R.id.userform_btn);
        
        // Add a onClick-Listener to the button
        userform_btn.setOnClickListener(new OnClickListener()
          {
          public void onClick(View arg0) 
            {
              // Create an editfield that points to the editfield on the XML-View
              EditText userform_edit = (EditText)findViewById(R.id.userform_edit);
              
              // Set text of editfield as result-value
              UserForm.this.setResult(SUCCESS_RETURN_CODE, userform_edit.getText().toString());

              // Close this Activity
              UserForm.this.finish();
            }
          });
      } 
  }
