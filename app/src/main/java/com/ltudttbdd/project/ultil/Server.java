package com.ltudttbdd.project.ultil;

public class Server {
//    public static String host = "34.87.53.42";
    public static String host = "192.168.178.1:800";

    public static String urlProductCategory = "http://" + host + "/ctxhstore/public/api/data/getProductCategory";
    public static String urlNewProduct = "http://" + host + "/ctxhstore/public/api/data/getNewProduct";
    public static String urlAds = "http://" + host + "/ctxhstore/public/api/data/getSlideShow";
    public static String urlProduct = "http://" + host + "/ctxhstore/public/api/data/getProductByCategoryId";
    public static String urlOrder = "http://" + host + "/ctxhstore/public/api/data/postInforUser";
    public static String urlOrderDetail = "http://" + host + "/ctxhstore/public/api/data/postOrderDetail";
    public static String urlLogin = "http://" + host + "/ctxhstore/public/api/auth/login";
    public static String urlRegister = "http://" + host + "/ctxhstore/public/api/auth/register";
    public static String urlLogout = "http://" + host + "/ctxhstore/public/api/auth/logout";
}
