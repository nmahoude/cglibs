package org.nmx.codingame.analyser;

import com.google.gson.JsonObject;

public class Frame {

    public final String stdout;
    public final String stderr; // only for own player
    public final String summary;
    public final String view;
    public final int agentIndex;


    public Frame(JsonObject frame) {
        stdout = frame.get("stdout") != null ? frame.get("stdout").getAsString() : "";
        stderr = frame.get("stderr") != null ? frame.get("stderr").getAsString() : "";
        summary = frame.get("summary") != null ? frame.get("summary").getAsString() : "";
        view = frame.get("view") != null ? frame.get("view").getAsString() : "";
        agentIndex = frame.get("agentId") != null ? frame.get("agentId").getAsInt() : -1;
    }
}
