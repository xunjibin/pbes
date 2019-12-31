package com.example.firstsbc.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;



//servlet2.4规范提供了8个listener接口，可以将其分为三类，分别如下；
//
//        　　第一类：与HttpContext有关的listener接口，包括：ServletContextListener、ServletContextAttributeListener
//
//        　　第二类：与HttpSession有关的listner接口。包括：HttpSessionListener、HttpSessionAttributeListener、
//
//        HttpSessionBindingListener、 HttpSessionActivationListener、
//
//        　　第三类：与ServletRequest有关的Listener接口，包括：ServletRequestListener、ServletRequestAttributeListener
public class MyHttpSessionListener implements HttpSessionListener {
    public  static  int online =0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        System.out.println("创建session");
        online++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("销毁session");
    }
}
