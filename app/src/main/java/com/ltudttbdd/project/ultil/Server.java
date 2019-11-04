package com.ltudttbdd.project.ultil;

public class Server {

    public static String localhost = "172.30.234.76:80";
//    public static String localhost = "192.168.1.15:800";


    public static String urlProductCategory = "http://" + localhost + "/ctxhstore/public/api/data/getProductCategory";
    public static String urlNewProduct = "http://" + localhost + "/ctxhstore/public/api/data/getNewProduct";
    public static String urlAds = "http://" + localhost + "/ctxhstore/public/api/data/getSlideShow";
    public static String urlProduct = "http://" + localhost + "/ctxhstore/public/api/data/getProductByCategoryId";
    public static String urlOrder = "http://" + localhost + "/ctxhstore/public/api/data/postInforUser";
    public static String urlOrderDetail = "http://" + localhost + "/ctxhstore/public/api/data/postOrderDetail";
    public static String urlLogin = "http://" + localhost + "/ctxhstore/public/api/auth/login";
    public static String urlRegister = "http://" + localhost + "/ctxhstore/public/api/auth/register";
    public static String urlLogout = "http://" + localhost + "/ctxhstore/public/api/auth/logout";

}
