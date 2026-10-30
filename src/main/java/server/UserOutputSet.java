package server;

import java.io.DataOutputStream;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserOutputSet {
    public static CopyOnWriteArraySet<DataOutputStream> userSet = new CopyOnWriteArraySet<>();
}
