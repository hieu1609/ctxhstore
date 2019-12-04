package com.ltudttbdd.project.ultil;

public class Server {

    public static String host = "172.20.20.23:800";

    public static String urlProductCategory = "http://" + host + "/ctxhstore/public/api/data/getProductCategory";
    public static String urlNewProduct = "http://" + host + "/ctxhstore/public/api/data/getNewProduct";
    public static String urlAds = "http://" + host + "/ctxhstore/public/api/data/getSlideShow";
    public static String urlProduct = "http://" + host + "/ctxhstore/public/api/data/getProductByCategoryId";
    public static String urlOrder = "http://" + host + "/ctxhstore/public/api/data/postInforUser";
    public static String urlOrderDetail = "http://" + host + "/ctxhstore/public/api/data/postOrderDetail";
    public static String urlLogin = "http://" + host + "/ctxhstore/public/api/auth/login";
    public static String urlRegister = "http://" + host + "/ctxhstore/public/api/auth/register";
    public static String urlLogout = "http://" + host + "/ctxhstore/public/api/auth/logout";
    public static String urlChange = "http://" + host + "/ctxhstore/public/api/auth/change-password";
    public static String urleditUserProfile = "http://" + host + "/ctxhstore/public/api/user/editUserProfile";
    public static String urlrating = "http://" + host + "/ctxhstore/public/api/user/postReview";

}
