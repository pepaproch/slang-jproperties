package org.pepaproch.properties.plugin;

import org.sonarsource.slang.api.CodeVerifier;

import java.util.regex.Pattern;

public class PropertiesCodeVerifier implements CodeVerifier {

    private static final Pattern commentedOutCodePattern = Pattern
            .compile("^(#|!){1}[ \\t\\x0B\\f]*(?!(?i)todo)(?!(?i)fixme)([^=:\\s]|(?<=\\\\)\\ |(?<=\\\\)\\=|(?<=\\\\)\\:)+[ \\t\\x0B\\f]*(:|=){1}.*$");

    @Override
    public boolean containsCode(String content) {
        return commentedOutCodePattern.matcher(content).find();
    }
}
