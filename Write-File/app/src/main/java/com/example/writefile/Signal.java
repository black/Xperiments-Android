package com.example.writefile;


public class Signal {
    public enum Type{BLINK, RAW, MEDITATION, ATTENTION, LOALPHA, HIALPHA, LOBETA, HIBETA, LOGAMMA, MIDGAMMA, DELTA, THETA}
    public long time;
    public Type type;
    public int val;
    public Signal(long time, Type type, int val){
        this.time = time;
        this.type = type;
        this.val = val;
    }

    public String toString() {
        return time+","+type+","+val+"\n";
    }
}
