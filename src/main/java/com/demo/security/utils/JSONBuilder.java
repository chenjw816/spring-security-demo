package com.demo.security.utils;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by louis on 2014/9/22.
 */
public class JSONBuilder {
    private final JSONObject jsonObject;
    public static Builder builder(){
        return new Builder();
    }

    private JSONBuilder(final Builder builder){
        this.jsonObject=builder.jsonObject;
    }


    public static class Builder{
        private JSONObject jsonObject=new JSONObject();

        public JSONBuilder build(){
            return new JSONBuilder(this);
        }
        public Builder inflate(String key,Object value){
            jsonObject.put(key,value);
            return this;
        }

        public Builder inflateALL(Map map){
            jsonObject.putAll(map);
            return this;
        }
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
