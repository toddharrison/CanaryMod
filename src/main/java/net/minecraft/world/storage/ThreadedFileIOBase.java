package net.minecraft.world.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable {

    public static final ThreadedFileIOBase a = new ThreadedFileIOBase();
    private List b = Collections.synchronizedList(new ArrayList());
    private volatile long c;
    private volatile long d;
    private volatile boolean e;
    public volatile boolean keepRunning = true; // CanaryMod: allow for stopping the IO

    private ThreadedFileIOBase() {
        Thread thread = new Thread(this, "File IO Thread");

        thread.setPriority(1);
        thread.start();
    }

    public void run() {
        while (keepRunning || this.c != this.d) {
            this.b();
        }
    }

    private void b() {
        for (int i = 0; i < this.b.size(); ++i) {
            IThreadedFileIO oithreadedfileio = (IThreadedFileIO) this.b.get(i);
            boolean flag = oithreadedfileio.c();

            if (!flag) {
                this.b.remove(i--);
                ++this.d;
            }

            try {
                Thread.sleep(this.e ? 0L : 10L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }

        if (this.b.isEmpty()) {
            try {
                Thread.sleep(25L);
            } catch (InterruptedException interruptedexception1) {
                interruptedexception1.printStackTrace();
            }
        }
    }

    public void a(IThreadedFileIO oithreadedfileio) {
        if (!this.b.contains(oithreadedfileio)) {
            ++this.c;
            this.b.add(oithreadedfileio);
        }
    }

    public void a() throws InterruptedException {
        this.e = true;

        while (this.c != this.d) {
            Thread.sleep(10L);
        }

        this.e = false;
    }
}
