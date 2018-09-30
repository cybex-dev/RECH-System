package models;

import com.typesafe.config.Config;
import play.api.Play;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class App extends Controller{

    private Config config = null;
    private static App app = null;

    public static App getInstance(){
        if (app == null){
            app = new App();
        }
        return app;
    }

    private App() {
        config = Play.current().injector().instanceOf(Config.class);
    }

    public String getApplicationShortName(){
        return config.getString("shortname");
    }

    public String getApplicationFullName(){
        return config.getString("fullname");
    }

    public String getDocumentDirectory() {
        return config.getString("documentLocation");
    }

    public String getBaseUrl() {
        return config.getString("baseUrl");
    }
}
