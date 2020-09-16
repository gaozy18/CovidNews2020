package About;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.java.dac.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);


        ((TextView)view.findViewById(R.id.text_url)).setMovementMethod(LinkMovementMethod.getInstance());

        view.findViewById(R.id.button_version).setOnClickListener((View v) -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("版本信息提示")
                    .setMessage("当前版本：V1.0.0")
                    .setPositiveButton("确定", null).create();
            dialog.show();
        });
        return view;
    }
}
