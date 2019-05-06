package ficus.suitcase.jettynotifylistner;

import org.eclipse.jetty.server.Server;

/**
 * JettyServer监听回调服务
 * 将监听到的后续处理动作写在一个servlet的service方法中;
 * 然后将这个servlet放入Jetty的context中进行监听；
 *
 * 使用场景：对于服务只通知到某个端口，没有具体回调地址的场景，或者多个服务共用一个端口时(可根据内容区分，发往不同的目的服务器)
 * 用这种监听的方式处理更轻量和灵活；
 * 这种处理回调监听的方式比mina监听端口更合理和灵活；
 * Created by DamonFicus on 20181009
 * @author DamonFicus
 */
public class JettyListenerServer {
//    private static final Logger logger = LoggerFactory.getLogger(JettyListenerServer.class);

    private Server server;
    /**
     * 实际开发中，诸如端口参数，可灵活从配置文件或数据库中亦或是配置中心获取；
     */
    private static final String ASYN_NOTIFY_PORT="8890";

    /**
     * 启动监听服务
     * @throws Exception
     */
    public void startServer() throws Exception {

//        logger.info("JettyListenerServer 服务启动中,监听服务端口{}",ASYN_NOTIFY_PORT);
        server = new Server();
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(Integer.valueOf(ASYN_NOTIFY_PORT));
//        server.setConnectors(new Connector[] {connector});
//        ServletContextHandler context = new ServletContextHandler();
//        context.setContextPath("/");
//        context.addServlet(AsynNotifyServlet.class, "/");
//        HandlerCollection handlers = new HandlerCollection();
//        handlers.setHandlers(new Handler[] { context, new DefaultHandler()});
//        server.setHandler(handlers);
//        server.start();
//        logger.info("JettyListenerServer 监听服务通知服务启动成功");
    }

    /**
     * 关闭监听服务
     * @throws Exception
     */
    public void stopServer() throws Exception {
//        if(server!=null){
//            logger.info("JettyListenerServer监听通知服务关闭");
//            server.stop();
//        }
    }

}
