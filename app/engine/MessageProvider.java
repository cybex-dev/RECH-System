package engine;

import com.google.inject.Inject;
import play.i18n.Lang;
import play.i18n.Langs;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import java.lang.reflect.Array;
import java.util.*;

public class MessageProvider {

    private play.i18n.MessagesApi messagesApi;
    private Langs langs;

    public MessageProvider() {

    }

    @Inject
    public MessageProvider(Langs langs) {
        this.langs = langs;
    }

    @Inject
    public MessageProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    /**
     * Gets messages from conf/messages.xx using messageId as identifier and parameters as any message inputs
     * @param messageId message identifier
     * @param parameters messages parameters
     * @return  formatted message
     */
    public String getMessage(String messageId, Object...parameters) {
        Lang lang = langs.availables().stream().findFirst().orElse(new Lang(Locale.ENGLISH));
        Collection<Lang> candidates = Collections.singletonList(lang);
        Messages messages = messagesApi.preferred(candidates);
        String message = messages.at(messageId, parameters);
        return message;
    }

}
