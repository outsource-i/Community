package com.onion.community.engine.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.Toast;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;
import com.onion.community.R;
import com.onion.community.api.RetrofitUtils;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import com.onion.community.constant.Constant;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.util.ToastUtil;
import com.onion.community.util.logger.Logger;

/**
 * Created by YF on 2019/3/5.
 */
public class NewsFragment extends EaseConversationListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EaseTitleBar easeTitleBar = getView().findViewById(R.id.title_bar);
        easeTitleBar.setVisibility(View.VISIBLE);
        easeTitleBar.setTitle("我的关注");
        getView().findViewById(R.id.search_content).setVisibility(View.VISIBLE);

        easeTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = getView().findViewById(R.id.query);
                Editable text = edt.getText();
                try {
                    EMClient.getInstance().contactManager().addContact(text.toString(), "理由");
                } catch (HyphenateException e) {
                    ToastUtil.makeText(getActivity(),"没有搜到该人", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {


            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                Logger.i(s);
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                Logger.i(s);
            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                Logger.i(username);
            }
        });

        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EMConversation emConversation = conversationList.get(i);
                String id = emConversation.conversationId();

                RetrofitUtils
                        .getInstance()
                        .build()
                        .getFriend(id)
                        .compose(T.D())
                        .subscribe(new Result<HttpWrapper<User>>() {
                            @Override
                            protected void onSuccess(HttpWrapper<User> userHttpWrapper) {
                                if(Constant.SUCCESS_CODE == userHttpWrapper.code){
                                    User data = userHttpWrapper.getData();
                                    //需要头像更新 ... TODO
                                    Intent intent = new Intent(getActivity(), ChatActivity.class).putExtra(ChatActivity.USERID, data.getId())
                                            .putExtra(ChatActivity.COMPANYNAME, data.getNickName()).putExtra(ChatActivity.HEADER_URL,data.getHeadImg())
                                            .putExtra(ChatActivity.NICKNAME,data.getNickName());
                                    startActivity(intent);
                                }else{
                                    ToastUtil.makeText(getActivity(),userHttpWrapper.getInfo(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });



            }
        });

    }



}
