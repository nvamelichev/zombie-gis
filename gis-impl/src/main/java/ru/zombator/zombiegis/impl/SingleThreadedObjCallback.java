package ru.zombator.zombiegis.impl;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.netbeans.api.annotations.common.NonNull;

import ru.zombator.zombiegis.interfaces.ObjCallback;
import ru.zombator.zombiegis.transfers.ObjDTO;

/**
 * {@link ObjCallback}-декоратор, вызывающий методы указанного обработчика серверных событий в одном и том же потоке
 * с названием <code>"ObjCallback Thread"</code>.
 *
 * @author nvamelichev
 */
public final class SingleThreadedObjCallback implements ObjCallback {
    private static final Logger LOG = Logger.getLogger(SingleThreadedObjCallback.class.getName());
    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ObjCallback Thread");
        }
    });

    /**
     * ObjCallback, методы которого вызываются в потоке обработки серверных событий.
     */
    private final ObjCallback delegate;

    public SingleThreadedObjCallback(@NonNull ObjCallback delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public void onCreate(final ObjDTO[] created) {
        submit("onCreate", new Runnable() {
            @Override
            public void run() {
                delegate.onCreate(created);
            }
        });
    }

    @Override
    public void onModify(final ObjDTO[] modified) {
        submit("onModify", new Runnable() {
            @Override
            public void run() {
                delegate.onModify(modified);
            }
        });
    }

    @Override
    public void onDelete(final Long[] deleted) {
        submit("onDelete", new Runnable() {
            @Override
            public void run() {
                delegate.onDelete(deleted);
            }
        });
    }

    private void submit(final String methodName, final Runnable runnable) {
        EXEC.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    LogRecord rec = new LogRecord(Level.INFO,
                        "exception caught while calling {0}.{1}");
                    rec.setParameters(new Object[]{delegate, methodName});
                    rec.setThrown(e);

                    LOG.log(rec);
                }
            }
        });
    }
}
