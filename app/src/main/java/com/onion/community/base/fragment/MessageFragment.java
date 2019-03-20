package com.onion.community.base.fragment;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.onion.community.base.ConfigActivity;
import com.onion.community.base.MessageActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhangqi on 2016/10/13.
 */

public class MessageFragment extends SupportFragment {

    public void showMessage(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }


    public String getText(TextView tv){
        if(tv == null){
            return "";
        }
        CharSequence text = tv.getText();
        if(text == null){
            return "";
        }
        return text.toString();
    }

    public void showDialog(String tips) {
        FragmentActivity activity = getActivity();
        if(activity instanceof MessageActivity){
            MessageActivity message = (MessageActivity) activity;
            message.showDialog(tips);
        }else{
            throw new RuntimeException("您的activity没有继承MessageActivity");
        }
    }

    public void dissDialog() {
        FragmentActivity activity = getActivity();
        if(activity instanceof MessageActivity){
            MessageActivity message = (MessageActivity) activity;
            message.dissDialog();
        }else{
            throw new RuntimeException("您的activity没有继承MessageActivity");
        }
    }
}
