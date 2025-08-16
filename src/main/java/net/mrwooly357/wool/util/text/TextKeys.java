package net.mrwooly357.wool.util.text;

public enum TextKeys implements TextKey {

    CHAT("chat"),
    COMMAND("command");

    private final String suffix;

    TextKeys(String suffix) {
        this.suffix = suffix;
    }


    @Override
    public String getSuffix() {
        return suffix;
    }
}
