package com.kooloco.core;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by hlink21 on 29/11/17.
 */
@Singleton
public class AESCryptoInterceptor implements Interceptor {

    private final AES aes;

    @Inject
    public AESCryptoInterceptor(AES aes) {
        this.aes = aes;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();

        RequestBody requestBody = request.body();

        boolean isRequiredEncryption = !requestBody.contentType().type().equalsIgnoreCase("multipart");

        byte[] bodyPlainText = isRequiredEncryption ? transformInputStream(bodyToString(requestBody)) : bodyToString(requestBody);

        String apiKey = request.headers().get(Session.API_KEY);
        String token = request.headers().get(Session.USER_SESSION);

        Request build = null;
        if (bodyPlainText != null) {
            build = request.newBuilder()
                    .post(RequestBody.create(
                            requestBody.contentType(),
                            bodyPlainText)
                    )
                    .header(Session.API_KEY, aes.encrypt(apiKey))
                    .header(Session.USER_SESSION, aes.encrypt(token))
                    .build();
        }


        Response proceed = chain.proceed(build);

        String cipherBody = proceed.body().string();

        String plainBody = aes.decrypt(cipherBody);

        //Not decrypt

        //String plainBody = cipherBody;

        return proceed.newBuilder()
                .body(ResponseBody.create(MediaType.parse("text/json"), plainBody.trim()))
                .build();

    }

    private byte[] bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return null;
            return buffer.readByteArray();
        } catch (final IOException e) {
            return null;
        }
    }

    private byte[] transformInputStream(byte[] inputStream) {
        return aes.encrypt(inputStream);
    }
}
