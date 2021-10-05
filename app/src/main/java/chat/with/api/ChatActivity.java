package chat.with.api;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
//import chat.with.api.interfaces.OnAskToLoadMoreCallback;
//import chat.with.api.views.MessagesWindow;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.samsao.messageui.interfaces.OnAskToLoadMoreCallback;
import com.samsao.messageui.views.MessagesWindow;
import com.samsao.messageui.models.Message;
import java.util.ArrayList;
import java.util.List;

import chat.with.api.connection.API;

import chat.with.api.model.req.ReqKirimChat;
import chat.with.api.model.res.ResChat;
import chat.with.api.model.res.ResDetailChat;
import chat.with.api.model.res.ResUtama;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private static final int MESSAGE_COUNT = 5;
    private MessagesWindow messagesWindow;
    //private MessagesWindow mMessagesWindow;
    //List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagesWindow = (MessagesWindow) findViewById(R.id.customized_messages_window);
        final EditText message = messagesWindow.getWritingMessageView().findViewById(R.id.message_box_text_field);

        onload();

//        List<Message> messages = new ArrayList<>();
//        Message messaged = new Message(":)", Message.THIS_SIDE);
//        messaged.setTime("7:20 pm");
//        messages.add(messaged);
//        messagesWindow.loadMessages(messages);
//        messagesWindow.setOnAskToLoadMoreCallback(new OnAskToLoadMoreCallback() {
//            @Override
//            public void onAskToLoadMore() {
//                List<Message> messages = getMessages("");
//                if (messages.isEmpty()) {
//                    messagesWindow.removeOnAskToLoadMoreCallback();
//                } else {
//                    messagesWindow.loadOldMessages(messages);
//                }
//            }
//        });

        message.setHint("Type Here.....");
        messagesWindow.setBackgroundResource(R.drawable.gray_button_background);
        messagesWindow.getWritingMessageView().findViewById(R.id.message_box_button).setPadding(5,5,5,5);
        messagesWindow.getWritingMessageView().findViewById(R.id.message_box_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //messagesWindow.sendMessage(message.getText().toString());
                kirimpesan(message.getText().toString(),"reza","15:27","agus");
//code python instant
                //messagesWindow.receiveMessage(message.getText().toString());
                //messagesWindow.setTimestampMode(MessagesWindow.ALWAYS_AFTER_BALLOON);
//                messagesWindow.setTimestampMode(MessagesWindow.INSIDE_BALLOON_TOP);
//                messagesWindow.setTimestampInsideBalloonGravity(MessagesWindow.TIMESTAMP_INSIDE_BALLOON_GRAVITY_CORNER);
                message.setText("");
            }
        });


    }

    private void kirimpesan(String chat,String pengirim,String waktu,String penerima) {
//        ReqKirimChat reqKirimChat = new ReqKirimChat();
//        reqKirimChat.setChat(chat);
//        reqKirimChat.setUsr_pengirim(pengirim);
//        reqKirimChat.setWaktu_chat(waktu);
//        reqKirimChat.setUsr_penerima(penerima);
        Call<ResUtama> callKirimChat = API.service().kirimPesanRequest(chat,pengirim,waktu,penerima);
        callKirimChat.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("Log Chat", response.code() + "");
                if (response.code() == 200) {

                    //ResUtama resChat = response.body();
                    messagesWindow.sendMessage(chat);
                }
                else{

                    new SweetAlertDialog(ChatActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(response.code()+ "")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                new SweetAlertDialog(ChatActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    private void onload() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ChatActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResChat> callLupaPassword = API.service().chatRequest();
        callLupaPassword.enqueue(new Callback<ResChat>() {
            @Override
            public void onResponse(Call<ResChat> call, Response<ResChat> response) {
                Log.d("Log Chat", response.code() + "");
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    ResChat resChat = response.body();
                    for(int i=0;i<resChat.getDataChat().size();i++){
                        ResDetailChat resDetailChat = resChat.getDataChat().get(i);
                        if(resDetailChat.getUsr_pengirim().equals("reza")||resDetailChat.getUsr_penerima().equals("agus")){
                            messagesWindow.sendMessage(resDetailChat.getChat());
                        }
                        else if (resDetailChat.getUsr_pengirim().equals("agus")||resDetailChat.getUsr_penerima().equals("reza")){
                            messagesWindow.receiveMessage(resDetailChat.getChat());
                        }
                    }
                }
                else{
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(ChatActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(response.code()+ "")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResChat> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(ChatActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    private List<Message> getMessages(String identification) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            Message message = new Message(identification + "message " + i, i % 3 == 0 ? Message.THIS_SIDE : Message.THAT_SIDE);
            message.setTime("7:20 pm");
            messages.add(message);
        }
        return messages;
    }
}