package com.official.read.base;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * com.official.read.base
 * Created by ZP on 2017/11/9.
 * description:　Model统一管理类，如果P层有多个M，GG
 * version: 1.0
 */

abstract public class BaseModel<T1, T2> {

   public abstract Observable<T1> execute(LinkedHashMap<String, String> map);

   public abstract List<T2> get(String... str);

   public final static class METHOD {

      static final String PACKAGE = "com.official.read.model.";

      public final static String DETAIL_MODEL = PACKAGE + "DetailModelImpl";
      public final static String RECOMMEND_MODEL = PACKAGE + "RecommendModelImpl";

      public static BaseModel method(String method) {
         BaseModel model = null;
         try {
            // 通过反射得到当前是需要那个Model
            model = (BaseModel) Class.forName(method).newInstance();
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (InstantiationException e) {
            e.printStackTrace();
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
         return model;
      }

   }
}
