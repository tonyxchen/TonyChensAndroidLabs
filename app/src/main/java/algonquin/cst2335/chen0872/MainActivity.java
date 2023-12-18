package algonquin.cst2335.chen0872;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**The main activity that starts up when the app is launched. This class loads a login page
 * where the user is prompted to enter a password and attempt to login. The class then checks
 * the password complexity and will show messages to the user making it known what is wrong.
 * @author Tony
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the centre of the screen*/
    private TextView tv = null;

    /** This holds the edittext below the text view*/
    private EditText et = null;

    /** This holds the button at the bottom of the screen*/
    private Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.pwMessage);
        et = findViewById(R.id.editTextPassword);
        btn = findViewById(R.id.loginButton);

        btn.setOnClickListener( clk ->{
            String password = et.getText().toString();
            if(checkPasswordComplexity(password)){
                tv.setText("Your password meets the requirements");
                Intent nextPage = new Intent(MainActivity.this, ChatRoom.class);
                startActivity(nextPage);

            } else {
                tv.setText("YOU SHALL NOT PASS!");
            }
        });

    }


    /** This function should check if this string has an Upper Case letter,
     *  a lower case letter, a number, and a special symbol (#$%^*!@?).
     *  If it is missing any of these 4 requirements,
     *  then show a Toast message saying which requirement is missing,
     *  like "Your password does not have an upper case letter", or
     *  "Your password does not have a special symbol".
     *
     * @param pw The String object that we are checking
     * @return  Returns true if input is complex enough
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i=0; i<pw.length(); i++){
            if (Character.isUpperCase(pw.charAt(i))){
                foundUpperCase = true;
            }if (Character.isLowerCase(pw.charAt(i))){
                foundLowerCase = true;
            }if (Character.isDigit(pw.charAt(i))){
                foundNumber = true;
            }if (isSpecialCharacter(pw.charAt(i))){
                foundSpecial = true;
            }
        }
        if(!foundUpperCase) {
            Toast.makeText(getApplicationContext(),"Missing an upper case letter",Toast.LENGTH_LONG).show(); // Say that they are missing an upper case letter;
            return false;

        }

        else if(!foundLowerCase) {
            Toast.makeText(getApplicationContext(),"Missing a lower case letter",Toast.LENGTH_LONG).show(); // Say that they are missing a lower case letter;
            return false;
        }

        else if(!foundNumber) {
            Toast.makeText(getApplicationContext(),"Missing a number",Toast.LENGTH_LONG).show(); // Say that they are missing a lower case letter;
            return false;
        }

        else if(!foundSpecial) {
            Toast.makeText(getApplicationContext(),"Missing a special character",Toast.LENGTH_LONG).show(); // Say that they are missing a lower case letter;
            return false;
        }

        else

            return true; //only get here if they're all true

    }

    /** Checks to see if the input character is one of the accepted special characters or not
     *
     * @param c input character
     * @return Return true if the input is a special character
     */
    boolean isSpecialCharacter(char c){
        switch (c){
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '?':
            case '*':
                return true;
            default:
                return false;
        }
    }
}