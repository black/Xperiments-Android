package com.example.writefile;


public class Signals {
    public enum Signal{BLINK, RAW, MEDITATION, ATTENTION, LOALPHA, HIALPHA, LOBETA, HIBETA, LOGAMMA, MIDGAMMA, DELTA, THETA}
    public long time;
    public Signal signal;
    public int val;
    public Signals(long time,Signal signal,int val){
        this.time = time;
        this.signal = signal;
        this.val = val;
    }

    public String toString() {
        return time+","+signal+","+val+"\n";
    }
}
