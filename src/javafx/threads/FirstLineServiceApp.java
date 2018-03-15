/*
* References:
* 
* Code copied from https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm 
*  The content of that page has been copied to my Jupyter Notebook as well.
 * http://localhost:8888/notebooks/MyJavaNotes/Java%20Tid-bits%201.ipynb#Threads-In-JavaFX
*  This is the Example 4 from that site/page and it shows an implementation of the Service 
*     class which reads the first line from any URL and returns it as a string.
 */
package javafx.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class FirstLineServiceApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FirstLineService service = new FirstLineService();
        //service.setUrl("http://google.com");
        service.setUrl("http://localhost:8888/notebooks/MyJavaNotes/Java%20Tid-bits%201.ipynb#Threads-In-JavaFX");
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                System.out.println("done:" + t.getSource().getValue());
            }
        });
        service.start();
    }

    public static void main(String[] args) {
        launch();
    }

    private static class FirstLineService extends Service<String> {
        private StringProperty url = new SimpleStringProperty();

        public final void setUrl(String value) {
            url.set(value);
        }

        public final String getUrl() {
            return url.get();
        }

        public final StringProperty urlProperty() {
           return url;
        }

        protected Task<String> createTask() {
            final String _url = getUrl();
            return new Task<String>() {
                protected String call() 
                    throws IOException, MalformedURLException {
                        String result = null;
                        BufferedReader in = null;
                        try {
                            URL u = new URL(_url);
                            in = new BufferedReader(
                                new InputStreamReader(u.openStream()));
                            result = in.readLine();
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                        return result;
                }
            };
        }
    }
}
