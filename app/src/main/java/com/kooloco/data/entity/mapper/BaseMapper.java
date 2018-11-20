package com.kooloco.data.entity.mapper;


import com.kooloco.data.entity.ResponseEntity;
import com.kooloco.model.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by hlink21 on 4/3/17.
 */

public abstract class BaseMapper<FromT, ToT> {

    public Response<ToT> process(ResponseEntity<FromT> fromTResponse) {
        Response<ToT> response = createNewResponse(fromTResponse);
        response.setData(transform(fromTResponse.data));
        return response;
    }

    public Response<List<ToT>> processCollection(ResponseEntity<Collection<FromT>> fromTResponse) {
        Response<List<ToT>> response = createNewResponse(fromTResponse);
        response.setData(transformCollection(fromTResponse.data));
        return response;
    }

    public static <F, T> Response<T> createNewResponse(ResponseEntity<F> fromTResponse) {
        Response<T> response = new Response<>();
        response.setMessage(fromTResponse.message);
        response.setResponseCode(fromTResponse.responseCode);
        return response;
    }

    protected List<ToT> transformCollection(Collection<FromT> fromTs) {
        List<ToT> toTList = new ArrayList<>();
        if (fromTs != null) {
            for (FromT fromT : fromTs) {
                toTList.add(transform(fromT));
            }
        }
        return toTList;
    }

    public abstract ToT transform(FromT fromT);

    public Function<ResponseEntity<FromT>, Response<ToT>> mapFunction = new Function<ResponseEntity<FromT>, Response<ToT>>() {
        @Override
        public Response<ToT> apply(@NonNull ResponseEntity<FromT> fromTResponse) throws Exception {
            return process(fromTResponse);
        }
    };

    public static Function<ResponseEntity, Response> responseMapFunction = new Function<ResponseEntity, Response>() {
        @Override
        public Response apply(@NonNull ResponseEntity fromTResponse) throws Exception {
            return createNewResponse(fromTResponse);
        }
    };

    public Function<ResponseEntity<Collection<FromT>>, Response<List<ToT>>> collectionMapFunction = new Function<ResponseEntity<Collection<FromT>>, Response<List<ToT>>>() {
        @Override
        public Response<List<ToT>> apply(@NonNull ResponseEntity<Collection<FromT>> collectionResponse) throws Exception {
            return processCollection(collectionResponse);
        }
    };

}
