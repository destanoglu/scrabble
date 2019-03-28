package service;

import controller.BoardController;
import exception.BoardNotFoundException;
import exception.RestException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.HibernateUtil;

import javax.ws.rs.core.Configurable;
import java.sql.SQLException;

public class Application
{
    @Autowired private HibernateUtil hibernateUtil;
    @Autowired private BoardController boardController;

    private Server server = null;
    private static final int PORT = 8080;
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public void start() throws Exception {
        if (server == null) {
            server = createServer();
            log.info("jetty server created");
        }
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    log.info("shutdown hook invoked");
                    server.stop();
                    log.info("jetty server stopped");
                    hibernateUtil.shutdown();
                    log.info("hibernate session closed");
                } catch (Exception e) {
                    log.info("error stopping jetty server", e);
                }
            }
        });
        server.join();
    }

    private Server createServer() throws SQLException, ClassNotFoundException {
        // Create resource config
        ResourceConfig resourceConfig = new ResourceConfig();
        // Configure all the external config
        setUpResources(resourceConfig);

        // Create ServletContainer and ServletHolder
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);

        // specify the servlet which classes to load
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "sahibinden.scrabble");

        // Create ServletContextHandler
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");

        // Add all servlets to the servlet context
        servletContextHandler.addServlet(servletHolder, "/*");

        // create embedded jetty server
        server = new Server(PORT);

        // Add the ServletContextHandler to the server
        server.setHandler(servletContextHandler);
        return server;
    }

    private void setUpResources(Configurable<?> configurable) {

        // Register rest Controllers
        configurable.register(boardController);
        // Register the customer exception mappers
        configurable.register(RestException.class);
        configurable.register(BoardNotFoundException.class);
    }
}
