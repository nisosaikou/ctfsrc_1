package org.nisosaikou.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.nisosaikou.ctfsrc_1.R;

import java.security.MessageDigest;

import static android.content.pm.PackageManager.GET_PERMISSIONS;

public class MainActivity extends AppCompatActivity {

    EditText _edit_flag;
    Button _btn_check;
    PackageInfo _packageInfo;
    public static String _result = "YmpOa1oyazFNMkZ6WVRZMmJ6STBNM05oTmpaaE1qUXphUUFBQUdzQUFBQnYK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("NSSK_LOG", "MainActivity extends AppCompatActivity");
        try {
            _packageInfo = MainActivity.this.getPackageManager().getPackageInfo("org.nisosaikou.ctfsrc_1", GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        _edit_flag = this.findViewById(R.id.edtv_flag);
        _btn_check = this.findViewById(R.id.btn_check);
        _btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_flag = _edit_flag.getText().toString();
                if(check_flag(input_flag))
                {
                    Toast.makeText(view.getContext(), "You are right.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(view.getContext(), "You are wrong.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean check_flag(String input_flag)
    {
        //input_flag = "flag{464486c54e55a2d6c44706171fe271c3}";
        if (input_flag.length() != 32+6)
            return false;
        if (!(input_flag.startsWith("flag{") && input_flag.endsWith("}")))
            return false;
        byte[] flags = input_flag.substring("flag{".length()-1, input_flag.length()-1).getBytes();

        byte[] newFlags = new byte[flags.length];
        // 先简单异或
        for (int i = 0; i < flags.length; i++)
            flags[i] ^= 2;

        // 调换顺序
        for (int i = 0; i < flags.length; i++)
        {
            if (i < flags.length / 4)
                newFlags[i] = flags[flags.length / 4 * 3 + i];
            else if (i < flags.length / 4 && i < flags.length / 4 * 2)
                newFlags[i] = flags[flags.length / 4 * 2 + i];
            else if (i < flags.length / 4 * 2 && i < flags.length / 4 * 3)
                newFlags[i] = flags[flags.length / 4  + i];
            else if (i < flags.length / 4 * 3 && i < flags.length)
                newFlags[i] = flags[i];
        }

        int versionNameIndex = 0;
        for (int i = 0; i < flags.length; i++)
        {
            if (i%4 == 0)
                flags[i] = _packageInfo.versionName.getBytes()[versionNameIndex++];
            else
                flags[i] = newFlags[i];
        }

        String result = sEncodeBase64(sEncodeBase64_(sEncodeBase64(flags).getBytes()).getBytes());
        result = result.replaceAll("\n", "");
        result = result.substring(0, _result.length());
        if (result.equals(_result))
            return true;
        return false;
    }

    public static String sEncodeBase64(byte[] bytes){
        try {
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return "";
    }

    private static String getMD5(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static String sEncodeBase64_(byte[] bytes){
        try {
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return "";
    }

}