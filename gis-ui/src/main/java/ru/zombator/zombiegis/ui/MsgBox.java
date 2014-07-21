package ru.zombator.zombiegis.ui;

import java.text.MessageFormat;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Методы для отображения диалоговых окон. Все методы принимают на вход сообщение и набор параметров,
 * интерпретируемых аналогично {@link MessageFormat#format(java.lang.String, java.lang.Object...)}.
 *
 * @author nvamelichev
 */
public final class MsgBox {
    private MsgBox() {
    }

    public static void error(String message, Object... args) {
        msg(NotifyDescriptor.ERROR_MESSAGE, message, args);
    }

    public static void alert(String message, Object... args) {
        msg(NotifyDescriptor.WARNING_MESSAGE, message, args);
    }

    public static void info(String message, Object... args) {
        msg(NotifyDescriptor.INFORMATION_MESSAGE, message, args);
    }

    private static Object msg(int messageType, String message, Object... args) {
        NotifyDescriptor desc = new NotifyDescriptor.Message(MessageFormat.format(message, args),
            messageType);
        return DialogDisplayer.getDefault().notify(desc);
    }

    public static boolean confirm(String message, Object... args) {
        NotifyDescriptor desc = new NotifyDescriptor.Confirmation(MessageFormat.format(message, args),
            NotifyDescriptor.YES_NO_OPTION);
        return NotifyDescriptor.YES_OPTION.equals(DialogDisplayer.getDefault().notify(desc));
    }
}
