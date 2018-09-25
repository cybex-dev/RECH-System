package helpers;

import controllers.UserSystem.routes;
import dao.UserSystem.EntityPerson;
import models.App;
import play.api.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class Mailer {

    @Inject
    NotifierExecutor notifierExecutor;

    @Inject
    MailerClient mailerClient;

    public Mailer() {
        mailerClient = Play.current().injector().instanceOf(MailerClient.class);
    }

    /**
     * Call with CompletableFuture.supplyAsync(() -> Mailer.SendWelcome("New User", c.getEmail()), httpExecutionContext.current());
     * @param name
     * @param email_address
     * @return
     */
    public static CompletableFuture<Boolean> SendWelcome(String name, String email_address) {
        Email email = Mailer.StandardEmail()
                .setTo(Collections.singletonList(email_address))
                .setSubject("Research and Ethics Committee Registration")
                .setBodyHtml("Welcome, " + name);
        return new Mailer().sendEmail(email);
    }

//    /**
//     * Notifies a user of an application being submitted
//     * Call with CompletableFuture.supplyAsync(() -> Mailer.SendWelcome("New User", c.getEmail()), httpExecutionContext.current());
//     * @param name
//     * @param email_address
//     * @return
//     */
//    public static CompletableFuture<Boolean> NotifyApplicationSubmitted(String name, String email_address, String applicationTitle) {
//        Email email = Mailer.StandardEmail()
//                .setTo(Collections.singletonList(email_address))
//                .setSubject("Ethics Application \'" + applicationTitle + "\' submitted for review")
//                .setBodyHtml("Hi, " + name + "<br/><br/>"
//                        + "Your ethics application titled: <br/><br/>"
//                        + "<h2>" + applicationTitle + "</h2><br/><br/>"
//                        + "has been submitted for approval<br/><br/><br/>"
//                        + "You will be notified of any changes<br/><br/>"
//                        + "RCD @ RECH Committee");
//        return new Mailer().sendEmail(email);
//    }

//    /**
//     * Notify a user of an application status change
//     * Call with CompletableFuture.supplyAsync(() -> Mailer.SendWelcome("New User", c.getEmail()), httpExecutionContext.current());
//     * @param name
//     * @param email_address
//     * @return
//     */
//    public static CompletableFuture<Boolean> NotifyApplicationStatusChange(String name, String email_address, String applicationTitle, String newStatus, String oldStatus) {
//
//
//        Email email = Mailer.StandardEmail()
//                .setTo(Collections.singletonList(email_address))
//                .setSubject("Ethics Application \'" + applicationTitle + "\' status change from " + oldStatus + " to " + newStatus)
//                .setBodyHtml("Hi, " + name + "<br/><br/>"
//                        + "Your ethics application titled: <br/><br/>"
//                        + "<h2>" + applicationTitle + "</h2><br/><br/>"
//                        + "has been submitted for approval<br/><br/><br/>"
//                        + "You will be notified of any changes<br/><br/>"
//                        + "RCD @ RECH Committee");
//        return new Mailer().sendEmail(email);
//    }

    public static CompletableFuture<Boolean> SendVerificationEmail(EntityPerson person, String token) {
        String verificationUrl = App.getInstance().getBaseUrl() + routes.RegistrationController.verify(token).url();
        System.out.println(verificationUrl);
        Email verificationEmail = Mailer.StandardEmail()
                .setTo(Collections.singletonList(person.getUserEmail()))
                .setSubject("NMU Research & Ethics Committee: Account Verification")
                .setBodyHtml("Welcome" +
                        "<br/><br/>Please verify your account using hyperlink below:" +
                        "<br/><br/><i><a style=\"color:blue;font-size:2em;\" href=\"" + verificationUrl + "\">Verify Account</i></a>" +
                        "<br/><br/>or visiting the URL below:" +
                        "<br/><br/><a href=\"" + verificationUrl + "\">" + verificationUrl + "</a>" +
                        "<br/><br/>If you have not created this account then ignore this email" +
                        "<br/><br/>Regards," +
                        "<br/>" + App.getInstance().getApplicationShortName());
        return new Mailer().sendEmail(verificationEmail);
    }

    public static CompletableFuture<Boolean> SendPasswordChange(String fullName, String email_address, String date) {
        Email email = Mailer.StandardEmail()
                .setTo(Collections.singletonList(email_address))
                .setSubject(App.getInstance().getApplicationFullName() + ": Password Change")
                .setBodyHtml("Hi, " + fullName +
                        "<br/><br/>Notifying you of a password change at " + date +
                        "<br/><br/>Regards," +
                        "<br/>" + App.getInstance().getApplicationShortName());
        return new Mailer().sendEmail(email);
    }

    public CompletableFuture<Boolean> sendEmail(Email email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MailerClient mailer = mailerClient;
                mailer.send(email);
                return true;
            } catch (Exception x) {
                x.printStackTrace();
                return false;
            }
        }/*, notifierExecutor*/);
    }

    private static Email StandardEmail() {
        return new Email()
                .setFrom("admin@rcd.mandela.ac.za")
                .setBodyText("Cannot display HTML message in your client!");
    }

//    private File getFile(String s) {
//        return environment.getFile(s);
//    }

//    private File getLogo() {
//        return environment.getFile("public/images/logo.png");
//    }
}