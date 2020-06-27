package com.example.recyclerService;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuth implements Interceptor {

    private final String credentials;

    public BasicAuth(String user,String pass){
        this.credentials= Credentials.basic(user,pass);
    }
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request=chain.request();
        Request authRequest=request.newBuilder()
                .header("Authorization",credentials).build();
        return chain.proceed(authRequest);
    }
}
