package net.krvtz.views;

import io.dropwizard.views.View;

public class EscapeView extends View {
    private final String content;

    public EscapeView(String content) {
        super("/escape.ftl");
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}