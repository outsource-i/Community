package com.onion.community.engine.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.onion.community.api.RetrofitUtils;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.util.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractFragment extends EaseContactListFragment {

    private List<String> usernames;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                RetrofitUtils
                        .getInstance()
                        .build()
                        .getFriend(user.getUsername())
                        .compose(T.D())
                        .subscribe(new Result<HttpWrapper<User>>() {
                            @Override
                            protected void onSuccess(HttpWrapper<User> userHttpWrapper) {

                                User data = userHttpWrapper.getData();
                                //需要头像更新 ... TODO
                                Intent intent = new Intent(getActivity(), ChatActivity.class).putExtra(ChatActivity.USERID, data.getId())
                                        .putExtra(ChatActivity.COMPANYNAME, data.getNickName()).putExtra(ChatActivity.HEADER_URL, data.getHeadImg())
                                        .putExtra(ChatActivity.NICKNAME, data.getNickName());
                                startActivity(intent);
                            }
                        });
            }
        });

        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    Map<String, EaseUser> map = new HashMap<>();


                    for (int i = 0; i < usernames.size(); i++) {
                        map.put(usernames.get(i), new EaseUser(usernames.get(i)));
                    }
                    setContactsMap(map);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpView();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



}
