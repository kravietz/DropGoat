package net.krvtz.views;

import io.dropwizard.views.View;

public class HtmlView extends View {
    private final String content;

    public HtmlView(String template, String content) {
        super(template);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
