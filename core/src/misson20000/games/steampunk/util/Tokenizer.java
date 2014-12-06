package misson20000.games.steampunk.util;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by misson20000 on 12/3/14.
 */
public class Tokenizer {
  private Reader reader;
  private boolean ignoreNewlines;

  public boolean atEnd;
  public int lastDelim;

  public Tokenizer(Reader r, boolean ignoreNewlines) {
    this.reader = r;
    this.ignoreNewlines = ignoreNewlines;
    this.atEnd = false;
  }

  public String nextToken(String delimiter, boolean includeDelim) throws IOException {
    StringBuilder b = new StringBuilder();
    int ch;
    while((ch = reader.read()) != -1 && !delimiter.contains(Character.toString((char) ch))) {
      if((!ignoreNewlines) || (ch != '\r' && ch != '\n')) {
        b.append(Character.toString((char) ch));
      }
    }
    this.lastDelim = ch;
    if(includeDelim) {
      b.append(Character.toString((char) ch));
    }
    if(ch == -1) {
      this.atEnd = true;
    }
    if(b.toString().isEmpty()) {
      if(ch == -1) {
        return null;
      }
    }
    return b.toString();
  }
}
