package com.github.signed.swagger.trim;

public class ToolboxPath {

    private final String path;

    public ToolboxPath(String path) {
        this.path = path;
    }

    public String asString() {
        return path;
    }

    public boolean referenceSameResource(ToolboxPath toolboxPath) {
        return this.path.equals(toolboxPath.path);
    }
}
