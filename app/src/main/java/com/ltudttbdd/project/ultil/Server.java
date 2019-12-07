package com.ltudttbdd.project.ultil;

public class Server {
    //public static String host = "35.247.143.211";
    public static String host = "172.20.11.157:800";

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
    public static String urlmail = "http://" + host + "/ctxhstore/public/api/auth/request/reset-password";
    public static String urlreset = "http://" + host + "/ctxhstore/public/api/auth/accept/reset-password";
    public static String urlGetNotification = "http://" + host + "/ctxhstore/public/api/user/getNotifications";
    public static String urlSeenNotification = "http://" + host + "/ctxhstore/public/api/user/seenNotifications";
    public static String urlcomment = "http://" + host + "/ctxhstore/public/api/data/getCommentByProductId";
    public static String urlfeedback = "http://" + host + "/ctxhstore/public/api/user/postFeedback";
    public static String urlreceived = "http://" + host + "/ctxhstore/public/api/user/getPurchasesReceived";
    public static String urlconfirm = "http://" + host + "/ctxhstore/public/api/user/getPurchasesConfirm";
    public static String urlshipping = "http://" + host + "/ctxhstore/public/api/user/getPurchasesShipping";
    public static String urlcompleted = "http://" + host + "/ctxhstore/public/api/user/getPurchasesCompleted";
    public static String urlcancel = "http://" + host + "/ctxhstore/public/api/user/cancelorder";

}
