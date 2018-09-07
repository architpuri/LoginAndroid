package com.aptechno.login.login.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import static com.aptechno.login.login.data.api.ApiConstants.SUCCESS;

public class CommonUtils {
    private static String TAG="Common Utils Activity";
    //To show Toast
    public static void showToast(Context c, String s){
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }
    //To Check Internet Connectivity
    public static boolean checkInternetConnectivity(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    //To Validate EmailId & Password
    private static String patternReqd;

    //Normal Check Constraints - ID
    public static boolean isValidEmail(String input){
        patternReqd="(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if(input.matches(patternReqd)/*||input.matches(patternReqd2))*/&& input.length()>0)
            return true;
        else return false;
    }
    //Normal Check Constraints - PASSWORD
    public static boolean isValidPassword(String input){
        if(input.length()>4)
            return true;
        else return false;
    }

    //To display error msgs
    /*private static void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }*/

    //To display input error msgs
    //noInputErrorMsg(layoutidObj type TextInputLayout,String to Display,Focus EditText);
    public static void wrongInputErrorMsg(final TextInputLayout textInputLayout, String display, TextInputEditText editText){
        textInputLayout.setError(display);
        Log.d("CommonUtils",display);
        //requestFocus(editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setError("");
            }
        });
    }

    public static boolean isSuccess(){
        return true;//responseCode == SUCCESS;
    }
}
