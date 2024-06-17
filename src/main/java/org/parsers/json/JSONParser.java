/* Generated by: CongoCC Parser Generator. JSONParser.java  */
package org.parsers.json;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.concurrent.CancellationException;
import org.parsers.json.Token.TokenType;
import static org.parsers.json.Token.TokenType.*;
import org.parsers.json.ast.Root;
import org.parsers.json.ast.Array;
import org.parsers.json.ast.Value;
import org.parsers.json.ast.KeyValuePair;
import org.parsers.json.ast.JSONObject;


public class JSONParser {
    static final int UNLIMITED = Integer.MAX_VALUE;
    // The last token successfully "consumed"
    Token lastConsumedToken;
    private TokenType nextTokenType;
    // Normally null when parsing, populated when doing lookahead
    private Token currentLookaheadToken;
    private String currentlyParsedProduction;
    private String currentLookaheadProduction;
    private final boolean legacyGlitchyLookahead = false;
    private final Token DUMMY_START_TOKEN = new Token();
    private boolean cancelled;

    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    /** Generated Lexer. */
    private JSONLexer token_source;

    public void setInputSource(String inputSource) {
        token_source.setInputSource(inputSource);
    }

    String getInputSource() {
        return token_source.getInputSource();
    }

    //=================================
    // Generated constructors
    //=================================
    public JSONParser(String inputSource, CharSequence content) {
        this(new JSONLexer(inputSource, content));
    }

    public JSONParser(CharSequence content) {
        this("input", content);
    }

    /**
    * @param inputSource just the name of the input source (typically the filename) that
    * will be used in error messages and so on.
    * @param path The location (typically the filename) from which to get the input to parse
    */
    public JSONParser(String inputSource, Path path) throws IOException {
        this(inputSource, TokenSource.stringFromBytes(Files.readAllBytes(path)));
    }

    public JSONParser(String inputSource, Path path, Charset charset) throws IOException {
        this(inputSource, TokenSource.stringFromBytes(Files.readAllBytes(path), charset));
    }

    /**
    * @param path The location (typically the filename) from which to get the input to parse
    */
    public JSONParser(Path path) throws IOException {
        this(path.toString(), path);
    }

    /** Constructor with user supplied Lexer. */
    public JSONParser(JSONLexer lexer) {
        token_source = lexer;
        lastConsumedToken = DUMMY_START_TOKEN;
        lastConsumedToken.setTokenSource(lexer);
    }

    /**
    * Set the starting line/column for location reporting.
    * By default, this is 1,1.
    */
    public void setStartingPos(int startingLine, int startingColumn) {
        token_source.setStartingPos(startingLine, startingColumn);
    }

    // this method is for testing only.
    public boolean getLegacyGlitchyLookahead() {
        return legacyGlitchyLookahead;
    }

    // If the next token is cached, it returns that
    // Otherwise, it goes to the token_source, i.e. the Lexer.
    private Token nextToken(final Token tok) {
        Token result = token_source.getNextToken(tok);
        while (result.isUnparsed()) {
            result = token_source.getNextToken(result);
        }
        nextTokenType = null;
        return result;
    }

    /**
    * @return the next Token off the stream. This is the same as #getToken(1)
    */
    public final Token getNextToken() {
        return getToken(1);
    }

    /**
    * @param index how many tokens to look ahead
    * @return the specific regular (i.e. parsed) Token index ahead/behind in the stream.
    * If we are in a lookahead, it looks ahead from the currentLookaheadToken
    * Otherwise, it is the lastConsumedToken. If you pass in a negative
    * number it goes backward.
    */
    public final Token getToken(final int index) {
        Token t = currentLookaheadToken == null ? lastConsumedToken : currentLookaheadToken;
        for (int i = 0; i < index; i++) {
            t = nextToken(t);
        }
        for (int i = 0; i > index; i--) {
            t = t.getPrevious();
            if (t == null) break;
        }
        return t;
    }

    private TokenType nextTokenType() {
        if (nextTokenType == null) {
            nextTokenType = nextToken(lastConsumedToken).getType();
        }
        return nextTokenType;
    }

    boolean activateTokenTypes(TokenType...types) {
        if (token_source.activeTokenTypes == null) return false;
        boolean result = false;
        for (TokenType tt : types) {
            result |= token_source.activeTokenTypes.add(tt);
        }
        if (result) {
            token_source.reset(getToken(0));
            nextTokenType = null;
        }
        return result;
    }

    boolean deactivateTokenTypes(TokenType...types) {
        boolean result = false;
        if (token_source.activeTokenTypes == null) {
            token_source.activeTokenTypes = EnumSet.allOf(TokenType.class);
        }
        for (TokenType tt : types) {
            result |= token_source.activeTokenTypes.remove(tt);
        }
        if (result) {
            token_source.reset(getToken(0));
            nextTokenType = null;
        }
        return result;
    }

    private static final HashMap<TokenType[], EnumSet<TokenType>> enumSetCache = new HashMap<>();

    private static EnumSet<TokenType> tokenTypeSet(TokenType first, TokenType...rest) {
        TokenType[] key = new TokenType[1 + rest.length];
        key[0] = first;
        if (rest.length > 0) {
            System.arraycopy(rest, 0, key, 1, rest.length);
        }
        Arrays.sort(key);
        if (enumSetCache.containsKey(key)) {
            return enumSetCache.get(key);
        }
        EnumSet<TokenType> result = (rest.length == 0) ? EnumSet.of(first) : EnumSet.of(first, rest);
        enumSetCache.put(key, result);
        return result;
    }

    // /Users/bnaudts/git/congo/examples/json/JSON.ccc:69:1
    final public void Root() {
        if (cancelled) throw new CancellationException();
        this.currentlyParsedProduction = "Root";
        // Code for BNFProduction specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:69:1
        Root thisProduction = null;
        if (buildTree) {
            thisProduction = new Root();
            openNodeScope(thisProduction);
        }
        ParseException parseException3 = null;
        int callStackSize4 = parsingStack.size();
        try {
            // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:69:8
            pushOntoCallStack("Root", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 69, 8);
            try {
                Value();
            } finally {
                popCallStack();
            }
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:69:15
            consumeToken(EOF);
        } catch (ParseException e) {
            parseException3 = e;
            throw e;
        } finally {
            restoreCallStack(callStackSize4);
            if (thisProduction != null) {
                if (parseException3 == null) {
                    closeNodeScope(thisProduction, nodeArity() > 1);
                } else {
                    clearNodeScope();
                }
            }
        }
    }

    // /Users/bnaudts/git/congo/examples/json/JSON.ccc:71:1
    final public void Array() {
        if (cancelled) throw new CancellationException();
        this.currentlyParsedProduction = "Array";
        // Code for BNFProduction specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:71:1
        Array thisProduction = null;
        if (buildTree) {
            thisProduction = new Array();
            openNodeScope(thisProduction);
        }
        ParseException parseException3 = null;
        int callStackSize4 = parsingStack.size();
        try {
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:72:5
            consumeToken(OPEN_BRACKET);
            // Code for ZeroOrOne specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:73:5
            if (first_set$JSON_ccc$74$7.contains(nextTokenType())) {
                // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:74:7
                pushOntoCallStack("Array", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 74, 7);
                try {
                    Value();
                } finally {
                    popCallStack();
                }
                // Code for ZeroOrMore specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:74:13
                while (true) {
                    if (!(nextTokenType() == COMMA)) break;
                    // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:74:14
                    consumeToken(COMMA);
                    // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:74:22
                    pushOntoCallStack("Array", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 74, 22);
                    try {
                        Value();
                    } finally {
                        popCallStack();
                    }
                }
            }
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:76:5
            consumeToken(CLOSE_BRACKET);
        } catch (ParseException e) {
            parseException3 = e;
            throw e;
        } finally {
            restoreCallStack(callStackSize4);
            if (thisProduction != null) {
                if (parseException3 == null) {
                    closeNodeScope(thisProduction, nodeArity() > 1);
                } else {
                    clearNodeScope();
                }
            }
        }
    }

    private static final EnumSet<TokenType> Value_FIRST_SET = tokenTypeSet(OPEN_BRACKET, OPEN_BRACE, TRUE, FALSE, NULL, STRING_LITERAL, NUMBER);

    // /Users/bnaudts/git/congo/examples/json/JSON.ccc:79:1
    final public void Value() {
        if (cancelled) throw new CancellationException();
        this.currentlyParsedProduction = "Value";
        // Code for BNFProduction specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:79:1
        Value thisProduction = null;
        if (buildTree) {
            thisProduction = new Value();
            openNodeScope(thisProduction);
        }
        ParseException parseException3 = null;
        int callStackSize4 = parsingStack.size();
        try {
            // Code for ExpansionChoice specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:80:5
            if (nextTokenType() == TRUE) {
                // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:80:5
                consumeToken(TRUE);
            } else if (nextTokenType() == FALSE) {
                // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:82:5
                consumeToken(FALSE);
            } else if (nextTokenType() == NULL) {
                // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:84:5
                consumeToken(NULL);
            } else if (nextTokenType() == STRING_LITERAL) {
                // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:86:5
                consumeToken(STRING_LITERAL);
            } else if (nextTokenType() == NUMBER) {
                // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:88:5
                consumeToken(NUMBER);
            } else if (nextTokenType() == OPEN_BRACKET) {
                // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:90:5
                pushOntoCallStack("Value", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 90, 5);
                try {
                    Array();
                } finally {
                    popCallStack();
                }
            } else if (nextTokenType() == OPEN_BRACE) {
                // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:92:5
                pushOntoCallStack("Value", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 92, 5);
                try {
                    JSONObject();
                } finally {
                    popCallStack();
                }
            } else {
                pushOntoCallStack("Value", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 80, 5);
                throw new ParseException(lastConsumedToken, Value_FIRST_SET, parsingStack);
            }
        } catch (ParseException e) {
            parseException3 = e;
            throw e;
        } finally {
            restoreCallStack(callStackSize4);
            if (thisProduction != null) {
                if (parseException3 == null) {
                    closeNodeScope(thisProduction, nodeArity() > 1);
                } else {
                    clearNodeScope();
                }
            }
        }
    }

    // /Users/bnaudts/git/congo/examples/json/JSON.ccc:96:1
    final public void KeyValuePair() {
        if (cancelled) throw new CancellationException();
        this.currentlyParsedProduction = "KeyValuePair";
        // Code for BNFProduction specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:96:1
        KeyValuePair thisProduction = null;
        if (buildTree) {
            thisProduction = new KeyValuePair();
            openNodeScope(thisProduction);
        }
        ParseException parseException3 = null;
        int callStackSize4 = parsingStack.size();
        try {
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:96:16
            consumeToken(STRING_LITERAL);
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:96:33
            consumeToken(COLON);
            // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:96:41
            pushOntoCallStack("KeyValuePair", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 96, 41);
            try {
                Value();
            } finally {
                popCallStack();
            }
        } catch (ParseException e) {
            parseException3 = e;
            throw e;
        } finally {
            restoreCallStack(callStackSize4);
            if (thisProduction != null) {
                if (parseException3 == null) {
                    closeNodeScope(thisProduction, nodeArity() > 1);
                } else {
                    clearNodeScope();
                }
            }
        }
    }

    // /Users/bnaudts/git/congo/examples/json/JSON.ccc:98:1
    final public void JSONObject() {
        if (cancelled) throw new CancellationException();
        this.currentlyParsedProduction = "JSONObject";
        // Code for BNFProduction specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:98:1
        JSONObject thisProduction = null;
        if (buildTree) {
            thisProduction = new JSONObject();
            openNodeScope(thisProduction);
        }
        ParseException parseException3 = null;
        int callStackSize4 = parsingStack.size();
        try {
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:99:5
            consumeToken(OPEN_BRACE);
            // Code for ZeroOrOne specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:100:5
            if (nextTokenType() == STRING_LITERAL) {
                // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:101:9
                pushOntoCallStack("JSONObject", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 101, 9);
                try {
                    KeyValuePair();
                } finally {
                    popCallStack();
                }
                // Code for ZeroOrMore specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:101:22
                while (true) {
                    if (!(nextTokenType() == COMMA)) break;
                    // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:101:23
                    consumeToken(COMMA);
                    // Code for NonTerminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:101:27
                    pushOntoCallStack("JSONObject", "/Users/bnaudts/git/congo/examples/json/JSON.ccc", 101, 27);
                    try {
                        KeyValuePair();
                    } finally {
                        popCallStack();
                    }
                }
            }
            // Code for Terminal specified at /Users/bnaudts/git/congo/examples/json/JSON.ccc:103:5
            consumeToken(CLOSE_BRACE);
        } catch (ParseException e) {
            parseException3 = e;
            throw e;
        } finally {
            restoreCallStack(callStackSize4);
            if (thisProduction != null) {
                if (parseException3 == null) {
                    closeNodeScope(thisProduction, nodeArity() > 1);
                } else {
                    clearNodeScope();
                }
            }
        }
    }

    private static final EnumSet<TokenType> first_set$JSON_ccc$74$7 = tokenTypeSet(OPEN_BRACKET, OPEN_BRACE, TRUE, FALSE, NULL, STRING_LITERAL, NUMBER);
    private ArrayList<NonTerminalCall> parsingStack = new ArrayList<>();
    private final ArrayList<NonTerminalCall> lookaheadStack = new ArrayList<>();

    private void pushOntoCallStack(String methodName, String fileName, int line, int column) {
        parsingStack.add(new NonTerminalCall("JSONParser", token_source, fileName, methodName, line, column));
    }

    private void popCallStack() {
        NonTerminalCall ntc = parsingStack.remove(parsingStack.size() - 1);
        this.currentlyParsedProduction = ntc.productionName;
    }

    private void restoreCallStack(int prevSize) {
        while (parsingStack.size() > prevSize) {
            popCallStack();
        }
    }

    void dumpLookaheadStack(PrintStream ps) {
        ListIterator<NonTerminalCall> it = lookaheadStack.listIterator(lookaheadStack.size());
        while (it.hasPrevious()) {
            it.previous().dump(ps);
        }
    }

    void dumpCallStack(PrintStream ps) {
        ListIterator<NonTerminalCall> it = parsingStack.listIterator(parsingStack.size());
        while (it.hasPrevious()) {
            it.previous().dump(ps);
        }
    }

    void dumpLookaheadCallStack(PrintStream ps) {
        ps.println("Current Parser Production is: " + currentlyParsedProduction);
        ps.println("Current Lookahead Production is: " + currentLookaheadProduction);
        ps.println("---Lookahead Stack---");
        dumpLookaheadStack(ps);
        ps.println("---Call Stack---");
        dumpCallStack(ps);
    }

    public boolean isParserTolerant() {
        return false;
    }

    public void setParserTolerant(boolean tolerantParsing) {
        if (tolerantParsing) {
            throw new UnsupportedOperationException("This parser was not built with that feature!");
        }
    }

    private Token consumeToken(TokenType expectedType) {
        Token nextToken = nextToken(lastConsumedToken);
        if (nextToken.getType() != expectedType) {
            nextToken = handleUnexpectedTokenType(expectedType, nextToken);
        }
        this.lastConsumedToken = nextToken;
        this.nextTokenType = null;
        if (buildTree && tokensAreNodes) {
            lastConsumedToken.open();
            pushNode(lastConsumedToken);
            lastConsumedToken.close();
        }
        return lastConsumedToken;
    }

    private Token handleUnexpectedTokenType(TokenType expectedType, Token nextToken) {
        throw new ParseException(nextToken, EnumSet.of(expectedType), parsingStack);
    }

    private boolean buildTree = true;
    private boolean tokensAreNodes = true;
    private boolean unparsedTokensAreNodes = false;

    public boolean isTreeBuildingEnabled() {
        return buildTree;
    }

    public void setUnparsedTokensAreNodes(boolean unparsedTokensAreNodes) {
        this.unparsedTokensAreNodes = unparsedTokensAreNodes;
    }

    public void setTokensAreNodes(boolean tokensAreNodes) {
        this.tokensAreNodes = tokensAreNodes;
    }

    NodeScope currentNodeScope = new NodeScope();

    /**
    * @return the root node of the AST. It only makes sense to call
    * this after a successful parse.
    */
    public Node rootNode() {
        return currentNodeScope.rootNode();
    }

    /**
    * push a node onto the top of the node stack
    * @param n the node to push
    */
    public void pushNode(Node n) {
        currentNodeScope.add(n);
    }

    /**
    * @return the node on the top of the stack, and remove it from the
    * stack.
    */
    public Node popNode() {
        return currentNodeScope.pop();
    }

    /**
    * @return the node currently on the top of the tree-building stack.
    */
    public Node peekNode() {
        return currentNodeScope.peek();
    }

    /**
    * Puts the node on the top of the stack. However, unlike pushNode()
    * it replaces the node that is currently on the top of the stack.
    * This is effectively equivalent to popNode() followed by pushNode(n)
    * @param n the node to poke
    */
    public void pokeNode(Node n) {
        currentNodeScope.poke(n);
    }

    /**
    * Replace the type of the last consumed token and poke it onto the
    * stack.
    */
    protected void replaceTokenType(TokenType tt) {
        lastConsumedToken = lastConsumedToken.replaceType(tt);
        pokeNode(lastConsumedToken);
    }

    /**
    * @return the number of Nodes on the tree-building stack in the current node
    * scope.
    */
    public int nodeArity() {
        return currentNodeScope.size();
    }

    private void clearNodeScope() {
        currentNodeScope.clear();
    }

    private void openNodeScope(Node n) {
        new NodeScope();
        if (n != null) {
            n.setTokenSource(lastConsumedToken.getTokenSource());
            // We set the begin/end offsets based on the ending location
            // of the last consumed token. So, we start with a Node
            // of length zero. Typically this is overridden in the
            // closeNodeScope() method, unless this node has no children
            n.setBeginOffset(lastConsumedToken.getEndOffset());
            n.setEndOffset(n.getBeginOffset());
            n.setTokenSource(this.token_source);
            n.open();
        }
    }

    /* A definite node is constructed from a specified number of
    * children.  That number of nodes are popped from the stack and
    * made the children of the definite node.  Then the definite node
    * is pushed on to the stack.
    * @param n is the node whose scope is being closed
    * @param num is the number of child nodes to pop as children
    * @return @{code true}
    */
    private boolean closeNodeScope(Node n, int num) {
        n.setBeginOffset(lastConsumedToken.getEndOffset());
        n.setEndOffset(lastConsumedToken.getEndOffset());
        currentNodeScope.close();
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            nodes.add(popNode());
        }
        Collections.reverse(nodes);
        for (Node child : nodes) {
            if (child.getInputSource() == n.getInputSource()) {
                n.setBeginOffset(child.getBeginOffset());
                break;
            }
        }
        for (Node child : nodes) {
            if (unparsedTokensAreNodes && child instanceof Token) {
                Token tok = (Token) child;
                while (tok.previousCachedToken() != null && tok.previousCachedToken().isUnparsed()) {
                    tok = tok.previousCachedToken();
                }
                boolean locationSet = false;
                while (tok.isUnparsed()) {
                    n.add(tok);
                    if (!locationSet && tok.getInputSource() == n.getInputSource() && tok.getBeginOffset() < n.getBeginOffset()) {
                        n.setBeginOffset(tok.getBeginOffset());
                        locationSet = true;
                    }
                    tok = tok.nextCachedToken();
                }
            }
            if (child.getInputSource() == n.getInputSource()) {
                n.setEndOffset(child.getEndOffset());
            }
            n.add(child);
        }
        n.close();
        pushNode(n);
        return true;
    }

    /**
    * A conditional node is constructed if the condition is true.  All
    * the nodes that have been pushed since the node was opened are
    * made children of the conditional node, which is then pushed
    * on to the stack.  If the condition is false the node is not
    * constructed and they are left on the stack.
    */
    private boolean closeNodeScope(Node n, boolean condition) {
        if (n == null || !condition) {
            currentNodeScope.close();
            return false;
        }
        return closeNodeScope(n, nodeArity());
    }

    public boolean getBuildTree() {
        return buildTree;
    }

    public void setBuildTree(boolean buildTree) {
        this.buildTree = buildTree;
    }


    @SuppressWarnings("serial")
    class NodeScope extends ArrayList<Node> {
        NodeScope parentScope;

        NodeScope() {
            this.parentScope = JSONParser.this.currentNodeScope;
            JSONParser.this.currentNodeScope = this;
        }

        boolean isRootScope() {
            return parentScope == null;
        }

        Node rootNode() {
            NodeScope ns = this;
            while (ns.parentScope != null) {
                ns = ns.parentScope;
            }
            return ns.isEmpty() ? null : ns.get(0);
        }

        Node peek() {
            if (isEmpty()) {
                return parentScope == null ? null : parentScope.peek();
            }
            return get(size() - 1);
        }

        Node pop() {
            return isEmpty() ? parentScope.pop() : remove(size() - 1);
        }

        void poke(Node n) {
            if (isEmpty()) {
                parentScope.poke(n);
            } else {
                set(size() - 1, n);
            }
        }

        void close() {
            parentScope.addAll(this);
            JSONParser.this.currentNodeScope = parentScope;
        }

        int nestingLevel() {
            int result = 0;
            NodeScope parent = this;
            while (parent.parentScope != null) {
                result++;
                parent = parent.parentScope;
            }
            return result;
        }

        public NodeScope clone() {
            NodeScope clone = (NodeScope) super.clone();
            if (parentScope != null) {
                clone.parentScope = parentScope.clone();
            }
            return clone;
        }

    }

}


