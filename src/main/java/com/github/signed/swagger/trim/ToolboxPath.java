package com.github.signed.swagger.trim;

public class ToolboxPath {

    public static String unifyUrlTemplateVariableNames(String url) {
        return url.replaceAll("\\{[^\\}]+\\}", "{variable}");
    }

    private final String path;

    public ToolboxPath(String path) {
        this.path = path;
    }

    public String asString() {
        return path;
    }

    public boolean referenceSameResource(ToolboxPath toolboxPath) {
        return unifyUrlTemplateVariableNames(this.path).equals(unifyUrlTemplateVariableNames(toolboxPath.path));
    }
}
