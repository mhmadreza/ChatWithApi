package chat.with.api.connection;



import chat.with.api.model.req.ReqKirimChat;
import chat.with.api.model.req.ReqRegister;
import chat.with.api.model.res.ResChat;
import chat.with.api.model.res.ResUtama;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Service {
    /*@Headers({
            "Content-Type:application/json"
    })*/

    @FormUrlEncoded
    //@POST("bikin_akun.php")
    @POST("post_regis.php")
    Call<ResUtama> registerRequest(@Field("username") String username,
                                   @Field("password") String password
    );

    @FormUrlEncoded
    @POST("post_chat.php")
    Call<ResUtama> kirimPesanRequest(@Field("chat") String chat,
                                     @Field("usr_pengirim") String usr_pengirim,
                                     @Field("waktu_chat") String waktu_chat,
                                     @Field("usr_penerima") String usr_penerima
    );

    @GET("get_chat.php")
    Call<ResChat> chatRequest();
}
