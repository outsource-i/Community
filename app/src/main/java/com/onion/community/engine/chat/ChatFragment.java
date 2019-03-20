package com.onion.community.engine.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.onion.community.R;
import com.onion.community.util.U;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper{

    private static final int REQUEST_CODE_SELECT_VIDEO = 1;
    private static final int REQUEST_CODE_SELECT_FILE = 2;
    private String mHeaderUrl;
    private String mNickName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EaseTitleBar easeTitleBar = getView().findViewById(R.id.title_bar);
        easeTitleBar.setBackgroundColor(Color.WHITE);
        easeTitleBar.setLeftImageResource(R.drawable.back_select);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            easeTitleBar.setElevation(6f);
        }

        EaseUser easeUiInfo = U.getEaseUiInfo(getArguments().getString(ChatActivity.USERID));
        if(easeUiInfo != null ){
            mNickName = easeUiInfo.getNickname();
        }else{
            mNickName = "未知";
        }
        easeTitleBar.setTitle(mNickName);

        inputMenu.registerExtendMenuItem("语音", R.drawable.ease_chat_voice_call_normal, 3, new EaseChatExtendMenu.EaseChatExtendMenuItemClickListener() {

            @Override
            public void onClick(int itemId, View view) {
                    if (!EMClient.getInstance().isConnected()) {
                        Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
                    } else {
//                        startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                                .putExtra("isComingCall", false));
//                        inputMenu.hideExtendMenuContainer();
                    }
            }
        });

        inputMenu.registerExtendMenuItem("视频", R.drawable.ease_chat_video_normal, 4, new EaseChatExtendMenu.EaseChatExtendMenuItemClickListener() {

            @Override
            public void onClick(int itemId, View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
            }
        });

        inputMenu.registerExtendMenuItem("文件", R.drawable.ease_chat_voice_call_normal, 4, new EaseChatExtendMenu.EaseChatExtendMenuItemClickListener() {

            @Override
            public void onClick(int itemId, View view) {
                selectFileFromLocal();
            }
        });
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    @Override
    protected boolean turnOnTyping() {
        return true;
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        super.setUpView();
    }

    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }


    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

//        Intent intent = new Intent(getActivity(), UserInfoDetailsActivity.class);
//        intent.putExtra("focusUserId", username);
//        startActivity(intent);

    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }
    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        for (final EMMessage msg : messages) {
            final EMCmdMessageBody body = (EMCmdMessageBody) msg.getBody();
            EMLog.i(TAG, "Receive cmd message: " + body.action() + " - " + body.isDeliverOnlineOnly());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ACTION_TYPING_BEGIN.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                        titleBar.setTitle(getString(com.hyphenate.easeui.R.string.alert_during_typing));
                    } else if (ACTION_TYPING_END.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                        titleBar.setTitle(mNickName);
                    }
                }
            });
        }
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
