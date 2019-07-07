package com.example.demo1.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo1.dto.AccessTokenDto;
import com.example.demo1.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token=string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getUser(String accesssToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.github.com/user?access_token"+accesssToken)
                .build();
        try{
        Response response = client.newCall(request).execute();
        String string=response.body().string();
        final GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
        return githubUser;
    }catch(IOException e){
            return null;
        }

        }




}
