package org.pepaproch.jproperties.parser.slang.visitor;

import org.sonarsource.slang.api.Comment;
import org.sonarsource.slang.api.Token;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.visitors.TreeContext;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.ArrayList;

public class TokensAndComentsVisitor extends TreeVisitor<TreeContext> {

    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();

    public TokensAndComentsVisitor() {
        register(Tree.class, (ctx, tree) -> tree.descendants().forEach(

                d -> {
                    getComments().addAll(d.metaData().commentsInside());
                    getTokens().addAll(d.metaData().tokens());
                }));
    }


    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
