package top.goodyp.springboot.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import top.goodyp.springboot.dto.AccessTokenDTO;
import top.goodyp.springboot.dto.GithubUser;

import java.io.IOException;

@Component  //spring的IOC特性：使用了这个注解的对象会自动实例化放到一个池子里面，用的时候可以轻松的通过名字直接使用，不需要使用new实例化
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String[] split = string.split("&");
                String tokenstr = split[0];
                String token = tokenstr.split("=")[1];
                return  token;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;


    }
}
