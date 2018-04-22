package ru.atom.chat.message;

import java.util.List;
import java.util.Queue;

public class MessageCreator {
    /*
    message example
    <div class="chatMessage me">
        <div class="timeAndPhoto">
            <div class="userPhoto"><img src="img/user2.png"></div>
            <div class="time">
                20:22
            </div>
        </div>
        <div class="userLetter">
            <p class="userName">
                hell yeah:
            </p>
            <p class="messageBody">
                hello worldsadasda asfasd qdsadsd ad asf asd asdasd asd asd asd
            </p>
        </div>
    </div>


        public static String messageInHtml(Iterable<IMessage> messages, String processingUser) {
        StringBuilder str = new StringBuilder();
        for(IMessage message : messages) {
            str.append(
                    "<div class=\"chatMessage me\">" +
                    "   <div class=\"timeAndPhoto\">" +
                    "       <div class=\"userPhoto\"><img src=\"img/user2.png\"></div>" +
                    "           <div class=\"time\">" +
                    message.getTime() +
                    "           </div>" +
                    "       </div>" +
                    "   <div class=\"userLetter\">" +
                    "       <p class=\"userName\">" +
                    message.getUserName() +
                    "       </p>" +
                    "       <p class=\"messageBody\">" +
                    message.getMessageBody() +
                    "       </p>" +
                    "   </div>" +
                    "</div>"
            );
        }
        return str.toString();

    }
     */

    public static String messageInHtml(Iterable<Message> messages, String processingUser) {
        StringBuilder str = new StringBuilder();
        for (IMessage message : messages) {
            boolean isProcUser = processingUser.equals(message.getUser().getLogin());
            str.append(
                    "<div class=\"chatMessage " + (isProcUser ? "me" : "notMe") + "\">" +
                            "   <div class=\"timeAndPhoto\">" +
                            "       <div class=\"userPhoto\">" +
                            "           <img src=\"img/user" + (isProcUser ? "2" : "1") + ".png\">" +
                            "       </div>" +
                            "       <div class=\"time\">" +
                            message.getTime() +
                            "       </div>" +
                            "   </div>" +
                            "   <div class=\"userLetter\">" +
                            "       <p class=\"userName\">" +
                            (isProcUser ? ":You" : message.getUser().getLogin() + ":") +
                            "       </p>" +
                            "       <p class=\"messageBody\">" +
                            message.getValue() +
                            "       </p>" +
                            "   </div>" +
                            "</div>"
            );
        }
        return str.toString();
    }
}
