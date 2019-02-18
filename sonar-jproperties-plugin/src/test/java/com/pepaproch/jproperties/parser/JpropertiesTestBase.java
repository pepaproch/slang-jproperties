package com.pepaproch.jproperties.parser;

public class JpropertiesTestBase {

    public static String WIKY_PROP_EXAMPLE =
            "# You are reading the \".properties\" entry.\n" +
                    "! The exclamation mark can also mark text as comments.\n" +
                    "# The key characters =, and : should be written with\n" +
                    "# a preceding backslash to ensure that they are properly loaded.\n" +
                    "# However, there is no need to precede the value characters =, and : by a backslash.\n" +
                    "website = https://en.wikipedia.org/\n" +
                    "language = English\n" +
                    "# The backslash below tells the application to continue reading\n" +
                    "# the value onto the next line.\n" +
                    "message = Welcome to \\\n" +
                    "          Wikipedia!\n" +
                    "# Add spaces to the key\n" +
                    "key\\ with\\ spaces = This is the value that could be looked up with the key \"key with spaces\".\n" +
                    "# Unicode\n" +
                    "tab : \\u0009\n" +
                    "# If you want your property to include a backslash, it should be escaped by another backslash\n" +
                    "path=c:\\wiki\\templates\n" +
                    "# However, some editors will handle this automatically";
}
